package de.tu_bs.cs.isf.e4cf.refactoring.events;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

@Singleton
@Creatable
public class EventManager {

	@Inject
	public EventManager(AddChildEventHandler addChildEventHandler, DeleteEventHandler deletedEventHandler,
			AddAttributeEventHandler addAttributeEventHandler, DeleteAttributeEventHandler deleteAttributeEventHandler,
			RestoreTreesEvent restoreTreesEvent, AddAttributeValueEventHandler addAttributeValueEventHandler,
			EditAttributeValueEventHandler editAttributeValueEventHandler,
			EditAttributeKeyEventHandler editAttributeKeyEventHandler, ServiceContainer services) {
		super();

		services.eventBroker.subscribe(DSEditorST.ADD_CHILD_EVENT, addChildEventHandler);
		services.eventBroker.subscribe(DSEditorST.DELETE_EVENT, deletedEventHandler);
		services.eventBroker.subscribe(DSEditorST.ATTRIBUTE_ADD_EVENT, addAttributeEventHandler);
		services.eventBroker.subscribe(DSEditorST.ATTRIBUTE_DELETE_EVENT, deleteAttributeEventHandler);
		services.eventBroker.subscribe(DSEditorST.RESTORE_TREES_EVENT, restoreTreesEvent);
		services.eventBroker.subscribe(DSEditorST.ATTRIBUTE_ADD_VALUE_EVENT, addAttributeValueEventHandler);
		services.eventBroker.subscribe(DSEditorST.ATTRIBUTE_EDIT_VALUE_EVENT, editAttributeValueEventHandler);
		services.eventBroker.subscribe(DSEditorST.ATTRIBUTE_EDIT_KEY_EVENT, editAttributeKeyEventHandler);
	}

}
