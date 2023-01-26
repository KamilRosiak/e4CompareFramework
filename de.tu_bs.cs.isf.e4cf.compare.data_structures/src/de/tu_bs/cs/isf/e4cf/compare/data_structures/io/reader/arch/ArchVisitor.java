package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

import org.antlr.v4.runtime.misc.NotNull;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ArchVisitor extends ArchParserGrammarBaseVisitor<Node> {
	
	@Override public Node visitArchfile(@NotNull ArchParserGrammar.ArchfileContext ctx) {
		System.out.println("archfile");
//		Node root = new NodeImpl(NodeType.ARCH_FILE);
//		
//		Node prolog = this.visitProlog(ctx.prolog());
//		Node architecture = this.visitArchitecture(ctx.architecture());
//		root.addChild(prolog);
//		root.addChild(architecture);
		
		return visitChildren(ctx); 
	}
	
	@Override public Node visitAttribute(@NotNull ArchParserGrammar.AttributeContext ctx) { 
		System.out.println("attribute");
		return visitChildren(ctx); 
	}
	
	@Override public Node visitProlog(@NotNull ArchParserGrammar.PrologContext ctx) {
		System.out.println("prolog");
		return visitChildren(ctx);
	}
	
	@Override public Node visitArchitecture(@NotNull ArchParserGrammar.ArchitectureContext ctx) {
		System.out.println("architecture");
		return visitChildren(ctx); 
	}

	@Override public Node visitElement(@NotNull ArchParserGrammar.ElementContext ctx) {
		String name = ctx.Name().toString();
		System.out.println("element: " + name);
		return visitChildren(ctx); 
	}
	
	@Override 
	public Node visitComponent(@NotNull ArchParserGrammar.ComponentContext ctx) { 
		System.out.println("component");
		return visitChildren(ctx); 
	}
	
	

}
