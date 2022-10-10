package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public final class AttributeParserCpp {
	private Node rootNode;
	
	public AttributeParserCpp(Node rootNode)
	{
		this.rootNode = rootNode;
	}
	
	
	public void parseAllAttributes()
	{
		if (rootNode == null) {
			return; 
		}
		rootNode.setNodeType("C++");
		recursiveRename(rootNode);
	}
	
	private void recursiveRename(Node node) {

		//TODO implement a good way for renaming NodeTypes to their equivalent
		RenamerCpp renamer = RenamerCpp.getInstance();
		renamer.renameNode(node);
		
		if (node.getNumberOfChildren() > 0)
		{
			List<Node> children = new ArrayList<Node>(node.getChildren());
			for (Node child : children) {
				recursiveRename(child);
			}
		}
	}
}
