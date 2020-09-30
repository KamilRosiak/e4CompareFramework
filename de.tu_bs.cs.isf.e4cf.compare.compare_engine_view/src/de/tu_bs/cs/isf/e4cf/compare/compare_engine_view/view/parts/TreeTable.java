package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.parts;



import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
		nameColumn.setCellValueFactory(new PropertyValueFactory("treeName"));
		typeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 60); // 60% of the width
		typeColumn.setCellValueFactory(new PropertyValueFactory("artifactType"));
		getColumns().addAll(nameColumn,typeColumn);
	}
	
}
