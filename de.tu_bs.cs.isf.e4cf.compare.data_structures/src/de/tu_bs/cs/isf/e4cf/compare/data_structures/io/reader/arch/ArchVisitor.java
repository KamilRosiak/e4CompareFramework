package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;

public class ArchVisitor extends ArchParserGrammarBaseVisitor<Node> {

	@Override
	public Node visitArchfile(@NotNull ArchParserGrammar.ArchfileContext ctx) {
		Node root = new NodeImpl(NodeType.FILE);

		Node prolog = this.visitProlog(ctx.prolog());
		Node architecture = this.visitArchitecture(ctx.architecture());
		root.addChild(prolog);
		root.addChild(architecture);

		return root;
	}

	@Override
	public Node visitProlog(@NotNull ArchParserGrammar.PrologContext ctx) {
		Node prolog = new NodeImpl(NodeType.XML_METADATA);
		parseAttributes(prolog, ctx.attribute());
		return prolog;
	}

	@Override
	public Node visitArchitecture(@NotNull ArchParserGrammar.ArchitectureContext ctx) {
		Node architecture = new NodeImpl(NodeType.ARCH_ARCHITECTURE);
		addChildren(architecture, ctx.children);
		return architecture;
	}

	@Override
	public Node visitAttribute(@NotNull ArchParserGrammar.AttributeContext ctx) {
		Node attr = new NodeImpl(NodeType.XML_ATTRIBUTE);
		attr.addAttribute(parseAttribute(ctx));
		return attr;
	}

	@Override
	public Node visitElement(@NotNull ArchParserGrammar.ElementContext ctx) {
		NodeType elementType = getElementType(ctx.Name().toString());
		Node element = new NodeImpl(elementType);
		parseAttributes(element, ctx.attribute());
		return element;
	}

	@Override
	public Node visitComponent(@NotNull ArchParserGrammar.ComponentContext ctx) {
		Node component = new NodeImpl(NodeType.ARCH_COMPONENT);
		addChildren(component, ctx.children);
		return component;
	}

	@Override
	public Node visitContent(@NotNull ArchParserGrammar.ContentContext ctx) {
		Node temp = new NodeImpl(NodeType.XML_CONTENT);
		addChildren(temp, ctx.children);
		return temp;
	}

	private void parseAttributes(Node node, List<ArchParserGrammar.AttributeContext> attributes) {
		attributes.stream().map(this::parseAttribute).forEach(node::addAttribute);
	}

	private Attribute parseAttribute(ArchParserGrammar.AttributeContext ctx) {
		String key = ctx.Name().getText();
		String value = ctx.VALUE().getText();
		return new AttributeImpl(key, new StringValueImpl(value));
	}

	private NodeType getElementType(String elementName) {
		switch (elementName) {
		case "signals":
			return NodeType.ARCH_SIGNAL;
		case "ports":
			return NodeType.ARCH_PORT;
		case "connectors":
			return NodeType.ARCH_CONNECTOR;
		case "ownedComments":
			return NodeType.ARCH_OWNED_COMMENT;
		default:
			return NodeType.UNDEFINED;
		}
	}

	private void addChildren(Node node, List<ParseTree> children) {
		children.stream().map(this::visit).filter(parsed -> parsed != null)
				.map(parsed -> new Pair<Node, Node>(node, parsed)).forEach(this::handleParsedNode);
	}

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
