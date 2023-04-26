package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
			switch (node.getValue().getNodeType()) {
			case "DIRECTORY":
				node.setGraphic(new ImageView(DIRECTORY));
				break;
			case "CompilationUnit":
				node.setGraphic(new ImageView(JAVA));
				break;
			case "FILE":
				node.setGraphic(new ImageView(FILE));
				break;
			case "JAVA":
				node.setGraphic(new ImageView(COMPILATION_UNIT));
				break;
			case "MethodDeclaration":
				node.setGraphic(new ImageView(METHOD_DECLARATION));
				break;
			case "LineComment":
				node.setGraphic(new ImageView(LINE_COMMENT));
				break;
			case "FieldDeclaration":
				node.setGraphic(new ImageView(FILED_DECLARATION));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		node.getValue().setRepresentation(node.getValue().getNodeType());
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
