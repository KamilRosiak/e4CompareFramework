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
	public static final Image IMPORT = new Image("icons/refactoring/import_16.png");
	public static final Image STATEMENT = new Image("icons/refactoring/statement_16.png");
	public static final Image VARIABLE = new Image("icons/refactoring/variable_16.png");
	public static final Image CONSTRUCTOR = new Image("icons/refactoring/constructor_16.png");
	public static final Image ARGS = new Image("icons/refactoring/argument_16.png");

	// required for extensions point creation
	public JavaDecorator() {
	}

	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> node) {
		// create node image with variability and type image
		try {
			node.setGraphic(new HBox(getImageForVariability(node), getImageForType(node)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// create node image with variability and type image
		String detail = TreeViewUtilities.getNameDetails(node);
		if (!detail.isEmpty()) {
			node.getValue().setRepresentation(node.getValue().getNodeType() + ": " + detail);
		} else {
			node.getValue().setRepresentation(node.getValue().getNodeType());
		}
		return node;
	}

	/**
	 * Returns an ImageView for the nodes variability class
	 */
	private ImageView getImageForVariability(TreeItem<Node> node) {
		switch (node.getValue().getVariabilityClass()) {
		case MANDATORY:
			return new ImageView(FileTable.FV_MANDATORY_16);
		case ALTERNATIVE:
			return new ImageView(FileTable.FV_ALTERNATIVE_16);
		case OPTIONAL:
			return new ImageView(FileTable.FV_OPTIONAL_16);
		}
		return new ImageView();
	}

	/**
	 * Returns an ImageView for the nodes type
	 */
	private ImageView getImageForType(TreeItem<Node> node) {
		switch (node.getValue().getNodeType()) {
		case "DIRECTORY":
			return new ImageView(DIRECTORY);
		case "CompilationUnit":
			return new ImageView(COMPILATION_UNIT);
		case "FILE":
			return new ImageView(FILE);
		case "JAVA":
			return new ImageView(JAVA);
		case "MethodDeclaration":
			return new ImageView(METHOD_DECLARATION);
		case "LineComment":
			return new ImageView(LINE_COMMENT);
		case "FieldDeclaration":
			return new ImageView(FILED_DECLARATION);
		case "VariableDecarator":
			return new ImageView(VARIABLE);
		case "Import":
			return new ImageView(IMPORT);
		case "Imports":
			return new ImageView(IMPORT);
		case "ConstructorDeclaration":
			return new ImageView(CONSTRUCTOR);
		case "Argument":
			return new ImageView(ARGS);
		default:
			break;
		}
		return new ImageView();
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
