package de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.GrammarCheckView;
import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.behaviour.AnalyzeBehaviour;
import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.behaviour.ClearViewBehaviour;
import de.tu_bs.cs.isf.e4cf.parser.antlr.grammar_check.view.behaviour.ShowTokenBehaviour;
import de.tu_bs.cs.isf.e4cf.parser.antlr.util.AST;

public class GrammarCheckController <L extends Lexer, P extends Parser> {

	public static int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	
	private GrammarCheckView gcView;
	private L lexer;
	private P parser;
	
	private int winWidth, winHeight;
	private boolean prettyPrint;

	public GrammarCheckController(L lexer, P parser) {
		this.lexer = lexer;
		this.parser = parser;
		
		this.winWidth = WINDOW_WIDTH;
		this.winHeight = WINDOW_HEIGHT;
		this.prettyPrint = false;
	}


	public void createView(Composite parent) {
		gcView = new GrammarCheckView(this);
		gcView.createControls(parent);
	}
	
	public void attachViewBehaviour() {
		gcView.getAnalyzeButton().addSelectionListener(new AnalyzeBehaviour<L, P>(this));
		gcView.getTokenButton().addSelectionListener(new ShowTokenBehaviour<L, P>(this));
		gcView.getClearButton().addSelectionListener(new ClearViewBehaviour<L, P>(this));
	}
	
	/**
	 * Applies the grammar rule on the source input.
	 * 
	 * @param rule
	 * @param srcInput
	 * @return
	 */
	public ParseTree compile(String rule, String srcInput) {
		// start parsing
		CodePointCharStream srcInputStream = CharStreams.fromString(srcInput);
		lexer.setInputStream(srcInputStream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		parser.setTokenStream(tokenStream);
		
		ParserRuleContext parserContext = null;
		try {

			for (Method m : parser.getClass().getDeclaredMethods()) {
				if (m.getName().equalsIgnoreCase(rule)) {
					parserContext = (ParserRuleContext) m.invoke(parser);
				}
			}
		} catch (ParseCancellationException e) {
			e.printStackTrace();
		} catch (NoViableAltException e) {
			e.printStackTrace();
		} catch (RecognitionException re) {
			System.out.println("An exception has occured: "+re.getMessage());
			System.out.println("Additional ANTLRv4 messages: \n"
					+ "Offending State: " +re.getOffendingState()+"\n"
					+ "RuleContext: "+re.getCtx()+ "\n"
					+ "Offending Token: "+re.getOffendingToken());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
			
		// Visualization
		AST simpleSyntaxTree = new AST(parserContext);
		if (prettyPrint) {
			System.out.println(simpleSyntaxTree.toString());
	        
		} else {
			System.out.println(parserContext.toStringTree(parser));
		}
	
		// return parse tree
		return parserContext;
	}
	
	public List<Token> compileTokens(String srcInput) {
		CodePointCharStream srcInputStream = CharStreams.fromString(srcInput);
		lexer.setInputStream(srcInputStream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		tokenStream.fill();
		List<Token> tokens = tokenStream.getTokens();
		return tokens;
	}
	
	public String[] getRules() {
		return parser.getRuleNames();
	}
	
	public GrammarCheckController<L, P> setPrettyPrint(boolean prettyPrint) {
		this.prettyPrint  = prettyPrint;
		return this;
	}
	
	public GrammarCheckController<L, P> setWindowWidth(int width) {
		this.winWidth = width;
		return this;
	}
	
	public int getWindowWidth() {
		return winWidth;
	}
	
	public GrammarCheckController<L, P> setWindowHeight(int height) {
		this.winHeight= height;
		return this;
	}
	
	public int getWindowHeight() {
		return winHeight;
	}
	
	public GrammarCheckView getGrammarCheckView() {
		return gcView;
	}

	public L getLexer() {
		return lexer;
	}

	public P getParser() {
		return parser;
	}
}
