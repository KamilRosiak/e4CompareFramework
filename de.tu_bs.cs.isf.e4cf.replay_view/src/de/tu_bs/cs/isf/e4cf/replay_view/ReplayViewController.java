 
package de.tu_bs.cs.isf.e4cf.replay_view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.IPartListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;

import FeatureDiagram.FeatureDiagramm;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.ModificationReplayer;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewStringTable;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.AddReplayCommandBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.ApplyMutlipleReplaysBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.ApplySingleReplayBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.CancelCommandBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.CheckAllBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.CheckBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.PauseResumeCommandBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.RemoveReplayCommandBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.TreeItemCheckBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.UncheckAllBehaviour;
import de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour.UncheckBehaviour;

public class ReplayViewController {
	
	public class FeatureModelEditorPartListener implements IPartListener {
		@Override public void partActivated(MPart part) 	{}
		@Override public void partBroughtToTop(MPart part) 	{}
		@Override public void partDeactivated(MPart part) 	{}
		
		@Override public void partHidden(MPart part) 		{ 
			serviceContainer.eventBroker.send(FDEventTable.DEREGISTER_ERR_LISTENER, replayer);
		}
		
		@Override public void partVisible(MPart part) 		{ 
			serviceContainer.eventBroker.send(FDEventTable.REGISTER_ERR_LISTENER, replayer);
		}
	}
	
	private ServiceContainer serviceContainer;
	private ReplayView view;
	
	private List<FeatureModelModificationSet> modSets;
	private ModificationReplayer replayer;
	
	@Inject
	public ReplayViewController(ServiceContainer serviceContainer) {
		this.serviceContainer = serviceContainer;
		this.modSets = new ArrayList<>();
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		view = new ReplayView(this, serviceContainer);
		view.attachApplyBehaviour(new ApplyMutlipleReplaysBehaviour(view));
		view.attachPauseResumeBehaviour(new PauseResumeCommandBehaviour(view));
		view.attachCancelBehaviour(new CancelCommandBehaviour(view));
		view.attachAddReplayCommandBehaviour(new AddReplayCommandBehaviour(view));
		view.attachRemoveReplayCommandBehaviour(new RemoveReplayCommandBehaviour(view));
		view.attachCheckBehaviour(new CheckBehaviour(view));
		view.attachCheckAllBehaviour(new CheckAllBehaviour(view));
		view.attachUncheckBehaviour(new UncheckBehaviour(view));
		view.attachUncheckAllBehaviour(new UncheckAllBehaviour(view));
		view.attachTreeDoubleClickBehaviour(new ApplySingleReplayBehaviour(view));
		view.attachTreeItemCheckBehaviour(new TreeItemCheckBehaviour(view));
		
		view.createControl(parent);
		view.getModificationSetTreeViewer().setInput(this.modSets);
		
		// initialize replay logic
		this.replayer = new ModificationReplayer(view);
		replayer.addStateChangeListener(view);
		
		// keep track of feature model editor part changes registering the error listener dynamically
		boolean featureModelVisible = serviceContainer.partService.isPartVisible(FDStringTable.BUNDLE_NAME);
		if (featureModelVisible) {
			serviceContainer.eventBroker.send(FDEventTable.REGISTER_ERR_LISTENER, replayer);
		}
		serviceContainer.partService.registerPartListener(new FeatureModelEditorPartListener());
	}
	
	@Optional
	@Inject
	public void addModifications(@UIEventTopic(ReplayViewStringTable.EVENT_ADD_MOD_SETS) List<FeatureModelModificationSet> modSets) {
		// add objects to tree viewer
		this.modSets.addAll(modSets);
		TreeViewer tv = this.view.getModificationSetTreeViewer();
		tv.refresh();
		
		// adjust columns widths
		List<TreeColumn> cols = Arrays.asList(tv.getTree().getColumns());
		cols.forEach(col -> col.pack());
	}
	
	@Optional
	@Inject
	public void featureModelChanged(@UIEventTopic(FDEventTable.REPLAY_FEATURE_DIAGRAM_CHANGED) FeatureDiagramm fd) {
		if (fd != null) {
			replayer.setCurrentFeatureDiagram(fd); 
		}
	}

	public List<FeatureModelModificationSet> getModificationSets() {
		return modSets;
	}
	
	public void removeModificationSet(FeatureModelModificationSet modSet) {
		if (modSet != null) {
			modSets.remove(modSet);
		}
	}
	
	public void removeModification(Modification mod) {
		if (mod != null) {
			modSets.forEach(modSet -> modSet.getModifications().remove(mod));			
		}
	}

	public ModificationReplayer getReplayer() {
		return replayer;
	}
	
	@PreDestroy
	public void preDestroy() {
		if (replayer != null) {
			replayer.pauseReplay();
			serviceContainer.eventBroker.send(FDEventTable.DEREGISTER_ERR_LISTENER, replayer);
		}
	}
	
	@Focus
	public void onFocus() {
		
	}
	
	public ServiceContainer getServiceContainer() {
		return serviceContainer;
	}
}