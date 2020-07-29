// Generated from ConstraintGrammar.g4 by ANTLR 4.7.1
package ConstraintGrammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ConstraintGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LEFT_BRACKET=1, RIGHT_BRACKET=2, NEGATION=3, AND=4, OR=5, IMPLIES=6, EQUALS=7, 
		IDENTIFER=8, WHITESPACE=9;
	public static final int
		RULE_formula = 0, RULE_equals_formula = 1, RULE_implies_formula = 2, RULE_or_formula = 3, 
		RULE_and_formula = 4, RULE_primitive = 5, RULE_negation = 6, RULE_literal = 7;
	public static final String[] ruleNames = {
		"formula", "equals_formula", "implies_formula", "or_formula", "and_formula", 
		"primitive", "negation", "literal"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'NOT'", "'AND'", "'OR'", "'IMPLIES'", "'EQUALS'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LEFT_BRACKET", "RIGHT_BRACKET", "NEGATION", "AND", "OR", "IMPLIES", 
		"EQUALS", "IDENTIFER", "WHITESPACE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ConstraintGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ConstraintGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FormulaContext extends ParserRuleContext {
		public Equals_formulaContext equals_formula() {
			return getRuleContext(Equals_formulaContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).exitFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintGrammarVisitor ) return ((ConstraintGrammarVisitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_formula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			equals_formula();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Equals_formulaContext extends ParserRuleContext {
		public List<Implies_formulaContext> implies_formula() {
			return getRuleContexts(Implies_formulaContext.class);
		}
		public Implies_formulaContext implies_formula(int i) {
			return getRuleContext(Implies_formulaContext.class,i);
		}
		public List<TerminalNode> EQUALS() { return getTokens(ConstraintGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(ConstraintGrammarParser.EQUALS, i);
		}
		public Equals_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equals_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).enterEquals_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).exitEquals_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintGrammarVisitor ) return ((ConstraintGrammarVisitor<? extends T>)visitor).visitEquals_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Equals_formulaContext equals_formula() throws RecognitionException {
		Equals_formulaContext _localctx = new Equals_formulaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_equals_formula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			implies_formula();
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EQUALS) {
				{
				{
				setState(19);
				match(EQUALS);
				setState(20);
				implies_formula();
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Implies_formulaContext extends ParserRuleContext {
		public List<Or_formulaContext> or_formula() {
			return getRuleContexts(Or_formulaContext.class);
		}
		public Or_formulaContext or_formula(int i) {
			return getRuleContext(Or_formulaContext.class,i);
		}
		public List<TerminalNode> IMPLIES() { return getTokens(ConstraintGrammarParser.IMPLIES); }
		public TerminalNode IMPLIES(int i) {
			return getToken(ConstraintGrammarParser.IMPLIES, i);
		}
		public Implies_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implies_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).enterImplies_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).exitImplies_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintGrammarVisitor ) return ((ConstraintGrammarVisitor<? extends T>)visitor).visitImplies_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Implies_formulaContext implies_formula() throws RecognitionException {
		Implies_formulaContext _localctx = new Implies_formulaContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_implies_formula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			or_formula();
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPLIES) {
				{
				{
				setState(27);
				match(IMPLIES);
				setState(28);
				or_formula();
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Or_formulaContext extends ParserRuleContext {
		public List<And_formulaContext> and_formula() {
			return getRuleContexts(And_formulaContext.class);
		}
		public And_formulaContext and_formula(int i) {
			return getRuleContext(And_formulaContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(ConstraintGrammarParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(ConstraintGrammarParser.OR, i);
		}
		public Or_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).enterOr_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).exitOr_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintGrammarVisitor ) return ((ConstraintGrammarVisitor<? extends T>)visitor).visitOr_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Or_formulaContext or_formula() throws RecognitionException {
		Or_formulaContext _localctx = new Or_formulaContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_or_formula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			and_formula();
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(35);
				match(OR);
				setState(36);
				and_formula();
				}
				}
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class And_formulaContext extends ParserRuleContext {
		public List<PrimitiveContext> primitive() {
			return getRuleContexts(PrimitiveContext.class);
		}
		public PrimitiveContext primitive(int i) {
			return getRuleContext(PrimitiveContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(ConstraintGrammarParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(ConstraintGrammarParser.AND, i);
		}
		public And_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).enterAnd_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).exitAnd_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintGrammarVisitor ) return ((ConstraintGrammarVisitor<? extends T>)visitor).visitAnd_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final And_formulaContext and_formula() throws RecognitionException {
		And_formulaContext _localctx = new And_formulaContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_and_formula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			primitive();
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(43);
				match(AND);
				setState(44);
				primitive();
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public NegationContext negation() {
			return getRuleContext(NegationContext.class,0);
		}
		public TerminalNode LEFT_BRACKET() { return getToken(ConstraintGrammarParser.LEFT_BRACKET, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode RIGHT_BRACKET() { return getToken(ConstraintGrammarParser.RIGHT_BRACKET, 0); }
		public PrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).enterPrimitive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).exitPrimitive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintGrammarVisitor ) return ((ConstraintGrammarVisitor<? extends T>)visitor).visitPrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveContext primitive() throws RecognitionException {
		PrimitiveContext _localctx = new PrimitiveContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_primitive);
		int _la;
		try {
			setState(61);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NEGATION) {
					{
					setState(50);
					negation();
					}
				}

				setState(53);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NEGATION) {
					{
					setState(54);
					negation();
					}
				}

				{
				setState(57);
				match(LEFT_BRACKET);
				setState(58);
				formula();
				setState(59);
				match(RIGHT_BRACKET);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NegationContext extends ParserRuleContext {
		public TerminalNode NEGATION() { return getToken(ConstraintGrammarParser.NEGATION, 0); }
		public NegationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).enterNegation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).exitNegation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintGrammarVisitor ) return ((ConstraintGrammarVisitor<? extends T>)visitor).visitNegation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegationContext negation() throws RecognitionException {
		NegationContext _localctx = new NegationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_negation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(NEGATION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode IDENTIFER() { return getToken(ConstraintGrammarParser.IDENTIFER, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintGrammarListener ) ((ConstraintGrammarListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintGrammarVisitor ) return ((ConstraintGrammarVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(IDENTIFER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13F\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\3\3\3\3\3"+
		"\7\3\30\n\3\f\3\16\3\33\13\3\3\4\3\4\3\4\7\4 \n\4\f\4\16\4#\13\4\3\5\3"+
		"\5\3\5\7\5(\n\5\f\5\16\5+\13\5\3\6\3\6\3\6\7\6\60\n\6\f\6\16\6\63\13\6"+
		"\3\7\5\7\66\n\7\3\7\3\7\5\7:\n\7\3\7\3\7\3\7\3\7\5\7@\n\7\3\b\3\b\3\t"+
		"\3\t\3\t\2\2\n\2\4\6\b\n\f\16\20\2\2\2D\2\22\3\2\2\2\4\24\3\2\2\2\6\34"+
		"\3\2\2\2\b$\3\2\2\2\n,\3\2\2\2\f?\3\2\2\2\16A\3\2\2\2\20C\3\2\2\2\22\23"+
		"\5\4\3\2\23\3\3\2\2\2\24\31\5\6\4\2\25\26\7\t\2\2\26\30\5\6\4\2\27\25"+
		"\3\2\2\2\30\33\3\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\5\3\2\2\2\33\31"+
		"\3\2\2\2\34!\5\b\5\2\35\36\7\b\2\2\36 \5\b\5\2\37\35\3\2\2\2 #\3\2\2\2"+
		"!\37\3\2\2\2!\"\3\2\2\2\"\7\3\2\2\2#!\3\2\2\2$)\5\n\6\2%&\7\7\2\2&(\5"+
		"\n\6\2\'%\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\t\3\2\2\2+)\3\2\2\2"+
		",\61\5\f\7\2-.\7\6\2\2.\60\5\f\7\2/-\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2"+
		"\61\62\3\2\2\2\62\13\3\2\2\2\63\61\3\2\2\2\64\66\5\16\b\2\65\64\3\2\2"+
		"\2\65\66\3\2\2\2\66\67\3\2\2\2\67@\5\20\t\28:\5\16\b\298\3\2\2\29:\3\2"+
		"\2\2:;\3\2\2\2;<\7\3\2\2<=\5\2\2\2=>\7\4\2\2>@\3\2\2\2?\65\3\2\2\2?9\3"+
		"\2\2\2@\r\3\2\2\2AB\7\5\2\2B\17\3\2\2\2CD\7\n\2\2D\21\3\2\2\2\t\31!)\61"+
		"\659?";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}