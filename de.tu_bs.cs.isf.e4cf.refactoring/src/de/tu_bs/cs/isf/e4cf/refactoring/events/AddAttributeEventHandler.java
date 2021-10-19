package de.tu_bs.cs.isf.e4cf.refactoring.events;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.osgi.service.event.Event;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;

@Singleton
@Creatable
public class AddAttributeEventHandler extends EventHandlerBase {

	@Inject
	public AddAttributeEventHandler(ClusterEngine clusterEngine) {
		super(clusterEngine);
	}

	@Override
	public void handleEvent(Event event) {
		Node selectedNode = (Node) event.getProperty(DSEditorST.SELECTED_NODE);
		Attribute attribute = (Attribute) event.getProperty(DSEditorST.ATTRIBUTE);
		CloneModel cloneModel = (CloneModel) event.getProperty(DSEditorST.CLONE_MODEL);

		cloneModel.addAttribute(selectedNode, attribute);
		refreshTree(cloneModel);

	}

}
