package de.tu_bs.cs.isf.e4cf.replay_view.view.menu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;

import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewStringTable;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class JumpToAction extends Action {

	private ReplayView view;

	public JumpToAction(ReplayView view) {
		super("Jump to", AS_PUSH_BUTTON);
		this.view = view;
	}
	
	@Override
	public void run() {
		// Get selection from tree viewer
		TreeViewer tv = view.getModificationSetTreeViewer();
		if (tv == null) {
			RCPMessageProvider.errorMessage("Replay View", "The Replay Tree Viewer is not initialized.");
		}
		Object selectedObject = tv.getStructuredSelection().getFirstElement();
		
		// For a modification, jump to it
		if (selectedObject instanceof Modification) {
			Modification mod = (Modification) selectedObject;
			view.getController().getReplayer().jumpTo(mod);
		}
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return view.getServiceContainer().imageService.getImageDescriptor(ReplayViewStringTable.BUNDLE_NAME, "icons/skip_24.png");
	}
	
}
