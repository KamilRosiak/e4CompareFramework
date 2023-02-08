package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;

public class XMLVisitor extends XMLParserBaseVisitor<Node> {
	private final Map<String, NodeType> nameToType;
	
	public XMLVisitor(Map<String, NodeType> nameToType) {
		this.nameToType = nameToType;
	}
	
	@Override
	public Node visitDocument(@NotNull XMLParser.DocumentContext ctx) {
		Node rootNode = new NodeImpl(NodeType.FILE);

		Node prolog = this.visitProlog(ctx.prolog());
		Node rootTag = this.visitRoot(ctx.root());
		rootNode.addChild(prolog);
		rootNode.addChild(rootTag);

		return rootNode;
	}

	@Override
	public Node visitProlog(@NotNull XMLParser.PrologContext ctx) {
		Node prolog = new NodeImpl(NodeType.XML_METADATA);
		parseAttributes(prolog, ctx.attribute());
		return prolog;
	}

	@Override
	public Node visitRoot(@NotNull XMLParser.RootContext ctx) {
		Node model = new NodeImpl(getElementType(ctx.Name().toString()));
		addChildren(model, ctx.children);
		return model;
	}

	@Override
	public Node visitAttribute(@NotNull XMLParser.AttributeContext ctx) {
		Node attr = new NodeImpl(NodeType.XML_ATTRIBUTE);
		attr.addAttribute(parseAttribute(ctx));
		return attr;
	}

	@Override
	public Node visitElement(@NotNull XMLParser.ElementContext ctx) {
		NodeType elementType = getElementType(ctx.Name().toString());
		Node element = new NodeImpl(elementType);
		parseAttributes(element, ctx.attribute());
		return element;
	}

	@Override
	public Node visitComponent(@NotNull XMLParser.ComponentContext ctx) {
		NodeType componentType = getElementType(ctx.Name().toString());
		Node component = new NodeImpl(componentType);
		addChildren(component, ctx.children);
		return component;
	}

	@Override
	public Node visitContent(@NotNull XMLParser.ContentContext ctx) {
		Node temp = new NodeImpl(NodeType.XML_CONTENT);
		addChildren(temp, ctx.children);
		return temp;
	}

	private void parseAttributes(Node node, List<XMLParser.AttributeContext> attributes) {
		attributes.stream().map(this::parseAttribute).forEach(node::addAttribute);
	}

	private Attribute parseAttribute(XMLParser.AttributeContext ctx) {
		String key = ctx.Name().getText();
		String value = ctx.VALUE().getText();
		return new AttributeImpl(key, new StringValueImpl(value));
	}

	private NodeType getElementType(String elementName) {
		List<String> elements = nameToType.keySet().stream()
			.filter(elementName::contains)
			.collect(Collectors.toList());
		if (elements.isEmpty()) {
			System.out.println("Unhandled xml element:" + elementName);
			return NodeType.UNDEFINED;
		} else if (elements.size() == 1) {
			return nameToType.get(elements.get(0));
		} else {
			System.out.println(String.format("Multiple matches for element: %s - %s", elementName, elements.toString()));
			return NodeType.UNDEFINED;
		}
		
	}
	
	/**
	 * Visits a list of items and adds them to the given node as children.
	 * @param node The parent Node
	 * @param children The items to visit
	 */
	private void addChildren(Node node, List<ParseTree> children) {
		children.stream()
			.map(this::visit) // visit each child
			.filter(parsed -> parsed != null) // remove null entries
			.map(parsed -> new Pair<Node, Node>(node, parsed)) 
			.forEach(this::handleParsedNode); // insert as child or set attribute
	}

	/**
	 * Adds a parsed node to its parent node as a child or a property
	 */
	private void handleParsedNode(Pair<Node, Node> parentParsedPair) {
		Node parent = parentParsedPair.first;
		Node parsed = parentParsedPair.second;
		if (parsed.getStandardizedNodeType().equals(NodeType.XML_ATTRIBUTE)) {
			parent.addAttribute(parsed.getAttributes().get(0));
		} else if (parsed.getStandardizedNodeType().equals(NodeType.XML_CONTENT)) {
			parsed.getChildren().forEach(parent::addChild);
		} else {
			parent.addChild(parsed);
		}
	}

}
