package de.tu_bs.cs.isf.e4cf.replay_view.replay.tasks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.TimerTask;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.ecore.util.EcoreUtil;

import FeatureDiagramModificationSet.FeatureDiagramModificationSetFactory;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.ModificationReplayer;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.ReplayTaskObserver;

/**
 * Represents the replay task for periodic executions of modifications applied on the current feature model.
 * 
 * @author Oliver Urbaniak
 * @see TimerTask
 */
public class SequentialReplayTask extends TimerTask {
	protected FeatureDiagramModificationSetFactory modFactory = FeatureDiagramModificationSetFactory.eINSTANCE;
			
	/**
	 * The modification queue stores the current number of modifications yet to be executed
	 */
	protected Queue<Modification> modQueue;
	
	/**
	 * The most recently applied modification within this task
	 */
	protected Modification mostRecentModification;
	
	/**
	 * Observers that get notified if a modification has been applied
	 */
	protected List<ReplayTaskObserver> modificationObservers;
	
	/**
	 * A task is considered active if a replay sequence is started (scheduled) and the task has not been cancelled or completed.
	 * The task does not become inactive if the client pauses the replay execution via {@link ModificationReplayer#pauseReplay()}.
	 */
	protected boolean active;

	/**
	 * Queue comprising all the modifications, even those which have already been applied.
	 */
	protected Queue<Modification> totalModifications;

	protected IEventBroker eventBroker;
	
	public SequentialReplayTask(IEventBroker eventBroker) {
		this(eventBroker, new ArrayDeque<Modification>());
	}
	
	public SequentialReplayTask(IEventBroker eventBroker, Queue<Modification> modQueue) {
		this.eventBroker = eventBroker;
		this.setModQueue(modQueue);
		this.setTotalModifications(new ArrayDeque<Modification>(modQueue));
		this.modificationObservers 	= new ArrayList<>();
		this.active 				= false;
		this.setMostRecentModification(null);
	}
	
	public SequentialReplayTask(SequentialReplayTask task) {
		this.eventBroker = task.eventBroker;
		this.setModQueue(task.getModQueue());
		this.setTotalModifications(task.getTotalModifications());
		this.modificationObservers 	= task.modificationObservers;
		this.active 				= task.active;
		this.setMostRecentModification(task.getMostRecentModification());
	}
	
	@Override
	public void run() {
		Modification mod = getModQueue().poll();
		if (mod != null) {			
			// store modifications beforehand
			setMostRecentModification(mod);
			
			// wrap modification into a set
			Modification cloneMod = EcoreUtil.copy(mod);
			FeatureModelModificationSet modSet = modFactory.createFeatureModelModificationSet();
			modSet.getModifications().add(cloneMod);

			// replay modification set
			eventBroker.send(FDEventTable.REPLAY_FD_MODIFICATION_SET, modSet);
			
			// update observers
			modificationObservers.forEach(observer -> observer.onApplyModification(mod));
		} else {
			cancel();
			setActive(false);
			modificationObservers.forEach(observer -> observer.onFinish());
		}
	}
	
	public Queue<Modification> getModificationQueue() {
		return getModQueue();
	}
	
	public Optional<Modification> peekMostRecentModification() {
		return Optional.ofNullable(getMostRecentModification());
	}
	
	public void addModificationObserver(ReplayTaskObserver observer) {
		if (!modificationObservers.contains(observer)) {
			modificationObservers.add(observer);
		}
	}
	
	public void removeModificationObserver(ReplayTaskObserver observer) {
		modificationObservers.remove(observer);
	}
	
	public Queue<Modification> getTotalModifications() {
		return totalModifications;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * A task is considered active if a replay sequence is started (scheduled) and the task has not been cancelled by a user.
	 * The task does not become inactive if the client pauses the replay execution via {@link ModificationReplayer#pauseReplay()}.
	 */
	public boolean isActive() {
		return active;
	}

	public Modification getMostRecentModification() {
		return mostRecentModification;
	}

	public void setMostRecentModification(Modification mostRecentModification) {
		this.mostRecentModification = mostRecentModification;
	}

	public Queue<Modification> getModQueue() {
		return modQueue;
	}

	public void setModQueue(Queue<Modification> modQueue) {
		this.modQueue = modQueue;
	}

	public void setTotalModifications(Queue<Modification> totalModifications) {
		this.totalModifications = totalModifications;
	}
}