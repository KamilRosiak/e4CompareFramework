package de.tu_bs.cs.isf.e4cf.replay_view.replay;

import java.util.ArrayDeque;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Timer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.widgets.Display;

import FeatureDiagram.FeatureDiagramm;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.error.ErrorListener;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.state.ReplayerState;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.state.ReplayerStateMachine;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.state.ReplayerState.ReplayAction;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.tasks.FastForwardReplayTask;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.tasks.SequentialReplayTask;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ModificationSetUtil;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewStringTable;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

/**
 * Replays a list of commands available in the {@link ReplayView}.
 * 
 * @author Oliver Urbaniak
 *
 */
public class ModificationReplayer extends ReplayerStateMachine implements ReplayTaskObserver, ErrorListener {
		
	private static final int ZERO_DELAY = 0;
	private static final String TIMER_NAME = "modificationTimer";
	public static final int DEFAULT_REPLAY_PERIOD = 2000;
	
	private ReplayView view;
	
	private SequentialReplayTask task;
	private Timer timer;
		
	/**
	 * A task is considered paused if {@link #pauseReplay()} has been executed stopping 
	 * the replay execution. The client is able to resume execution.
	 */
	private boolean paused;
	
	private FeatureDiagramm currentFeatureDiagram;
	
	/**
	 * Stores the modification together with its resulting feature model after applying.
	 * The mapping depends on the initial feature model on which the 
	 */
	private Map<Modification, FeatureDiagramm> featureDiagramMap;
	
	public ModificationReplayer(ReplayView view) {
		super(ReplayerState.INACTIVE);
		this.view 				= view;
		this.task 				= new SequentialReplayTask(view.getServiceContainer().eventBroker);
		this.timer 				= new Timer(TIMER_NAME, true);
		this.paused 			= false;
		this.featureDiagramMap 	= new IdentityHashMap<>();
	}
	
	public void replayCheckedCommands() {
		replayCommands(new CheckedModificationProvider(view));
	}

	public void replaySelectedCommands() {
		replayCommands(new SelectedModificationProvider(view));
	}
	
	/**
	 * Initiates the replay task for a set of modifications. The modification provider decides which modifications are replayed. 
	 * The period between replay actions can be adjusted in the Preferences. 
	 * If no preferences are set, the replay period is {@value #DEFAULT_REPLAY_PERIOD}.
	 * 
	 * @param modProvider provider for the replayed modifications
	 * @param replayPeriod the time between modifications in <i>ms</i>
	 */
	public void replayCommands(ModificationProvider modProvider) {
		this.replayCommands(modProvider, getPreferredReplayPeriod(), ZERO_DELAY);
	}
	
	public void replayCommands(ModificationProvider modProvider, int replayPeriod) {
		this.replayCommands(modProvider, replayPeriod, ZERO_DELAY);
	}
	
	/**
	 * Initiates the replay task for a set of modifications. The modification provider decides which modifications are replayed.
	 * 
	 * @param modProvider provider for the replayed modifications
	 * @param replayPeriod the time between modifications in <i>ms</i>
	 */
	synchronized public void replayCommands(ModificationProvider modProvider, int replayPeriod, int initialDelay) {				
		// check validity for state change 
		if (!advanceState(ReplayAction.APPLY)) {
			// do not allow replay overlap
			if (wasInState(ReplayerState.PLAYING)) {
				RCPMessageProvider.informationMessage("Replay View", "Another replay sequence is being played. "
						+ "Please wait for the full replay to end.");
				return;
			}
			if (wasInState(ReplayerState.PAUSED)) {
				RCPMessageProvider.informationMessage("Replay View", "Another replay sequence is being played but has been paused. "
						+ "Please resume or cancel the execution.");
				return;
			}			
		}
		
		// collect filtered modifications to be replayed
		Queue<Modification> mods = modProvider != null ? modProvider.getModifications() : new ArrayDeque<Modification>();
		
		// send each individual modification to replayer 
		task = new SequentialReplayTask(view.getServiceContainer().eventBroker, mods);
		task.addModificationObserver(this);
		if (timer != null) timer.cancel();
		timer = new Timer(TIMER_NAME, true);
		
		task.setActive(true);
		timer.schedule(task, initialDelay, replayPeriod);
	}

