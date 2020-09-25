package de.tu_bs.cs.isf.e4cf.compare.data_structures.compare.view.parts;



import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TreeTable extends TableView<Tree> {
	private final static String NAME_COLUMN = "Name";
	private final static String TYPE_COLUMN = "Artifact Type";
	
	public TreeTable() {
		super();
		createColumn();
	}
	
	@SuppressWarnings("unchecked")
	private void createColumn() {
		TableColumn<Tree, String> nameColumn = new TableColumn<Tree, String>(NAME_COLUMN);
		TableColumn<Tree, String> typeColumn = new TableColumn<Tree, String>(TYPE_COLUMN);
		// Resize Policy and defined width for the columns
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		nameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 40); // 40% of the width
		nameColumn.getStyleClass().add("Times New Roman,20");
		typeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 60); // 60% of the width
		getColumns().addAll(nameColumn,typeColumn);
	}
}
