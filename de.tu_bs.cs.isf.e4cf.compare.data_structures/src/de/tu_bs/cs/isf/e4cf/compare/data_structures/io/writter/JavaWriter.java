package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/**
 * 
 * This class converts the generic tree structure back into the original java
 * file.
 * 
 * @author Serkan Acar
 * @author Pascal Blum
 * @author Paulo Haas
 * @author Hassan Smaoui
 *
 */
public class JavaWriter extends AbstractArtifactWriter {
	public final static String FILE_ENDING = "java";
	public final static String NODE_TYPE_TREE = "JAVA";

	/**
	 * Initializes a new instance of class JavaWriter.
	 */
	public JavaWriter() {
		super(FILE_ENDING);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSuppotedNodeType() {
		return NODE_TYPE_TREE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeArtifact(Tree tree, String path) {
		// TODO Auto-generated method stub
		if (tree.getArtifactType().equals(NODE_TYPE_TREE)) {
			FileStreamUtil.writeTextToFile(path + "." + FILE_ENDING, generateFileContent(tree.getRoot()));
		}
	}

	/**
	 * Generates the contents of a java source file recursively.
	 * 
	 * @param root Node of the syntax tree.
	 * @return Contents of a file
	 */
	private String generateFileContent(Node root) {
		String fileContent = new String();

		// Build the file depth-first
		// If node is leaf then getChildren returns an empty list and the body of the
		// for loop is not executed.
		for (Node node : root.getChildren()) {
			fileContent += generateFileContent(node);
		}
		return fileContent;
	}
}