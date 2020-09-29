package de.tu_bs.cs.isf.e4cf.compare.data_structures.writter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.reader.TextReader;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

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
			FileStreamUtil.writeTextToFile(path, createFileContent(tree));
		}
	}
	
	private String createFileContent(Tree tree) {
		String fileContent = "";
		for(Node lineNode : tree.getRoot().getChildren()) {
			for(Node wordNode : lineNode.getChildren()) {
				System.out.println(wordNode);
				fileContent += wordNode.getAttributesForKey(TextReader.VALUE_TYPE_TEXT).getValues().get(0)+ " ";
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
