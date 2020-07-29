package de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.behaviour;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.GrammarCheckController;
import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.GrammarCheckView;
import de.tu_bs.cs.isf.e4cf.parser.antlr.util.AST;

public class AnalyzeBehaviour<L extends Lexer, P extends Parser> implements SelectionListener {

	private GrammarCheckController<L, P> controller;
	
	public AnalyzeBehaviour(GrammarCheckController<L, P> grammarCheckView) {
		this.controller = grammarCheckView; 
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		GrammarCheckView view = controller.getGrammarCheckView();
		
		String srcString = view.getSoruceLanguageText().getText();
		String rule = view.getRuleCombo().getText();
		
		// for valid input, compile the source language string
		if (srcString.trim().length() > 0 && rule.trim().length() > 0) {	
			ParseTree tree = controller.compile(rule, srcString);
								
			if (tree != null) {
				AST simpleSyntaxTree = new AST(tree);
				if (simpleSyntaxTree != null) {
					view.getSyntaxTreeText().setText(simpleSyntaxTree.toString());
				}
			}
			
			// build graphical representation
			P parser = controller.getParser();
			TreeViewer parserTreeGraph = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
			parserTreeGraph.setScale(1.5f);
			JScrollPane scrollable = new JScrollPane(parserTreeGraph);
			scrollable.getHorizontalScrollBar().setUnitIncrement(20);
			scrollable.getVerticalScrollBar().setUnitIncrement(20);
			
			JFrame window = new JFrame("Grammar Parser Demo - Complete Syntax Tree");
			window.add(scrollable);
			window.setSize(controller.getWindowWidth(), controller.getWindowHeight());
			window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			window.setVisible(true);
			
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

}
