package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

import org.antlr.v4.runtime.misc.NotNull;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ArchVisitor extends ArchParserGrammarBaseVisitor<Node> {
	
	@Override public Node visitArchfile(@NotNull ArchParserGrammar.ArchfileContext ctx) {
		System.out.println("archfile");
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
		System.out.println("element: " + ctx.Name().toString());
		return visitChildren(ctx); 
	}
	

}
