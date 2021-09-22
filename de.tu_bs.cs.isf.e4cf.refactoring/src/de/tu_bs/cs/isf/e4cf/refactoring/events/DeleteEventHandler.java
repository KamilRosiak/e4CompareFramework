package de.tu_bs.cs.isf.e4cf.refactoring.events;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.osgi.service.event.Event;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces.NodesCallback;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;

@Singleton
@Creatable
public class DeleteEventHandler extends EventHandlerBase {

	@Inject
	public DeleteEventHandler(ClusterEngine clusterEngine) {
		super(clusterEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleEvent(Event event) {
		Node deletedNode = (Node) event.getProperty(DSEditorST.DELETED_NODE);
		NodesCallback callback = (NodesCallback) event.getProperty(DSEditorST.CALLBACK);
		CloneModel cloneModel = (CloneModel) event.getProperty(DSEditorST.CLONE_MODEL);
		Set<Node> affectedNodes = cloneModel.delete(deletedNode);
		callback.handle(affectedNodes);
		
		
		clusterEngine.analyzeCloneModel(cloneModel, null);
	}

}
