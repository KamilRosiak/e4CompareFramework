package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/**
 * This class realizes the transformation of the tree data structure back into the origin format.
 * In this case its transforms a instance of the class @see Tree into an text file.
 * @author Kamil Rosiak
 *
 */
public class TextWritter extends AbstractArtifactWriter {
	public final static String FILE_ENDING = "txt";
	public final static String NODE_TYPE_TREE = "TEXT";

	public TextWritter() {
		super(FILE_ENDING);
	}

	@Override
	public void writeArtifact(Tree tree, String path) {
		//If the artifact is not of type text 
		if(!tree.getArtifactType().equals(NODE_TYPE_TREE)) {
			return;
		} else {
			FileStreamUtil.writeTextToFile(path +"."+FILE_ENDING, createFileContent(tree));
		}
	}
	
	/**
	 * This method transforms a Tree of type TEXT back into a string that represents the origin file.
	 * @param tree
	 * @return
	 */
	private String createFileContent(Tree tree) {
		String fileContent = "";
		for(Node lineNode : tree.getRoot().getChildren()) {
			for(Node wordNode : lineNode.getChildren()) {
				for(Attribute attr : wordNode.getAttributes()) {
					fileContent += attr.getAttributeValues().iterator().next() + " ";
				}
			}
			fileContent += "\n";
		}
		return fileContent;
	}

	@Override
	public String getSuppotedNodeType() {
		return NODE_TYPE_TREE;
	}

}
