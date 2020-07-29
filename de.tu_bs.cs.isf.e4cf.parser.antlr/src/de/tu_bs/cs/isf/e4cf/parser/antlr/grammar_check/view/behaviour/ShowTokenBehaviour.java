package de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.behaviour;

import java.util.List;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.GrammarCheckController;
import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.GrammarCheckView;

public class ShowTokenBehaviour<L extends Lexer, P extends Parser> implements SelectionListener {

	private GrammarCheckController<L, P> controller;
	
	public ShowTokenBehaviour(GrammarCheckController<L, P> grammarCheckView) {
		this.controller = grammarCheckView; 
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		GrammarCheckView view = controller.getGrammarCheckView();
		
		String srcString = view.getSoruceLanguageText().getText();
		
		if (srcString.trim().length() > 0) {
			List<Token> tokens = controller.compileTokens(srcString);
			if (!tokens.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Token Stream:\n");
				sb.append("---------------\n");
				for (int i = 0; i < tokens.size(); i++) {
					Token token = tokens.get(i);
					String tokenString = token.getText()+", "+ controller.getLexer().getVocabulary().getSymbolicName(token.getType());
					sb.append((i+1)+". "+tokenString+"\n");
				}
				view.getSyntaxTreeText().setText(sb.toString());
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

}