	private int getPreferredReplayPeriod() {
		int replayPeriod = DEFAULT_REPLAY_PERIOD;
		KeyValueNode s = PreferencesUtil.getValueWithDefault(ReplayViewStringTable.BUNDLE_NAME, ReplayViewStringTable.REPLAY_PERIOD_KEY, 
				Integer.toString(DEFAULT_REPLAY_PERIOD));
		String periodSting = s.getStringValue();
		try {
			replayPeriod = Math.abs(Integer.parseInt(periodSting));
		} catch (NumberFormatException e) {
			replayPeriod = DEFAULT_REPLAY_PERIOD;
		}
		return replayPeriod;
	}

	@Override
	public void onApplyModification(Modification mod) {
		// only continue if one of these states are active
		if (!isInAnyState(ReplayerState.PLAYING, ReplayerState.JUMPING)) {
			return;
		}
		
		// store modification with the current feature diagram state
		if (mod != null) {
			featureDiagramMap.put(mod, currentFeatureDiagram);
		}
		
		// update view 
		Display.getDefault().syncExec(() -> {
			view.focusItem(mod);
			if (view.getFormatCheckbox().getSelection()) {
				view.getServiceContainer().eventBroker.send(FDEventTable.FORMAT_DIAGRAM_EVENT, null);
			}
		});
	}
	
	@Override
	public void onFinish() {
		if (!advanceState(ReplayAction.CANCEL)) {
			if (wasInState(ReplayerState.UNDEFINED)) {
				System.out.println("[ReplayView::Cancel]: replay view is in undefined state.");
			}
		}
	}
	
	/**
	 * Jumps back or ahead to a certain modification. A jump back reverts to an older state associated 
	 * with the modification. A jump ahead of the timeline replays the necessary modifications in a fast-forward fashion.
	 * <br>
	 * At the moment a jump is only possible if the target modification is from the same as the current one. 
	 * 
	 * @param targetMod Jump target
	 */
	synchronized public void jumpTo(Modification targetMod) {
		// if the replay is currently playing, pause it
		if (!isInState(ReplayerState.PAUSED)) {
			pauseReplay();
		}
		
		// enter jumping state
		if (!advanceState(ReplayAction.JUMP)) {
			if (wasInState(ReplayerState.INACTIVE)) {
				RCPMessageProvider.errorMessage("ReplayView", "There is no active replay. Please apply a sequence of checked modificiations before.");
				revertState();
			}
			if (wasInState(ReplayerState.PLAYING)) {
				RCPMessageProvider.errorMessage("ReplayView", "There is an active replay. The jump requires a pausing replay state.");
				revertState();
			}
			return;
		}
					
		// Only allow jumps to modifications that are part of the replay
		if (!task.getTotalModifications().contains(targetMod)) {
			RCPMessageProvider.errorMessage("Invalid Jump to Modification", "The jump to modifications not involved with the replay is invalid. "
					+ "Please select a modification which is part of the current replay.");
			revertState();
			return;
		}
		
		// Get the last modification applied
		Optional<Modification> optCurrentMod = task.peekMostRecentModification();
		if (!optCurrentMod.isPresent()) {
			throw new RuntimeException("There is no last played modification available.");
		}
		
		// Determine chronological order of the two modifications
		Modification currentMod = optCurrentMod.get();
		int result = ModificationSetUtil.compare(currentMod, targetMod);
		if (result == 0) { 			// SIMULTANEOUS MODIFICATIONS
			handleSimulataneousModifications(currentMod, targetMod);
		} else if (result < 0) { 	// JUMP AHEAD IN TIME
			handleJumpAhead(currentMod, targetMod);
		} else if (result > 0) { 	// JUMP BACK IN TIME
			handleJumpBack(currentMod, targetMod);
		}
	}

	private void handleSimulataneousModifications(Modification currentMod, Modification targetMod) {
		if (EcoreUtil.equals(currentMod, targetMod)) {
			// nothing to do here --> same modification
		} else {
			throw new RuntimeException("There are two different modifications with the same timestamp and therefore not distinguishable.");
		}
	}
	
