package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

public class PropertiesAction implements UndoAction {

	private String name;
	private String oldAttributeValue;
	private String newAttributeValue;

	public PropertiesAction(String name, String oldAttributeValue, String newAttributeValue) {
		this.name = name;
		this.oldAttributeValue = oldAttributeValue;
		this.newAttributeValue = newAttributeValue;
	}

	@Override
	public void undo() {
		System.out.println(newAttributeValue);
		System.out.println(oldAttributeValue);
		newAttributeValue = oldAttributeValue;
	}

//	@Optional
//	@Inject
//	public void refreshTreeView(@UIEventTopic("SendCurrentNodeEvent") Node node) {
//		this.node=node;
//	}
}
