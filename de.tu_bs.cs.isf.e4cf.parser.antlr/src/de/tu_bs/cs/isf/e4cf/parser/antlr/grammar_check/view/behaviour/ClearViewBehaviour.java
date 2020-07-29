package de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.behaviour;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.GrammarCheckController;
import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.GrammarCheckView;

public class ClearViewBehaviour<L extends Lexer, P extends Parser> implements SelectionListener {

	private GrammarCheckController<L, P> controller;
	
	public ClearViewBehaviour(GrammarCheckController<L, P> grammarCheckView) {
		this.controller = grammarCheckView; 
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		GrammarCheckView view = controller.getGrammarCheckView();
		view.getRuleCombo().select(0);
		view.getSoruceLanguageText().setText("");
		view.getSyntaxTreeText().setText("");
		view.getStatusText().setText("");
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

}
