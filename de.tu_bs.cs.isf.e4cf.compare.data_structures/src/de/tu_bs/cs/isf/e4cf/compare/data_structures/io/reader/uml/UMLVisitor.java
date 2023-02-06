package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml;

import java.util.HashMap;
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

public class UMLVisitor extends UMLParserBaseVisitor<Node> {
	
	private static Map<String, NodeType> elementType = new HashMap<>();
	
	static {
		elementType.put("packagedElement", NodeType.UML_PACKAGED_ELEMENT);
		elementType.put("generalization", NodeType.UML_GENERALIZATION);
		elementType.put("elementImport", NodeType.UML_ELEMENT_IMPORT);
		elementType.put("ownedAttribute", NodeType.UML_OWNED_ATTRIBUTE);
		elementType.put("ownedEnd", NodeType.UML_OWNED_END);
		elementType.put("ownedParameter", NodeType.UML_OWNED_PARAMETER);
		elementType.put("ownedOperation", NodeType.UML_OWNED_OPERATION);
		elementType.put("ownedLiteral", NodeType.UML_OWNED_LITERAL);
		elementType.put("defaultValue", NodeType.UML_DEFAULT_VALUE);
		elementType.put("lowerValue", NodeType.UML_LOWER_VALUE);
		elementType.put("upperValue", NodeType.UML_UPPER_VALUE);
		elementType.put("type", NodeType.UML_TYPE);
		elementType.put("interfaceRealization", NodeType.UML_INTERFACE_REALIZATION);
		elementType.put("nestedClassifier", NodeType.UML_NESTED_CLASSIFIER);
		elementType.put("name", NodeType.UML_NAME);
		elementType.put("importedElement", NodeType.UML_IMPORTED_ELEMENT);
		
	}

	@Override
	public Node visitDocument(@NotNull UMLParser.DocumentContext ctx) {
		Node root = new NodeImpl(NodeType.FILE);

		Node prolog = this.visitProlog(ctx.prolog());
		Node element = this.visitRoot(ctx.root());
		root.addChild(prolog);
		root.addChild(element);

		return root;
	}

	@Override
	public Node visitProlog(@NotNull UMLParser.PrologContext ctx) {
		Node prolog = new NodeImpl(NodeType.XML_METADATA);
		parseAttributes(prolog, ctx.attribute());
		return prolog;
	}

	@Override
	public Node visitRoot(@NotNull UMLParser.RootContext ctx) {
		Node architecture = new NodeImpl(NodeType.UML_PACKAGED_ELEMENT);
		addChildren(architecture, ctx.children);
		return architecture;
	}

	@Override
	public Node visitAttribute(@NotNull UMLParser.AttributeContext ctx) {
		Node attr = new NodeImpl(NodeType.XML_ATTRIBUTE);
		attr.addAttribute(parseAttribute(ctx));
		return attr;
	}

	@Override
	public Node visitElement(@NotNull UMLParser.ElementContext ctx) {
		NodeType elementType = getElementType(ctx.Name().toString());
		Node element = new NodeImpl(elementType);
		parseAttributes(element, ctx.attribute());
		return element;
	}

	@Override
	public Node visitComponent(@NotNull UMLParser.ComponentContext ctx) {
		NodeType componentType = getElementType(ctx.Name().toString());
		Node component = new NodeImpl(componentType);
		addChildren(component, ctx.children);
		return component;
	}

	@Override
	public Node visitContent(@NotNull UMLParser.ContentContext ctx) {
		Node temp = new NodeImpl(NodeType.XML_CONTENT);
		addChildren(temp, ctx.children);
		return temp;
	}

	private void parseAttributes(Node node, List<UMLParser.AttributeContext> attributes) {
		attributes.stream().map(this::parseAttribute).forEach(node::addAttribute);
	}

	private Attribute parseAttribute(UMLParser.AttributeContext ctx) {
		String key = ctx.Name().getText();
		String value = ctx.VALUE().getText();
		return new AttributeImpl(key, new StringValueImpl(value));
	}

	private NodeType getElementType(String elementName) {
		List<String> elements = elementType.keySet().stream()
			.filter(elementName::contains)
			.collect(Collectors.toList());
		if (elements.isEmpty()) {
			System.out.println("Unhandeled xml element:" + elementName);
			return NodeType.UNDEFINED;
		} else if (elements.size() == 1) {
			return elementType.get(elements.get(0));
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
			.map(this::visit)
			.filter(parsed -> parsed != null)
			.map(parsed -> new Pair<Node, Node>(node, parsed))
			.forEach(this::handleParsedNode);
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
