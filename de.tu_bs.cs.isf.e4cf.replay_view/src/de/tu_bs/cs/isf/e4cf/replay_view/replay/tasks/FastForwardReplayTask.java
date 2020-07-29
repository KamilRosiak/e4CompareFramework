package de.tu_bs.cs.isf.e4cf.replay_view.replay.tasks;

import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.ecore.util.EcoreUtil;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.ReplayTaskObserver;

/**
 * Represents the replay task for executing a sequence of modifications at once.
 * 
 * @author Oliver Urbaniak
 * @see TimerTask
 */
public class FastForwardReplayTask extends SequentialReplayTask {

	protected CountDownLatch latch;
	
	public FastForwardReplayTask(IEventBroker eventBroker) {
		super(eventBroker);
		latch = new CountDownLatch(1);
	}

	public FastForwardReplayTask(IEventBroker eventBroker, Queue<Modification> modQueue) {
		super(eventBroker, modQueue);
		latch = new CountDownLatch(1);
	}

	public FastForwardReplayTask(SequentialReplayTask task) {
		super(task);
	}

	@Override
	public void run() {		
		Modification mod = null;
		while ((mod = getModQueue().poll()) != null) {
			// store most recent modification
			setMostRecentModification(mod);
			
			// wrap modification into a set
			Modification cloneMod = EcoreUtil.copy(mod);
			FeatureModelModificationSet modSet = modFactory.createFeatureModelModificationSet();
			modSet.getModifications().add(cloneMod);

			// replay modification set
			eventBroker.send(FDEventTable.REPLAY_FD_MODIFICATION_SET, modSet);
			
			// update observers
			for (ReplayTaskObserver observer : modificationObservers) {
				observer.onApplyModification(mod);
			}
		}
		
		// set finished state
		active = false;
		latch.countDown();
	}
	
	public CountDownLatch getCountDownLatch() {
		return latch;
	}
}
