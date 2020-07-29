package de.tu_bs.cs.isf.e4cf.featuremodel.model.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import ConstraintGrammar.ConstraintGrammarLexer;
import ConstraintGrammar.ConstraintGrammarParser;
import CrossTreeConstraints.AbstractConstraint;
import de.tu_bs.cs.isf.e4cf.featuremodel.model.parser.handler.ConstraintErrorListener;

public class ConstraintParser {
	private ConstraintErrorListener errorListener = new ConstraintErrorListener();

	public AbstractConstraint parserConstraint(String text) {
		ConstraintGrammarParser parser = new ConstraintGrammarParser(new CommonTokenStream(new ConstraintGrammarLexer(CharStreams.fromString(text))));
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);
		ConstraintGrammarBaseVisitorImpl visitor = new ConstraintGrammarBaseVisitorImpl();
		visitor.visit(parser.formula());
		return visitor.getConstraint();
	}
	
	public void resetErrors() {
		errorListener.getErrorList().clear();
	}
	
	public ConstraintErrorListener getErrorListener() {
		return errorListener;
	}

	public void setErrorListener(ConstraintErrorListener errorListener) {
		this.errorListener = errorListener;
	}

}
