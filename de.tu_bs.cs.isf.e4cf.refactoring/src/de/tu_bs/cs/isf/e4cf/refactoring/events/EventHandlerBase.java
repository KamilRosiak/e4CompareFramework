package de.tu_bs.cs.isf.e4cf.refactoring.events;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.event.EventHandler;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;

public abstract class EventHandlerBase implements EventHandler {

	protected ClusterEngine clusterEngine;

	public static ServiceContainer services;

	public EventHandlerBase(ClusterEngine clusterEngine) {
		super();
		this.clusterEngine = clusterEngine;
	}

	protected void refreshTree(CloneModel cloneModel) {
		clusterEngine.analyzeCloneModel(cloneModel);
		Map<String, Object> event = new HashMap<String, Object>();
		event.put(DSEditorST.TREE, cloneModel.getTree());
		event.put(DSEditorST.CLONE_MODEL, cloneModel);
		services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, event);
	}

}
