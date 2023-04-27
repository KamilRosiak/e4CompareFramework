package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.utilities.TreeViewUtilities;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class JavaDecorator implements NodeDecorator {
	public static final Image DIRECTORY = new Image("icons/refactoring/folder_16.png");
	public static final Image FILE = new Image("icons/refactoring/file_16.png");
	public static final Image JAVA = new Image("icons/refactoring/java_16.png");
	public static final Image COMPILATION_UNIT = new Image("icons/refactoring/compilation_16.png");
	public static final Image METHOD_DECLARATION = new Image("icons/refactoring/function_16.png");
	public static final Image LINE_COMMENT = new Image("icons/refactoring/comment_16.png");
	public static final Image FILED_DECLARATION = new Image("icons/refactoring/field_16.png");

	public JavaDecorator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> node) {
		try {
			HBox hbox = new HBox();
			switch (node.getValue().getVariabilityClass()) {
			case MANDATORY:
				hbox.getChildren().add(new ImageView(FileTable.FV_MANDATORY_16));
				break;
			case ALTERNATIVE:
				hbox.getChildren().add(new ImageView(FileTable.FV_ALTERNATIVE_16));
				break;
			case OPTIONAL:
				hbox.getChildren().add(new ImageView(FileTable.FV_OPTIONAL_16));
				break;
			}

			switch (node.getValue().getNodeType()) {
			case "DIRECTORY":
				hbox.getChildren().add(new ImageView(DIRECTORY));
				break;
			case "CompilationUnit":
				hbox.getChildren().add(new ImageView(COMPILATION_UNIT));
				break;
			case "FILE":
				hbox.getChildren().add(new ImageView(FILE));
				break;
			case "JAVA":
				hbox.getChildren().add(new ImageView(JAVA));
				break;
			case "MethodDeclaration":
				hbox.getChildren().add(new ImageView(METHOD_DECLARATION));
				break;
			case "LineComment":
				hbox.getChildren().add(new ImageView(LINE_COMMENT));
				break;
			case "FieldDeclaration":
				hbox.getChildren().add(new ImageView(FILED_DECLARATION));
				break;
			default:
				break;
			}
			node.setGraphic(hbox);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String detail = TreeViewUtilities.getNameDetails(node);
		if (!detail.isEmpty()) {
			node.getValue().setRepresentation(node.getValue().getNodeType() +  ": " + detail);
		} else {
			node.getValue().setRepresentation(node.getValue().getNodeType());
		}
		return node;
	}

	@Override
	public boolean isSupportedTree(Tree tree) {
		return true;
	}

	@Override
	public String toString() {
		return "Java Code";
	}

}
