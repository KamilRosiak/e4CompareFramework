package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

import org.antlr.v4.runtime.misc.NotNull;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class ArchListenerImpl extends ArchParserGrammarBaseListener {
	
	private Node root;
	private Node architecture;
	
	public ArchListenerImpl() {
		root = new NodeImpl(NodeType.ARCH_FILE);
	}
	
	public Node getRootNode() {
		return root;
	}
	
	@Override
	public void enterProlog(@NotNull ArchParserGrammar.PrologContext ctx) {
		Node prologNode = new NodeImpl(NodeType.XML_METADATA);
		ctx.attribute().forEach(attr -> {
			Value<String> value = new StringValueImpl(attr.VALUE().getText());
			prologNode.addAttribute(attr.Name().getText(), value);
		});
		root.addChild(prologNode);
	}
	
	@Override 
	public void enterElement(@NotNull ArchParserGrammar.ElementContext ctx) {
		
	}
	
	private void handleArchitecture(ArchParserGrammar.ElementContext ctx) {
		
	}
	
	private void handleSignal(ArchParserGrammar.ElementContext ctx) {
		
	}
	
	private void handleComponent(ArchParserGrammar.ElementContext ctx) {
		
	}
	
	private void handlePort(ArchParserGrammar.ElementContext ctx) {
		
	}

}
