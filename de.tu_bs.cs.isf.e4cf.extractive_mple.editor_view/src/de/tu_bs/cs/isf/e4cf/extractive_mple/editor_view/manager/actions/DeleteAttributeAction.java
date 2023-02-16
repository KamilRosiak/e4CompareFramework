package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.manager.actions;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;

/**
 * Implementation of UndoAction for DeleteAttribute
 * 
 * @author Team05
 *
 */

public class DeleteAttributeAction extends AbstractTreeAction {
	private NodeAttributePair pair;
	private ServiceContainer services;

	public DeleteAttributeAction(String name, NodeAttributePair pair, ServiceContainer services) {
		this.setName(name);
		this.pair = pair;
		this.services = services;
	}

	@Override
	public void undo() {
		services.eventBroker.send(MPLEEditorConsts.ADD_ATTRIBUTE_EVENT, pair);
	}

	@Override
	public void execute() {
	    // TODO Auto-generated method stub
	    
	}

}
