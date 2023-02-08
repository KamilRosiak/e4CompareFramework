package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

public class XMIWriter extends AbstractArtifactWriter {
	public final static String NODE_TYPE_TREE = "TEXT";
	private final static String INDENT = "  ";
	private Map<NodeType, String> typeToName;
	
	public XMIWriter(String fileEnding) {
		super(fileEnding);
		this.typeToName = new HashMap<>();
	}
	
	public XMIWriter(String fileEnding, Map<NodeType, String> typeNameMap) {
		this(fileEnding);
		this.typeToName = typeNameMap;
	}
	
	@Override
	public String getSuppotedNodeType() {
		return NodeType.FILE.name();
	}

	@Override
	public void writeArtifact(Tree tree, String path) {
		String fileContent = createFileContent(tree);
		FileStreamUtil.writeTextToFile(path, fileContent);
	}
	
	private String createFileContent(Tree tree) {
		StringBuilder strBuilder = new StringBuilder();
		Deque<Node> depthStack = new ArrayDeque<>();
		Iterable<Node> nodeIterable = tree.getRoot().depthFirstSearch();
		List<Node> nodes = new LinkedList<>();
		nodeIterable.iterator().forEachRemaining(nodes::add);
		
		nodes.remove(0); // remove file node
		if (nodes.get(0).getStandardizedNodeType() == NodeType.XML_METADATA) {
			strBuilder.append(createPrologTag(nodes.get(0)));
			nodes.remove(0);
		}
		
		for (Node node : nodes) {
			// manage depth stack
			// push element onto stack
			if (depthStack.isEmpty() || depthStack.peek().getChildren().contains(node)) {
				depthStack.push(node);
			} else {
				// remove element from stack
				while (!depthStack.peek().getChildren().contains(node)) {
					Node top = depthStack.peek();
					if (top.getChildren().size() > 0) { // node has children
						// append closing tag of the element
						strBuilder.append(createIndentation(depthStack.size()) + createCloseTag(top));
					}
					depthStack.pop();
				}
				depthStack.push(node);
			}
			
			strBuilder.append(createIndentation(depthStack.size()) + createTag(node));
		}
		
		depthStack.pop();
		while (!depthStack.isEmpty()) {
			Node node = depthStack.peek();
			strBuilder.append(createIndentation(depthStack.size()) + createCloseTag(node));
			depthStack.pop();
		}
		
		return strBuilder.toString();
	}
	
	private String createIndentation(int i) {
		String indentation = "";
		for (int j = 1; j < i; j++) {
			indentation += INDENT;
		}
		return indentation;
	}
	
	private String createTag(Node node) {
		if (node.getChildren().size() > 0) {
			return createOpenTag(node);
		} else {
			return createSelfCloseTag(node);
		}
	}
	private String createPrologTag(Node node) {
		return String.format("<?%s%s?>\n", getTagName(node), createAttributes(node));
	}
	
	private String createSelfCloseTag(Node node) {
		return String.format("<%s%s/>\n", getTagName(node), createAttributes(node));
	}
	
	private String createOpenTag(Node node) {
		return String.format("<%s%s>\n", getTagName(node), createAttributes(node));
	}
	
	private String createCloseTag(Node node) {
		return String.format("</%s>\n", getTagName(node));
	}
	
	private String getTagName(Node node) {
		return this.typeToName.get(node.getStandardizedNodeType());
	}
	
	private String createAttributes(Node node) {
		String attributes = "";
		for (Attribute attr : node.getAttributes()) {
			attributes += String.format(" %s=%s", attr.getAttributeKey(), attr.getAttributeValues().get(0).getValue().toString());
		}
		return attributes;
	}

}