	/**
	 * 
	 * 
	 * @param currentMod
	 * @param targetMod
	 */
	private void handleJumpAhead(Modification currentMod, Modification targetMod) {	
		Runnable jumpAheadHandler = () -> {
			// Get the remaining modifications and ensure target modification is contained
			Queue<Modification> remainingMods = task.getModificationQueue();
			if (!remainingMods.contains(targetMod)) {
				RCPMessageProvider.errorMessage("Jump to Upcoming Modification", "It is not allowed to jump to a modification which isn't part of the replay. "
						+ "Please only select upcoming modifications to jump ahead to.");
				return;
			}
			
			// Split remaining modifications into two queues: queue limited by target inclusively [currentMod, targetMod] and rest (target, ...]
			Queue<Modification> toTargetMods = new ArrayDeque<>();
			Modification mod;
			while((mod = remainingMods.poll()) != null) {
				toTargetMods.add(mod);
				
				if (mod == targetMod) break;
			}
			
			// throw if the target modification has not been collected
			if(!toTargetMods.contains(targetMod)) {
				RCPMessageProvider.errorMessage("Jump to Upcoming Modification", "The upcoming jump target was never reached.");
				return;
			}
			
			// start replaying all modifications until the target, record feature diagram state for each modification
			SequentialReplayTask taskCopy = new SequentialReplayTask(task);
			task = new FastForwardReplayTask(view.getServiceContainer().eventBroker, toTargetMods);
			task.addModificationObserver(modification -> {
				if (modification != null) featureDiagramMap.put(modification, currentFeatureDiagram);	
			});
			task.setActive(true);
			timer.schedule(task, ZERO_DELAY);
			
			// wait until the fast forward task is finished
			try {
				((FastForwardReplayTask) task).getCountDownLatch().await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Update the visuals
			Display.getDefault().asyncExec(() -> {
				view.getModificationSetTreeViewer().setSelection(null);
				view.focusItem(targetMod);
				if (view.getFormatCheckbox().getSelection()) {
					view.getServiceContainer().eventBroker.send(FDEventTable.FORMAT_DIAGRAM_EVENT, null);
				}
			});
			
			// Now just initiate a new replay task
			task = taskCopy;
			task.setMostRecentModification(targetMod);
			
			// advance state
			advanceState(ReplayAction.PAUSE);
		};
		Thread jumpAheadThread = new Thread(jumpAheadHandler, "ReplayView_JumpAheadThread");
		jumpAheadThread.start();
	}

	private void handleJumpBack(Modification currentMod, Modification targetMod) {
		Runnable jumpBackHandler = () -> {
			// Get the remaining modifications and ensure target modification is contained
			Queue<Modification> totalMods = task.getTotalModifications();
			if (!totalMods.contains(targetMod)) {
				RCPMessageProvider.errorMessage("Jump to Past Modification", "It is not allowed to jump to a modification which isn't part of the replay. "
						+ "Please only select past modifications to jump back to.");
				return;
			}
			
			// Reset the feature diagram state to the one stored in the target modification
			FeatureDiagramm featureDiagram = featureDiagramMap.get(targetMod);
			if (featureDiagram == null) {
				throw new RuntimeException("Feature diagram must not be null.");
			}
			FeatureDiagramm featureModelCopy = EcoreUtil.copy(featureDiagram);
			view.getServiceContainer().eventBroker.send(FDEventTable.LOAD_FEATURE_DIAGRAM, featureModelCopy);
						
			// Wind back the clock for the replay task - set its modification queue to the state just after the target modification
			Queue<Modification> totalModsQueueCopy = new ArrayDeque<>(totalMods);
			while (totalModsQueueCopy.poll() != targetMod) {}
			task.getModQueue().clear();
			task.getModQueue().addAll(totalModsQueueCopy);
			task.setMostRecentModification(targetMod);
			
			// Update the visuals
			Display.getDefault().syncExec(() -> {
				view.getModificationSetTreeViewer().setSelection(null);
				view.focusItem(targetMod);
			}); 
			
			// advance state
			advanceState(ReplayAction.PAUSE);
			
		};
		Thread jumpBackThread = new Thread(jumpBackHandler, "ReplayView_JumpBackThread");
		jumpBackThread.start();
	}
	
	public static boolean fromSameSequence(Modification mod1, Modification mod2) {
		return mod1 != null && mod2 != null ? mod1.eContainer() == mod2.eContainer() : false;
	}
	
	/**
	 * Continues the replay if it is currently paused. 
	 * 
	 * @return true if the execution was successfully resumed.
	 */
	synchronized public boolean resumeReplay() {
		if (!advanceState(ReplayAction.RESUME)) {
			if (wasInState(ReplayerState.INACTIVE)) {
				RCPMessageProvider.errorMessage("Replay View", "There is no active replay to resume.");
				revertState();
			}
			if (wasInState(ReplayerState.PLAYING)) {
				RCPMessageProvider.errorMessage("ReplayView", "The replay is still active and cannot resume");
				revertState();
			}
			if (wasInState(ReplayerState.ERROR)) {
				System.out.println("[ReplayView::Resume]: replay view is in error state.");
			}
			return false;
		}
		
		timer.schedule(task, ZERO_DELAY, getPreferredReplayPeriod());
		
		paused = false;
		
		return true;
	}
	
	/**
	 * Pauses the replay. The execution can be continued with {@link #resumeReplay()}.
	 * 
	 * @return true if the execution was successfully paused.
	 */
	synchronized public boolean pauseReplay() {
		if (!advanceState(ReplayAction.PAUSE)) {
			if (wasInState(ReplayerState.INACTIVE)) {
				RCPMessageProvider.errorMessage("Replay View", "There is no active replay to be paused.");
				revertState();
			}
			if (wasInState(ReplayerState.ERROR)) {
				System.out.println("[ReplayView::Pause]: replay view is in error state.");
			}
			return false;
		}
			
		task.cancel();
		task = new SequentialReplayTask(task);
		
		paused = true;
		
		return true; 
	}
	
	/**
	 * Stops and cancels replay completely. The client can now replay a new sequence via {@link #replayCommands()}.
	 * 
	 * @return true if the execution was successfully cancelled.
	 */
	synchronized public boolean cancelReplay() {
		if (!advanceState(ReplayAction.CANCEL)) {
			if (wasInState(ReplayerState.INACTIVE)) {
				RCPMessageProvider.errorMessage("ReplayView", "There is no active replay to be cancelled.");
			}
			revertState();
			return false;
		}
		
		if (isActive()) {
			task.cancel();
			task = new SequentialReplayTask(view.getServiceContainer().eventBroker);
			
			paused = false;
			
			// reset selection
			Display.getDefault().syncExec(() -> {
				view.focusItem(null);
				view.getModificationSetTreeViewer().refresh();
				view.getApplyReplayButton().setEnabled(true);
				view.getCancelReplayButton().setEnabled(false);
				view.getPauseResumeCommandButton().setEnabled(false);
			});
			
			return true;
		}
		return false;
	}
	
	/**
	 * A replay task is considered paused if {@link #pauseReplay()} has been executed stopping 
	 * the replay execution. The client is able to resume execution.
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * A started replay task is considered active if the task has not been cancelled by a user or if there are still modifications left.
	 * The task does not become inactive if the client pauses the replay execution via {@link ModificationReplayer#pauseReplay()}.
	 */
	public boolean isActive() {
		return task != null && task.isActive();
	}
	
	public void setCurrentFeatureDiagram(FeatureDiagramm fd) {
		FeatureDiagramm fdCopy = EcoreUtil.copy(fd);
		this.currentFeatureDiagram = fdCopy;
	}
	
	public FeatureDiagramm getCurrentFeatureDiagram() {
		return currentFeatureDiagram;
	}

	
	/**
	 * Catches exceptions thrown from the Feature Model.
	 * 
	 * @param e exception thrown 
	 */
	@Override
	public void onError(FeatureModelViewError e) {
		if (isActive() || isPaused()) {
			// Abort replay and emphasize incorrect modification			
			Modification mostRecentMod = task.getMostRecentModification();

			cancelReplay();
			
			view.focusItem(mostRecentMod);
			RCPMessageProvider.errorMessage("Replay View", 
					"The current modification in the sequence is inconsistent with the current feature model state. "
					+ "The replay will now be aborted.");
			view.focusItem(null);
		}
	}
}
