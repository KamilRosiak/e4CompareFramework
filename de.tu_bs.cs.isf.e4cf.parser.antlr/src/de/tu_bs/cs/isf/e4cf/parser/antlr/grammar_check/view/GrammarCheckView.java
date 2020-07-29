package de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.GrammarCheckController;

public class GrammarCheckView {

	private GrammarCheckController<?, ?> controller;
	
	private Button analyzeBtn;
	private Button clearBtn;
	private Button tokenBtn;
	private Combo ruleCombo;
	private Text srcLanguageText;
	private Text syntaxTreeText;
	private Text statusText;

	public GrammarCheckView(GrammarCheckController<?,?> controller) {
		this.controller = controller;
	}

	public void createControls(Composite parent) {
		// main composition of parts
		Composite mainComp = new Composite(parent, SWT.NONE);
		mainComp.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, true));
		mainComp.setLayout(new GridLayout(5, true));
		
		Composite leftComp = new Composite(mainComp, SWT.NONE);
		GridData leftCompGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		leftCompGD.horizontalSpan = 2;
		leftComp.setLayoutData(leftCompGD);
		leftComp.setLayout(new GridLayout(1, true));
		
		Composite rightComp = new Composite(mainComp, SWT.NONE);
		GridData rightCompGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		rightCompGD.horizontalSpan = 3;
		rightComp.setLayoutData(rightCompGD);
		rightComp.setLayout(new GridLayout(1, true));
		
		// left column, first row
		Composite btnComp = new Composite(leftComp, SWT.NONE);
		GridData btnCompGD = new GridData(SWT.FILL, SWT.FILL, true, false);
		btnCompGD.horizontalSpan = 1;
		btnComp.setLayoutData(btnCompGD);
		btnComp.setLayout(new GridLayout(3, true));
		
		analyzeBtn = new Button(btnComp, SWT.PUSH);
		analyzeBtn.setText("Parse");
		analyzeBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		
		tokenBtn = new Button(btnComp, SWT.PUSH);
		tokenBtn.setText("Show Tokens");
		tokenBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));;
		
		clearBtn = new Button(btnComp, SWT.PUSH);
		clearBtn.setText("Clear");
		clearBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		//
		
		// space filling label
		new Label(leftComp, SWT.NONE);
		
		// left column, second row
		Group parserInputGroup = new Group(leftComp, SWT.NONE);
		parserInputGroup.setText("Parser Input");
		parserInputGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parserInputGroup.setLayout(new GridLayout(1, true));
		
		Label ruleLabel = new Label(parserInputGroup, SWT.NONE);
		ruleLabel.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, true, false));
		ruleLabel.setText("Parser Rule");
		
		ruleCombo = new Combo(parserInputGroup, SWT.READ_ONLY);
		GridData ruleInputGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
		ruleCombo.setLayoutData(ruleInputGD);
		ruleCombo.setItems(controller.getRules());
		ruleCombo.select(0);
		
		Label srcLanguageLabel = new Label(parserInputGroup, SWT.NONE);
		srcLanguageLabel.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, true, false));
		srcLanguageLabel.setText("Source Language Input");
		
		
		srcLanguageText = new Text(parserInputGroup, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData textInputGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		srcLanguageText.setLayoutData(textInputGD);
		srcLanguageText.setToolTipText("The code written in the source language which will be parsed.");
		
		// space filling label
		new Label(leftComp, SWT.NONE);
		
		// left column, third row
		Group metaInformationGroup = new Group(leftComp, SWT.NONE);
		metaInformationGroup.setText("Status Information");
		metaInformationGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		metaInformationGroup.setLayout(new GridLayout(1, true));
		
		statusText = new Text(metaInformationGroup, SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY | SWT.WRAP);
		GridData metaTextGD = new GridData(SWT.FILL, SWT.FILL, true, false);
		statusText.setLayoutData(metaTextGD);
		statusText.append("Welcome to the Grammar Check Application. \n"
				+ "----------------------------------------------------\n"
				+ "It let's you gain insight into your ANTLR parser or lexer results. You can either \n"
				+ "1) Parse source language text according to a selected parser rule or\n"
				+ "2) Show the tokens as produced by the lexer (does not require a parser rule).\n"
				+ "The results will be displayed on the right-hand side or in another window. \n"
				+ "For 1), a reduced textual(right-hand side) and a graphical syntax tree will be shown. \n"
				+ "For 2), sequence of tokens will be shown(right-hand side). \n\n");
		
		// right column 
		syntaxTreeText = new Text(rightComp, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData syntaxTreeOutputGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		syntaxTreeText.setLayoutData(syntaxTreeOutputGD);
	}

	public Button getAnalyzeButton() {
		return analyzeBtn;
	}

	public Button getClearButton() {
		return clearBtn;
	}

	public Button getTokenButton() {
		return tokenBtn;
	}

	public Combo getRuleCombo() {
		return ruleCombo;
	}

	public Text getSoruceLanguageText() {
		return srcLanguageText;
	}

	public Text getSyntaxTreeText() {
		return syntaxTreeText;
	}

	public Text getStatusText() {
		return statusText;
	}
}
