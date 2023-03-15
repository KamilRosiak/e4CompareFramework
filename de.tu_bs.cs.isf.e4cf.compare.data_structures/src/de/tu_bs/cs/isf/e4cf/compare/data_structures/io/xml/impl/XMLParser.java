// Generated from XMLParser.g4 by ANTLR 4.4
package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.impl;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XMLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SEA_WS=6, EQUALS=14, SPECIAL_CLOSE=11, SLASH=13, TEXT=9, CLOSE=10, VALUE=15, 
		COMMENT=1, OPEN=7, Name=16, SLASH_CLOSE=12, DTD=3, CharRef=5, XMLDeclOpen=8, 
		PI=18, WS=17, CDATA=2, EntityRef=4;
	public static final String[] tokenNames = {
		"<INVALID>", "COMMENT", "CDATA", "DTD", "EntityRef", "CharRef", "SEA_WS", 
		"'<'", "XMLDeclOpen", "TEXT", "'>'", "SPECIAL_CLOSE", "'/>'", "'/'", "'='", 
		"VALUE", "Name", "WS", "PI"
	};
	public static final int
		RULE_document = 0, RULE_prolog = 1, RULE_root = 2, RULE_component = 3, 
		RULE_content = 4, RULE_element = 5, RULE_reference = 6, RULE_attribute = 7, 
		RULE_chardata = 8, RULE_misc = 9;
	public static final String[] ruleNames = {
		"document", "prolog", "root", "component", "content", "element", "reference", 
		"attribute", "chardata", "misc"
	};

	@Override
	public String getGrammarFileName() { return "XMLParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public XMLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DocumentContext extends ParserRuleContext {
		public List<TerminalNode> SEA_WS() { return getTokens(XMLParser.SEA_WS); }
		public TerminalNode SEA_WS(int i) {
			return getToken(XMLParser.SEA_WS, i);
		}
		public TerminalNode EOF() { return getToken(XMLParser.EOF, 0); }
		public PrologContext prolog() {
			return getRuleContext(PrologContext.class,0);
		}
		public RootContext root() {
			return getRuleContext(RootContext.class,0);
		}
		public DocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_document; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitDocument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DocumentContext document() throws RecognitionException {
		DocumentContext _localctx = new DocumentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_document);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_la = _input.LA(1);
			if (_la==XMLDeclOpen) {
				{
				setState(20); prolog();
				}
			}

			setState(23); root();
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEA_WS) {
				{
				{
				setState(24); match(SEA_WS);
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30); match(EOF);
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

	public static class PrologContext extends ParserRuleContext {
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public TerminalNode SPECIAL_CLOSE() { return getToken(XMLParser.SPECIAL_CLOSE, 0); }
		public List<TerminalNode> SEA_WS() { return getTokens(XMLParser.SEA_WS); }
		public TerminalNode SEA_WS(int i) {
			return getToken(XMLParser.SEA_WS, i);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public TerminalNode XMLDeclOpen() { return getToken(XMLParser.XMLDeclOpen, 0); }
		public PrologContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prolog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitProlog(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrologContext prolog() throws RecognitionException {
		PrologContext _localctx = new PrologContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_prolog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32); match(XMLDeclOpen);
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Name) {
				{
				{
				setState(33); attribute();
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39); match(SPECIAL_CLOSE);
			setState(41); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(40); match(SEA_WS);
				}
				}
				setState(43); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SEA_WS );
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

	public static class RootContext extends ParserRuleContext {
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public List<ChardataContext> chardata() {
			return getRuleContexts(ChardataContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public ChardataContext chardata(int i) {
			return getRuleContext(ChardataContext.class,i);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public TerminalNode Name(int i) {
			return getToken(XMLParser.Name, i);
		}
		public List<TerminalNode> Name() { return getTokens(XMLParser.Name); }
		public ComponentContext component(int i) {
			return getRuleContext(ComponentContext.class,i);
		}
		public List<ComponentContext> component() {
			return getRuleContexts(ComponentContext.class);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitRoot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_root);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(45); match(OPEN);
			setState(46); match(Name);
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Name) {
				{
				{
				setState(47); attribute();
				}
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(53); match(CLOSE);
			setState(55);
			_la = _input.LA(1);
			if (_la==SEA_WS || _la==TEXT) {
				{
				setState(54); chardata();
				}
			}

			setState(66);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(59);
					switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
					case 1:
						{
						setState(57); component();
						}
						break;
					case 2:
						{
						setState(58); element();
						}
						break;
					}
					setState(62);
					_la = _input.LA(1);
					if (_la==SEA_WS || _la==TEXT) {
						{
						setState(61); chardata();
						}
					}

					}
					} 
				}
				setState(68);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(69); match(OPEN);
			setState(70); match(SLASH);
			setState(71); match(Name);
			setState(72); match(CLOSE);
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

	public static class ComponentContext extends ParserRuleContext {
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public ContentContext content() {
			return getRuleContext(ContentContext.class,0);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public TerminalNode Name(int i) {
			return getToken(XMLParser.Name, i);
		}
		public List<TerminalNode> Name() { return getTokens(XMLParser.Name); }
		public ComponentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_component; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitComponent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComponentContext component() throws RecognitionException {
		ComponentContext _localctx = new ComponentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_component);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74); match(OPEN);
			setState(75); match(Name);
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Name) {
				{
				{
				setState(76); attribute();
				}
				}
				setState(81);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(82); match(CLOSE);
			setState(83); content();
			setState(84); match(OPEN);
			setState(85); match(SLASH);
			setState(86); match(Name);
			setState(87); match(CLOSE);
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

	public static class ContentContext extends ParserRuleContext {
		public TerminalNode CDATA(int i) {
			return getToken(XMLParser.CDATA, i);
		}
		public List<TerminalNode> COMMENT() { return getTokens(XMLParser.COMMENT); }
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public List<ChardataContext> chardata() {
			return getRuleContexts(ChardataContext.class);
		}
		public List<ReferenceContext> reference() {
			return getRuleContexts(ReferenceContext.class);
		}
		public TerminalNode PI(int i) {
			return getToken(XMLParser.PI, i);
		}
		public ReferenceContext reference(int i) {
			return getRuleContext(ReferenceContext.class,i);
		}
		public ComponentContext component(int i) {
			return getRuleContext(ComponentContext.class,i);
		}
		public List<TerminalNode> PI() { return getTokens(XMLParser.PI); }
		public ChardataContext chardata(int i) {
			return getRuleContext(ChardataContext.class,i);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public TerminalNode COMMENT(int i) {
			return getToken(XMLParser.COMMENT, i);
		}
		public List<TerminalNode> CDATA() { return getTokens(XMLParser.CDATA); }
		public List<ComponentContext> component() {
			return getRuleContexts(ComponentContext.class);
		}
		public ContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_content; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContentContext content() throws RecognitionException {
		ContentContext _localctx = new ContentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_content);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			_la = _input.LA(1);
			if (_la==SEA_WS || _la==TEXT) {
				{
				setState(89); chardata();
				}
			}

			setState(105);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(98);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						setState(92); element();
						}
						break;
					case 2:
						{
						setState(93); component();
						}
						break;
					case 3:
						{
						setState(94); reference();
						}
						break;
					case 4:
						{
						setState(95); match(CDATA);
						}
						break;
					case 5:
						{
						setState(96); match(PI);
						}
						break;
					case 6:
						{
						setState(97); match(COMMENT);
						}
						break;
					}
					setState(101);
					_la = _input.LA(1);
					if (_la==SEA_WS || _la==TEXT) {
						{
						setState(100); chardata();
						}
					}

					}
					} 
				}
				setState(107);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
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

	public static class ElementContext extends ParserRuleContext {
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public TerminalNode Name() { return getToken(XMLParser.Name, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_element);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108); match(OPEN);
			setState(109); match(Name);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Name) {
				{
				{
				setState(110); attribute();
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116); match(SLASH_CLOSE);
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

	public static class ReferenceContext extends ParserRuleContext {
		public TerminalNode CharRef() { return getToken(XMLParser.CharRef, 0); }
		public TerminalNode EntityRef() { return getToken(XMLParser.EntityRef, 0); }
		public ReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceContext reference() throws RecognitionException {
		ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_reference);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			_la = _input.LA(1);
			if ( !(_la==EntityRef || _la==CharRef) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(XMLParser.VALUE, 0); }
		public TerminalNode Name() { return getToken(XMLParser.Name, 0); }
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120); match(Name);
			setState(121); match(EQUALS);
			setState(122); match(VALUE);
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

	public static class ChardataContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(XMLParser.TEXT, 0); }
		public TerminalNode SEA_WS() { return getToken(XMLParser.SEA_WS, 0); }
		public ChardataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chardata; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitChardata(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChardataContext chardata() throws RecognitionException {
		ChardataContext _localctx = new ChardataContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_chardata);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			_la = _input.LA(1);
			if ( !(_la==SEA_WS || _la==TEXT) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class MiscContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(XMLParser.COMMENT, 0); }
		public TerminalNode SEA_WS() { return getToken(XMLParser.SEA_WS, 0); }
		public TerminalNode PI() { return getToken(XMLParser.PI, 0); }
		public MiscContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_misc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitMisc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MiscContext misc() throws RecognitionException {
		MiscContext _localctx = new MiscContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_misc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << SEA_WS) | (1L << PI))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\24\u0083\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\3\2\5\2\30\n\2\3\2\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\2\3\2\3\3\3"+
		"\3\7\3%\n\3\f\3\16\3(\13\3\3\3\3\3\6\3,\n\3\r\3\16\3-\3\4\3\4\3\4\7\4"+
		"\63\n\4\f\4\16\4\66\13\4\3\4\3\4\5\4:\n\4\3\4\3\4\5\4>\n\4\3\4\5\4A\n"+
		"\4\7\4C\n\4\f\4\16\4F\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\7\5P\n\5\f"+
		"\5\16\5S\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\5\6]\n\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\5\6e\n\6\3\6\5\6h\n\6\7\6j\n\6\f\6\16\6m\13\6\3\7\3\7\3\7\7\7"+
		"r\n\7\f\7\16\7u\13\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\13\3D\2\f\2\4\6\b\n\f\16\20\22\24\2\5\3\2\6\7\4\2\b\b\13\13\5\2\3\3"+
		"\b\b\24\24\u008b\2\27\3\2\2\2\4\"\3\2\2\2\6/\3\2\2\2\bL\3\2\2\2\n\\\3"+
		"\2\2\2\fn\3\2\2\2\16x\3\2\2\2\20z\3\2\2\2\22~\3\2\2\2\24\u0080\3\2\2\2"+
		"\26\30\5\4\3\2\27\26\3\2\2\2\27\30\3\2\2\2\30\31\3\2\2\2\31\35\5\6\4\2"+
		"\32\34\7\b\2\2\33\32\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2"+
		"\36 \3\2\2\2\37\35\3\2\2\2 !\7\2\2\3!\3\3\2\2\2\"&\7\n\2\2#%\5\20\t\2"+
		"$#\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\')\3\2\2\2(&\3\2\2\2)+\7\r\2"+
		"\2*,\7\b\2\2+*\3\2\2\2,-\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\5\3\2\2\2/\60\7"+
		"\t\2\2\60\64\7\22\2\2\61\63\5\20\t\2\62\61\3\2\2\2\63\66\3\2\2\2\64\62"+
		"\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66\64\3\2\2\2\679\7\f\2\28:\5\22"+
		"\n\298\3\2\2\29:\3\2\2\2:D\3\2\2\2;>\5\b\5\2<>\5\f\7\2=;\3\2\2\2=<\3\2"+
		"\2\2>@\3\2\2\2?A\5\22\n\2@?\3\2\2\2@A\3\2\2\2AC\3\2\2\2B=\3\2\2\2CF\3"+
		"\2\2\2DE\3\2\2\2DB\3\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\t\2\2HI\7\17\2\2IJ"+
		"\7\22\2\2JK\7\f\2\2K\7\3\2\2\2LM\7\t\2\2MQ\7\22\2\2NP\5\20\t\2ON\3\2\2"+
		"\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2RT\3\2\2\2SQ\3\2\2\2TU\7\f\2\2UV\5\n\6"+
		"\2VW\7\t\2\2WX\7\17\2\2XY\7\22\2\2YZ\7\f\2\2Z\t\3\2\2\2[]\5\22\n\2\\["+
		"\3\2\2\2\\]\3\2\2\2]k\3\2\2\2^e\5\f\7\2_e\5\b\5\2`e\5\16\b\2ae\7\4\2\2"+
		"be\7\24\2\2ce\7\3\2\2d^\3\2\2\2d_\3\2\2\2d`\3\2\2\2da\3\2\2\2db\3\2\2"+
		"\2dc\3\2\2\2eg\3\2\2\2fh\5\22\n\2gf\3\2\2\2gh\3\2\2\2hj\3\2\2\2id\3\2"+
		"\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2l\13\3\2\2\2mk\3\2\2\2no\7\t\2\2os\7"+
		"\22\2\2pr\5\20\t\2qp\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2tv\3\2\2\2u"+
		"s\3\2\2\2vw\7\16\2\2w\r\3\2\2\2xy\t\2\2\2y\17\3\2\2\2z{\7\22\2\2{|\7\20"+
		"\2\2|}\7\21\2\2}\21\3\2\2\2~\177\t\3\2\2\177\23\3\2\2\2\u0080\u0081\t"+
		"\4\2\2\u0081\25\3\2\2\2\21\27\35&-\649=@DQ\\dgks";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}