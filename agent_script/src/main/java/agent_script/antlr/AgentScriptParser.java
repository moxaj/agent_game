// Generated from AgentScript.g4 by ANTLR 4.7.1
package agent_script.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AgentScriptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NAMESPACE=1, IMPORT=2, AS=3, CONSTANT=4, FUNCTION=5, END=6, IF=7, ELSE=8, 
		RETURN=9, WHILE=10, COMMA=11, LPAREN=12, RPAREN=13, DOT=14, SCOPE=15, 
		BOOLEAN=16, NUMBER=17, STRING=18, NIL=19, ASSIGN=20, LT=21, LE=22, GT=23, 
		GE=24, EQ=25, NE=26, AND=27, OR=28, NOT=29, ADD=30, SUB=31, MUL=32, DIV=33, 
		LMETA=34, RMETA=35, LNATIVE=36, RNATIVE_QUOTE=37, SYMBOL=38, COMMENT=39, 
		WHITESPACE=40, NATIVE_CHARS=41, RNATIVE=42, LNATIVE_QUOTE=43;
	public static final int
		RULE_script = 0, RULE_meta = 1, RULE_namespaceDefinition = 2, RULE_importDeclaration = 3, 
		RULE_constantDefinition = 4, RULE_functionDefinition = 5, RULE_block = 6, 
		RULE_statement = 7, RULE_assignStatement = 8, RULE_ifStatement = 9, RULE_elseIfStatement = 10, 
		RULE_elseStatement = 11, RULE_whileStatement = 12, RULE_returnStatement = 13, 
		RULE_functionCallStatement = 14, RULE_nativeStatement = 15, RULE_expression = 16;
	public static final String[] ruleNames = {
		"script", "meta", "namespaceDefinition", "importDeclaration", "constantDefinition", 
		"functionDefinition", "block", "statement", "assignStatement", "ifStatement", 
		"elseIfStatement", "elseStatement", "whileStatement", "returnStatement", 
		"functionCallStatement", "nativeStatement", "expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'namespace'", "'import'", "'as'", "'constant'", "'function'", "'end'", 
		"'if'", "'else'", "'return'", "'while'", "','", "'('", "')'", "'.'", "'::'", 
		null, null, null, "'nil'", "'='", "'<'", "'<='", "'>'", "'>='", "'=='", 
		"'!='", "'&&'", "'||'", "'!'", "'+'", "'-'", "'*'", "'/'", "'#['", "']'", 
		null, "'}'", null, null, null, null, null, "'{'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "NAMESPACE", "IMPORT", "AS", "CONSTANT", "FUNCTION", "END", "IF", 
		"ELSE", "RETURN", "WHILE", "COMMA", "LPAREN", "RPAREN", "DOT", "SCOPE", 
		"BOOLEAN", "NUMBER", "STRING", "NIL", "ASSIGN", "LT", "LE", "GT", "GE", 
		"EQ", "NE", "AND", "OR", "NOT", "ADD", "SUB", "MUL", "DIV", "LMETA", "RMETA", 
		"LNATIVE", "RNATIVE_QUOTE", "SYMBOL", "COMMENT", "WHITESPACE", "NATIVE_CHARS", 
		"RNATIVE", "LNATIVE_QUOTE"
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
	public String getGrammarFileName() { return "AgentScript.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AgentScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ScriptContext extends ParserRuleContext {
		public ImportDeclarationContext importDeclaration;
		public List<ImportDeclarationContext> importDeclarations = new ArrayList<ImportDeclarationContext>();
		public ConstantDefinitionContext constantDefinition;
		public List<ConstantDefinitionContext> constantDefinitions = new ArrayList<ConstantDefinitionContext>();
		public FunctionDefinitionContext functionDefinition;
		public List<FunctionDefinitionContext> functionDefinitions = new ArrayList<FunctionDefinitionContext>();
		public NamespaceDefinitionContext namespaceDefinition() {
			return getRuleContext(NamespaceDefinitionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(AgentScriptParser.EOF, 0); }
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public List<ConstantDefinitionContext> constantDefinition() {
			return getRuleContexts(ConstantDefinitionContext.class);
		}
		public ConstantDefinitionContext constantDefinition(int i) {
			return getRuleContext(ConstantDefinitionContext.class,i);
		}
		public List<FunctionDefinitionContext> functionDefinition() {
			return getRuleContexts(FunctionDefinitionContext.class);
		}
		public FunctionDefinitionContext functionDefinition(int i) {
			return getRuleContext(FunctionDefinitionContext.class,i);
		}
		public ScriptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_script; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitScript(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptContext script() throws RecognitionException {
		ScriptContext _localctx = new ScriptContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_script);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			namespaceDefinition();
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(35);
				((ScriptContext)_localctx).importDeclaration = importDeclaration();
				((ScriptContext)_localctx).importDeclarations.add(((ScriptContext)_localctx).importDeclaration);
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONSTANT) | (1L << FUNCTION) | (1L << LMETA))) != 0)) {
				{
				setState(43);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(41);
					((ScriptContext)_localctx).constantDefinition = constantDefinition();
					((ScriptContext)_localctx).constantDefinitions.add(((ScriptContext)_localctx).constantDefinition);
					}
					break;
				case 2:
					{
					setState(42);
					((ScriptContext)_localctx).functionDefinition = functionDefinition();
					((ScriptContext)_localctx).functionDefinitions.add(((ScriptContext)_localctx).functionDefinition);
					}
					break;
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			match(EOF);
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

	public static class MetaContext extends ParserRuleContext {
		public Token SYMBOL;
		public List<Token> metaSymbols = new ArrayList<Token>();
		public TerminalNode LMETA() { return getToken(AgentScriptParser.LMETA, 0); }
		public TerminalNode RMETA() { return getToken(AgentScriptParser.RMETA, 0); }
		public List<TerminalNode> SYMBOL() { return getTokens(AgentScriptParser.SYMBOL); }
		public TerminalNode SYMBOL(int i) {
			return getToken(AgentScriptParser.SYMBOL, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AgentScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AgentScriptParser.COMMA, i);
		}
		public MetaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_meta; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitMeta(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MetaContext meta() throws RecognitionException {
		MetaContext _localctx = new MetaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_meta);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(LMETA);
			setState(51);
			((MetaContext)_localctx).SYMBOL = match(SYMBOL);
			((MetaContext)_localctx).metaSymbols.add(((MetaContext)_localctx).SYMBOL);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(52);
				match(COMMA);
				setState(53);
				((MetaContext)_localctx).SYMBOL = match(SYMBOL);
				((MetaContext)_localctx).metaSymbols.add(((MetaContext)_localctx).SYMBOL);
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(59);
			match(RMETA);
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

	public static class NamespaceDefinitionContext extends ParserRuleContext {
		public Token nameSymbol;
		public TerminalNode NAMESPACE() { return getToken(AgentScriptParser.NAMESPACE, 0); }
		public TerminalNode SYMBOL() { return getToken(AgentScriptParser.SYMBOL, 0); }
		public MetaContext meta() {
			return getRuleContext(MetaContext.class,0);
		}
		public NamespaceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespaceDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitNamespaceDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamespaceDefinitionContext namespaceDefinition() throws RecognitionException {
		NamespaceDefinitionContext _localctx = new NamespaceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_namespaceDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LMETA) {
				{
				setState(61);
				meta();
				}
			}

			setState(64);
			match(NAMESPACE);
			setState(65);
			((NamespaceDefinitionContext)_localctx).nameSymbol = match(SYMBOL);
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

	public static class ImportDeclarationContext extends ParserRuleContext {
		public Token nameSymbol;
		public Token aliasSymbol;
		public TerminalNode IMPORT() { return getToken(AgentScriptParser.IMPORT, 0); }
		public List<TerminalNode> SYMBOL() { return getTokens(AgentScriptParser.SYMBOL); }
		public TerminalNode SYMBOL(int i) {
			return getToken(AgentScriptParser.SYMBOL, i);
		}
		public TerminalNode AS() { return getToken(AgentScriptParser.AS, 0); }
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitImportDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_importDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(IMPORT);
			setState(68);
			((ImportDeclarationContext)_localctx).nameSymbol = match(SYMBOL);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(69);
				match(AS);
				setState(70);
				((ImportDeclarationContext)_localctx).aliasSymbol = match(SYMBOL);
				}
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

	public static class ConstantDefinitionContext extends ParserRuleContext {
		public Token nameSymbol;
		public ExpressionContext valueExpression;
		public TerminalNode CONSTANT() { return getToken(AgentScriptParser.CONSTANT, 0); }
		public TerminalNode ASSIGN() { return getToken(AgentScriptParser.ASSIGN, 0); }
		public TerminalNode SYMBOL() { return getToken(AgentScriptParser.SYMBOL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MetaContext meta() {
			return getRuleContext(MetaContext.class,0);
		}
		public ConstantDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitConstantDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantDefinitionContext constantDefinition() throws RecognitionException {
		ConstantDefinitionContext _localctx = new ConstantDefinitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_constantDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LMETA) {
				{
				setState(73);
				meta();
				}
			}

			setState(76);
			match(CONSTANT);
			setState(77);
			((ConstantDefinitionContext)_localctx).nameSymbol = match(SYMBOL);
			setState(78);
			match(ASSIGN);
			setState(79);
			((ConstantDefinitionContext)_localctx).valueExpression = expression(0);
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

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public Token nameSymbol;
		public Token SYMBOL;
		public List<Token> argumentSymbols = new ArrayList<Token>();
		public TerminalNode FUNCTION() { return getToken(AgentScriptParser.FUNCTION, 0); }
		public TerminalNode LPAREN() { return getToken(AgentScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(AgentScriptParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode END() { return getToken(AgentScriptParser.END, 0); }
		public List<TerminalNode> SYMBOL() { return getTokens(AgentScriptParser.SYMBOL); }
		public TerminalNode SYMBOL(int i) {
			return getToken(AgentScriptParser.SYMBOL, i);
		}
		public MetaContext meta() {
			return getRuleContext(MetaContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(AgentScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AgentScriptParser.COMMA, i);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LMETA) {
				{
				setState(81);
				meta();
				}
			}

			setState(84);
			match(FUNCTION);
			setState(85);
			((FunctionDefinitionContext)_localctx).nameSymbol = match(SYMBOL);
			setState(86);
			match(LPAREN);
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SYMBOL) {
				{
				setState(87);
				((FunctionDefinitionContext)_localctx).SYMBOL = match(SYMBOL);
				((FunctionDefinitionContext)_localctx).argumentSymbols.add(((FunctionDefinitionContext)_localctx).SYMBOL);
				}
			}

			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(90);
				match(COMMA);
				setState(91);
				((FunctionDefinitionContext)_localctx).SYMBOL = match(SYMBOL);
				((FunctionDefinitionContext)_localctx).argumentSymbols.add(((FunctionDefinitionContext)_localctx).SYMBOL);
				}
				}
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(97);
			match(RPAREN);
			setState(98);
			block();
			setState(99);
			match(END);
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

	public static class BlockContext extends ParserRuleContext {
		public StatementContext statement;
		public List<StatementContext> statements = new ArrayList<StatementContext>();
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << RETURN) | (1L << WHILE) | (1L << LNATIVE) | (1L << SYMBOL))) != 0)) {
				{
				{
				setState(101);
				((BlockContext)_localctx).statement = statement();
				((BlockContext)_localctx).statements.add(((BlockContext)_localctx).statement);
				}
				}
				setState(106);
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

	public static class StatementContext extends ParserRuleContext {
		public AssignStatementContext assignStatement() {
			return getRuleContext(AssignStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public FunctionCallStatementContext functionCallStatement() {
			return getRuleContext(FunctionCallStatementContext.class,0);
		}
		public NativeStatementContext nativeStatement() {
			return getRuleContext(NativeStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_statement);
		try {
			setState(113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(107);
				assignStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(109);
				whileStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(110);
				returnStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(111);
				functionCallStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(112);
				nativeStatement();
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

	public static class AssignStatementContext extends ParserRuleContext {
		public Token nameSymbol;
		public ExpressionContext valueExpression;
		public TerminalNode ASSIGN() { return getToken(AgentScriptParser.ASSIGN, 0); }
		public TerminalNode SYMBOL() { return getToken(AgentScriptParser.SYMBOL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitAssignStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignStatementContext assignStatement() throws RecognitionException {
		AssignStatementContext _localctx = new AssignStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assignStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			((AssignStatementContext)_localctx).nameSymbol = match(SYMBOL);
			setState(116);
			match(ASSIGN);
			setState(117);
			((AssignStatementContext)_localctx).valueExpression = expression(0);
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

	public static class IfStatementContext extends ParserRuleContext {
		public ExpressionContext testExpression;
		public ElseIfStatementContext elseIfStatement;
		public List<ElseIfStatementContext> elseIfStatements = new ArrayList<ElseIfStatementContext>();
		public TerminalNode IF() { return getToken(AgentScriptParser.IF, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode END() { return getToken(AgentScriptParser.END, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ElseStatementContext elseStatement() {
			return getRuleContext(ElseStatementContext.class,0);
		}
		public List<ElseIfStatementContext> elseIfStatement() {
			return getRuleContexts(ElseIfStatementContext.class);
		}
		public ElseIfStatementContext elseIfStatement(int i) {
			return getRuleContext(ElseIfStatementContext.class,i);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ifStatement);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			match(IF);
			setState(120);
			((IfStatementContext)_localctx).testExpression = expression(0);
			setState(121);
			block();
			setState(125);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(122);
					((IfStatementContext)_localctx).elseIfStatement = elseIfStatement();
					((IfStatementContext)_localctx).elseIfStatements.add(((IfStatementContext)_localctx).elseIfStatement);
					}
					} 
				}
				setState(127);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(129);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(128);
				elseStatement();
				}
			}

			setState(131);
			match(END);
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

	public static class ElseIfStatementContext extends ParserRuleContext {
		public ExpressionContext testExpression;
		public TerminalNode ELSE() { return getToken(AgentScriptParser.ELSE, 0); }
		public TerminalNode IF() { return getToken(AgentScriptParser.IF, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ElseIfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseIfStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitElseIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseIfStatementContext elseIfStatement() throws RecognitionException {
		ElseIfStatementContext _localctx = new ElseIfStatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_elseIfStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(ELSE);
			setState(134);
			match(IF);
			setState(135);
			((ElseIfStatementContext)_localctx).testExpression = expression(0);
			setState(136);
			block();
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

	public static class ElseStatementContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(AgentScriptParser.ELSE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElseStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitElseStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseStatementContext elseStatement() throws RecognitionException {
		ElseStatementContext _localctx = new ElseStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_elseStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(ELSE);
			setState(139);
			block();
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

	public static class WhileStatementContext extends ParserRuleContext {
		public ExpressionContext testExpression;
		public TerminalNode WHILE() { return getToken(AgentScriptParser.WHILE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode END() { return getToken(AgentScriptParser.END, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(WHILE);
			setState(142);
			((WhileStatementContext)_localctx).testExpression = expression(0);
			setState(143);
			block();
			setState(144);
			match(END);
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

	public static class ReturnStatementContext extends ParserRuleContext {
		public ExpressionContext innerExpression;
		public TerminalNode RETURN() { return getToken(AgentScriptParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(RETURN);
			setState(147);
			((ReturnStatementContext)_localctx).innerExpression = expression(0);
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

	public static class FunctionCallStatementContext extends ParserRuleContext {
		public Token functionName;
		public ExpressionContext expression;
		public List<ExpressionContext> argumentExpressions = new ArrayList<ExpressionContext>();
		public TerminalNode LPAREN() { return getToken(AgentScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(AgentScriptParser.RPAREN, 0); }
		public TerminalNode SYMBOL() { return getToken(AgentScriptParser.SYMBOL, 0); }
		public List<TerminalNode> COMMA() { return getTokens(AgentScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AgentScriptParser.COMMA, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FunctionCallStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCallStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitFunctionCallStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallStatementContext functionCallStatement() throws RecognitionException {
		FunctionCallStatementContext _localctx = new FunctionCallStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_functionCallStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			((FunctionCallStatementContext)_localctx).functionName = match(SYMBOL);
			setState(150);
			match(LPAREN);
			setState(152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << BOOLEAN) | (1L << NUMBER) | (1L << STRING) | (1L << NIL) | (1L << NOT) | (1L << ADD) | (1L << SUB) | (1L << LNATIVE) | (1L << SYMBOL))) != 0)) {
				{
				setState(151);
				((FunctionCallStatementContext)_localctx).expression = expression(0);
				((FunctionCallStatementContext)_localctx).argumentExpressions.add(((FunctionCallStatementContext)_localctx).expression);
				}
			}

			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(154);
				match(COMMA);
				setState(155);
				((FunctionCallStatementContext)_localctx).expression = expression(0);
				((FunctionCallStatementContext)_localctx).argumentExpressions.add(((FunctionCallStatementContext)_localctx).expression);
				}
				}
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(161);
			match(RPAREN);
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

	public static class NativeStatementContext extends ParserRuleContext {
		public Token NATIVE_CHARS;
		public List<Token> preTokens = new ArrayList<Token>();
		public ExpressionContext expression;
		public List<ExpressionContext> expressions = new ArrayList<ExpressionContext>();
		public List<Token> postTokens = new ArrayList<Token>();
		public TerminalNode LNATIVE() { return getToken(AgentScriptParser.LNATIVE, 0); }
		public TerminalNode RNATIVE() { return getToken(AgentScriptParser.RNATIVE, 0); }
		public List<TerminalNode> LNATIVE_QUOTE() { return getTokens(AgentScriptParser.LNATIVE_QUOTE); }
		public TerminalNode LNATIVE_QUOTE(int i) {
			return getToken(AgentScriptParser.LNATIVE_QUOTE, i);
		}
		public List<TerminalNode> RNATIVE_QUOTE() { return getTokens(AgentScriptParser.RNATIVE_QUOTE); }
		public TerminalNode RNATIVE_QUOTE(int i) {
			return getToken(AgentScriptParser.RNATIVE_QUOTE, i);
		}
		public List<TerminalNode> NATIVE_CHARS() { return getTokens(AgentScriptParser.NATIVE_CHARS); }
		public TerminalNode NATIVE_CHARS(int i) {
			return getToken(AgentScriptParser.NATIVE_CHARS, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NativeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nativeStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitNativeStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NativeStatementContext nativeStatement() throws RecognitionException {
		NativeStatementContext _localctx = new NativeStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_nativeStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(LNATIVE);
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NATIVE_CHARS) {
				{
				setState(164);
				((NativeStatementContext)_localctx).NATIVE_CHARS = match(NATIVE_CHARS);
				((NativeStatementContext)_localctx).preTokens.add(((NativeStatementContext)_localctx).NATIVE_CHARS);
				}
			}

			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LNATIVE_QUOTE) {
				{
				{
				setState(167);
				match(LNATIVE_QUOTE);
				setState(168);
				((NativeStatementContext)_localctx).expression = expression(0);
				((NativeStatementContext)_localctx).expressions.add(((NativeStatementContext)_localctx).expression);
				setState(169);
				match(RNATIVE_QUOTE);
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NATIVE_CHARS) {
					{
					setState(170);
					((NativeStatementContext)_localctx).NATIVE_CHARS = match(NATIVE_CHARS);
					((NativeStatementContext)_localctx).postTokens.add(((NativeStatementContext)_localctx).NATIVE_CHARS);
					}
				}

				}
				}
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(178);
			match(RNATIVE);
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

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParenWrappedExpressionContext extends ExpressionContext {
		public ExpressionContext innerExpression;
		public TerminalNode LPAREN() { return getToken(AgentScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(AgentScriptParser.RPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenWrappedExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitParenWrappedExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryMathExpressionContext extends ExpressionContext {
		public Token operator;
		public ExpressionContext innerExpression;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ADD() { return getToken(AgentScriptParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(AgentScriptParser.SUB, 0); }
		public UnaryMathExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitUnaryMathExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelationExpressionContext extends ExpressionContext {
		public ExpressionContext leftExpression;
		public Token operator;
		public ExpressionContext rightExpression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LT() { return getToken(AgentScriptParser.LT, 0); }
		public TerminalNode LE() { return getToken(AgentScriptParser.LE, 0); }
		public TerminalNode GT() { return getToken(AgentScriptParser.GT, 0); }
		public TerminalNode GE() { return getToken(AgentScriptParser.GE, 0); }
		public RelationExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitRelationExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExpressionContext extends ExpressionContext {
		public ExpressionContext leftExpression;
		public Token operator;
		public ExpressionContext rightExpression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(AgentScriptParser.OR, 0); }
		public OrExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SymbolExpressionContext extends ExpressionContext {
		public Token symbol;
		public TerminalNode SYMBOL() { return getToken(AgentScriptParser.SYMBOL, 0); }
		public SymbolExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitSymbolExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndExpressionContext extends ExpressionContext {
		public ExpressionContext leftExpression;
		public Token operator;
		public ExpressionContext rightExpression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(AgentScriptParser.AND, 0); }
		public AndExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NativeExpressionContext extends ExpressionContext {
		public Token NATIVE_CHARS;
		public List<Token> preTokens = new ArrayList<Token>();
		public ExpressionContext expression;
		public List<ExpressionContext> expressions = new ArrayList<ExpressionContext>();
		public List<Token> postTokens = new ArrayList<Token>();
		public TerminalNode LNATIVE() { return getToken(AgentScriptParser.LNATIVE, 0); }
		public TerminalNode RNATIVE() { return getToken(AgentScriptParser.RNATIVE, 0); }
		public List<TerminalNode> LNATIVE_QUOTE() { return getTokens(AgentScriptParser.LNATIVE_QUOTE); }
		public TerminalNode LNATIVE_QUOTE(int i) {
			return getToken(AgentScriptParser.LNATIVE_QUOTE, i);
		}
		public List<TerminalNode> RNATIVE_QUOTE() { return getTokens(AgentScriptParser.RNATIVE_QUOTE); }
		public TerminalNode RNATIVE_QUOTE(int i) {
			return getToken(AgentScriptParser.RNATIVE_QUOTE, i);
		}
		public List<TerminalNode> NATIVE_CHARS() { return getTokens(AgentScriptParser.NATIVE_CHARS); }
		public TerminalNode NATIVE_CHARS(int i) {
			return getToken(AgentScriptParser.NATIVE_CHARS, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NativeExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitNativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualityExpressionContext extends ExpressionContext {
		public ExpressionContext leftExpression;
		public Token operator;
		public ExpressionContext rightExpression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EQ() { return getToken(AgentScriptParser.EQ, 0); }
		public TerminalNode NE() { return getToken(AgentScriptParser.NE, 0); }
		public EqualityExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionCallExpressionContext extends ExpressionContext {
		public Token functionName;
		public ExpressionContext expression;
		public List<ExpressionContext> argumentExpressions = new ArrayList<ExpressionContext>();
		public TerminalNode LPAREN() { return getToken(AgentScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(AgentScriptParser.RPAREN, 0); }
		public TerminalNode SYMBOL() { return getToken(AgentScriptParser.SYMBOL, 0); }
		public List<TerminalNode> COMMA() { return getTokens(AgentScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AgentScriptParser.COMMA, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FunctionCallExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitFunctionCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NilExpressionContext extends ExpressionContext {
		public TerminalNode NIL() { return getToken(AgentScriptParser.NIL, 0); }
		public NilExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitNilExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralExpressionContext extends ExpressionContext {
		public TerminalNode BOOLEAN() { return getToken(AgentScriptParser.BOOLEAN, 0); }
		public TerminalNode NUMBER() { return getToken(AgentScriptParser.NUMBER, 0); }
		public TerminalNode STRING() { return getToken(AgentScriptParser.STRING, 0); }
		public LiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryMathExpressionContext extends ExpressionContext {
		public ExpressionContext leftExpression;
		public Token operator;
		public ExpressionContext rightExpression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL() { return getToken(AgentScriptParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(AgentScriptParser.DIV, 0); }
		public TerminalNode ADD() { return getToken(AgentScriptParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(AgentScriptParser.SUB, 0); }
		public BinaryMathExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitBinaryMathExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryBooleanExpressionContext extends ExpressionContext {
		public Token operator;
		public ExpressionContext innerExpression;
		public TerminalNode NOT() { return getToken(AgentScriptParser.NOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryBooleanExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AgentScriptVisitor ) return ((AgentScriptVisitor<? extends T>)visitor).visitUnaryBooleanExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				_localctx = new ParenWrappedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(181);
				match(LPAREN);
				setState(182);
				((ParenWrappedExpressionContext)_localctx).innerExpression = expression(0);
				setState(183);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(185);
				match(BOOLEAN);
				}
				break;
			case 3:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(186);
				match(NUMBER);
				}
				break;
			case 4:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(187);
				match(STRING);
				}
				break;
			case 5:
				{
				_localctx = new NilExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(188);
				match(NIL);
				}
				break;
			case 6:
				{
				_localctx = new NativeExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(189);
				match(LNATIVE);
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NATIVE_CHARS) {
					{
					setState(190);
					((NativeExpressionContext)_localctx).NATIVE_CHARS = match(NATIVE_CHARS);
					((NativeExpressionContext)_localctx).preTokens.add(((NativeExpressionContext)_localctx).NATIVE_CHARS);
					}
				}

				setState(201);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LNATIVE_QUOTE) {
					{
					{
					setState(193);
					match(LNATIVE_QUOTE);
					setState(194);
					((NativeExpressionContext)_localctx).expression = expression(0);
					((NativeExpressionContext)_localctx).expressions.add(((NativeExpressionContext)_localctx).expression);
					setState(195);
					match(RNATIVE_QUOTE);
					setState(197);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==NATIVE_CHARS) {
						{
						setState(196);
						((NativeExpressionContext)_localctx).NATIVE_CHARS = match(NATIVE_CHARS);
						((NativeExpressionContext)_localctx).postTokens.add(((NativeExpressionContext)_localctx).NATIVE_CHARS);
						}
					}

					}
					}
					setState(203);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(204);
				match(RNATIVE);
				}
				break;
			case 7:
				{
				_localctx = new FunctionCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(205);
				((FunctionCallExpressionContext)_localctx).functionName = match(SYMBOL);
				setState(206);
				match(LPAREN);
				setState(208);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << BOOLEAN) | (1L << NUMBER) | (1L << STRING) | (1L << NIL) | (1L << NOT) | (1L << ADD) | (1L << SUB) | (1L << LNATIVE) | (1L << SYMBOL))) != 0)) {
					{
					setState(207);
					((FunctionCallExpressionContext)_localctx).expression = expression(0);
					((FunctionCallExpressionContext)_localctx).argumentExpressions.add(((FunctionCallExpressionContext)_localctx).expression);
					}
				}

				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(210);
					match(COMMA);
					setState(211);
					((FunctionCallExpressionContext)_localctx).expression = expression(0);
					((FunctionCallExpressionContext)_localctx).argumentExpressions.add(((FunctionCallExpressionContext)_localctx).expression);
					}
					}
					setState(216);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(217);
				match(RPAREN);
				}
				break;
			case 8:
				{
				_localctx = new SymbolExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(218);
				((SymbolExpressionContext)_localctx).symbol = match(SYMBOL);
				}
				break;
			case 9:
				{
				_localctx = new UnaryBooleanExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(219);
				((UnaryBooleanExpressionContext)_localctx).operator = match(NOT);
				setState(220);
				((UnaryBooleanExpressionContext)_localctx).innerExpression = expression(8);
				}
				break;
			case 10:
				{
				_localctx = new UnaryMathExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(221);
				((UnaryMathExpressionContext)_localctx).operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
					((UnaryMathExpressionContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(222);
				((UnaryMathExpressionContext)_localctx).innerExpression = expression(7);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(245);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(243);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryMathExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryMathExpressionContext)_localctx).leftExpression = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(225);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(226);
						((BinaryMathExpressionContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((BinaryMathExpressionContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(227);
						((BinaryMathExpressionContext)_localctx).rightExpression = expression(7);
						}
						break;
					case 2:
						{
						_localctx = new BinaryMathExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryMathExpressionContext)_localctx).leftExpression = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(228);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(229);
						((BinaryMathExpressionContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((BinaryMathExpressionContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(230);
						((BinaryMathExpressionContext)_localctx).rightExpression = expression(6);
						}
						break;
					case 3:
						{
						_localctx = new RelationExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((RelationExpressionContext)_localctx).leftExpression = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(231);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(232);
						((RelationExpressionContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << LE) | (1L << GT) | (1L << GE))) != 0)) ) {
							((RelationExpressionContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(233);
						((RelationExpressionContext)_localctx).rightExpression = expression(5);
						}
						break;
					case 4:
						{
						_localctx = new EqualityExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((EqualityExpressionContext)_localctx).leftExpression = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(234);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(235);
						((EqualityExpressionContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NE) ) {
							((EqualityExpressionContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(236);
						((EqualityExpressionContext)_localctx).rightExpression = expression(4);
						}
						break;
					case 5:
						{
						_localctx = new AndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((AndExpressionContext)_localctx).leftExpression = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(237);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(238);
						((AndExpressionContext)_localctx).operator = match(AND);
						setState(239);
						((AndExpressionContext)_localctx).rightExpression = expression(3);
						}
						break;
					case 6:
						{
						_localctx = new OrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((OrExpressionContext)_localctx).leftExpression = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(240);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(241);
						((OrExpressionContext)_localctx).operator = match(OR);
						setState(242);
						((OrExpressionContext)_localctx).rightExpression = expression(2);
						}
						break;
					}
					} 
				}
				setState(247);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 16:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 3);
		case 4:
			return precpred(_ctx, 2);
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3-\u00fb\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\7\2\'\n\2\f\2\16\2*\13\2\3\2\3\2\7\2.\n\2\f\2\16\2\61\13\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\7\39\n\3\f\3\16\3<\13\3\3\3\3\3\3\4\5\4A\n\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5J\n\5\3\6\5\6M\n\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\7\5\7U\n\7\3\7\3\7\3\7\3\7\5\7[\n\7\3\7\3\7\7\7_\n\7\f\7\16\7b\13\7\3"+
		"\7\3\7\3\7\3\7\3\b\7\bi\n\b\f\b\16\bl\13\b\3\t\3\t\3\t\3\t\3\t\3\t\5\t"+
		"t\n\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\7\13~\n\13\f\13\16\13\u0081"+
		"\13\13\3\13\5\13\u0084\n\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\5\20\u009b\n\20"+
		"\3\20\3\20\7\20\u009f\n\20\f\20\16\20\u00a2\13\20\3\20\3\20\3\21\3\21"+
		"\5\21\u00a8\n\21\3\21\3\21\3\21\3\21\5\21\u00ae\n\21\7\21\u00b0\n\21\f"+
		"\21\16\21\u00b3\13\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\5\22\u00c2\n\22\3\22\3\22\3\22\3\22\5\22\u00c8\n\22\7"+
		"\22\u00ca\n\22\f\22\16\22\u00cd\13\22\3\22\3\22\3\22\3\22\5\22\u00d3\n"+
		"\22\3\22\3\22\7\22\u00d7\n\22\f\22\16\22\u00da\13\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\5\22\u00e2\n\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\7\22\u00f6\n\22\f\22"+
		"\16\22\u00f9\13\22\3\22\2\3\"\23\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"\2\6\3\2 !\3\2\"#\3\2\27\32\3\2\33\34\2\u0114\2$\3\2\2\2\4\64\3\2\2"+
		"\2\6@\3\2\2\2\bE\3\2\2\2\nL\3\2\2\2\fT\3\2\2\2\16j\3\2\2\2\20s\3\2\2\2"+
		"\22u\3\2\2\2\24y\3\2\2\2\26\u0087\3\2\2\2\30\u008c\3\2\2\2\32\u008f\3"+
		"\2\2\2\34\u0094\3\2\2\2\36\u0097\3\2\2\2 \u00a5\3\2\2\2\"\u00e1\3\2\2"+
		"\2$(\5\6\4\2%\'\5\b\5\2&%\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)/\3\2"+
		"\2\2*(\3\2\2\2+.\5\n\6\2,.\5\f\7\2-+\3\2\2\2-,\3\2\2\2.\61\3\2\2\2/-\3"+
		"\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\7\2\2\3\63\3\3\2\2"+
		"\2\64\65\7$\2\2\65:\7(\2\2\66\67\7\r\2\2\679\7(\2\28\66\3\2\2\29<\3\2"+
		"\2\2:8\3\2\2\2:;\3\2\2\2;=\3\2\2\2<:\3\2\2\2=>\7%\2\2>\5\3\2\2\2?A\5\4"+
		"\3\2@?\3\2\2\2@A\3\2\2\2AB\3\2\2\2BC\7\3\2\2CD\7(\2\2D\7\3\2\2\2EF\7\4"+
		"\2\2FI\7(\2\2GH\7\5\2\2HJ\7(\2\2IG\3\2\2\2IJ\3\2\2\2J\t\3\2\2\2KM\5\4"+
		"\3\2LK\3\2\2\2LM\3\2\2\2MN\3\2\2\2NO\7\6\2\2OP\7(\2\2PQ\7\26\2\2QR\5\""+
		"\22\2R\13\3\2\2\2SU\5\4\3\2TS\3\2\2\2TU\3\2\2\2UV\3\2\2\2VW\7\7\2\2WX"+
		"\7(\2\2XZ\7\16\2\2Y[\7(\2\2ZY\3\2\2\2Z[\3\2\2\2[`\3\2\2\2\\]\7\r\2\2]"+
		"_\7(\2\2^\\\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ac\3\2\2\2b`\3\2\2\2"+
		"cd\7\17\2\2de\5\16\b\2ef\7\b\2\2f\r\3\2\2\2gi\5\20\t\2hg\3\2\2\2il\3\2"+
		"\2\2jh\3\2\2\2jk\3\2\2\2k\17\3\2\2\2lj\3\2\2\2mt\5\22\n\2nt\5\24\13\2"+
		"ot\5\32\16\2pt\5\34\17\2qt\5\36\20\2rt\5 \21\2sm\3\2\2\2sn\3\2\2\2so\3"+
		"\2\2\2sp\3\2\2\2sq\3\2\2\2sr\3\2\2\2t\21\3\2\2\2uv\7(\2\2vw\7\26\2\2w"+
		"x\5\"\22\2x\23\3\2\2\2yz\7\t\2\2z{\5\"\22\2{\177\5\16\b\2|~\5\26\f\2}"+
		"|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0083\3"+
		"\2\2\2\u0081\177\3\2\2\2\u0082\u0084\5\30\r\2\u0083\u0082\3\2\2\2\u0083"+
		"\u0084\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086\7\b\2\2\u0086\25\3\2\2"+
		"\2\u0087\u0088\7\n\2\2\u0088\u0089\7\t\2\2\u0089\u008a\5\"\22\2\u008a"+
		"\u008b\5\16\b\2\u008b\27\3\2\2\2\u008c\u008d\7\n\2\2\u008d\u008e\5\16"+
		"\b\2\u008e\31\3\2\2\2\u008f\u0090\7\f\2\2\u0090\u0091\5\"\22\2\u0091\u0092"+
		"\5\16\b\2\u0092\u0093\7\b\2\2\u0093\33\3\2\2\2\u0094\u0095\7\13\2\2\u0095"+
		"\u0096\5\"\22\2\u0096\35\3\2\2\2\u0097\u0098\7(\2\2\u0098\u009a\7\16\2"+
		"\2\u0099\u009b\5\"\22\2\u009a\u0099\3\2\2\2\u009a\u009b\3\2\2\2\u009b"+
		"\u00a0\3\2\2\2\u009c\u009d\7\r\2\2\u009d\u009f\5\"\22\2\u009e\u009c\3"+
		"\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1"+
		"\u00a3\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a4\7\17\2\2\u00a4\37\3\2\2"+
		"\2\u00a5\u00a7\7&\2\2\u00a6\u00a8\7+\2\2\u00a7\u00a6\3\2\2\2\u00a7\u00a8"+
		"\3\2\2\2\u00a8\u00b1\3\2\2\2\u00a9\u00aa\7-\2\2\u00aa\u00ab\5\"\22\2\u00ab"+
		"\u00ad\7\'\2\2\u00ac\u00ae\7+\2\2\u00ad\u00ac\3\2\2\2\u00ad\u00ae\3\2"+
		"\2\2\u00ae\u00b0\3\2\2\2\u00af\u00a9\3\2\2\2\u00b0\u00b3\3\2\2\2\u00b1"+
		"\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b4\3\2\2\2\u00b3\u00b1\3\2"+
		"\2\2\u00b4\u00b5\7,\2\2\u00b5!\3\2\2\2\u00b6\u00b7\b\22\1\2\u00b7\u00b8"+
		"\7\16\2\2\u00b8\u00b9\5\"\22\2\u00b9\u00ba\7\17\2\2\u00ba\u00e2\3\2\2"+
		"\2\u00bb\u00e2\7\22\2\2\u00bc\u00e2\7\23\2\2\u00bd\u00e2\7\24\2\2\u00be"+
		"\u00e2\7\25\2\2\u00bf\u00c1\7&\2\2\u00c0\u00c2\7+\2\2\u00c1\u00c0\3\2"+
		"\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00cb\3\2\2\2\u00c3\u00c4\7-\2\2\u00c4"+
		"\u00c5\5\"\22\2\u00c5\u00c7\7\'\2\2\u00c6\u00c8\7+\2\2\u00c7\u00c6\3\2"+
		"\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00ca\3\2\2\2\u00c9\u00c3\3\2\2\2\u00ca"+
		"\u00cd\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ce\3\2"+
		"\2\2\u00cd\u00cb\3\2\2\2\u00ce\u00e2\7,\2\2\u00cf\u00d0\7(\2\2\u00d0\u00d2"+
		"\7\16\2\2\u00d1\u00d3\5\"\22\2\u00d2\u00d1\3\2\2\2\u00d2\u00d3\3\2\2\2"+
		"\u00d3\u00d8\3\2\2\2\u00d4\u00d5\7\r\2\2\u00d5\u00d7\5\"\22\2\u00d6\u00d4"+
		"\3\2\2\2\u00d7\u00da\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9"+
		"\u00db\3\2\2\2\u00da\u00d8\3\2\2\2\u00db\u00e2\7\17\2\2\u00dc\u00e2\7"+
		"(\2\2\u00dd\u00de\7\37\2\2\u00de\u00e2\5\"\22\n\u00df\u00e0\t\2\2\2\u00e0"+
		"\u00e2\5\"\22\t\u00e1\u00b6\3\2\2\2\u00e1\u00bb\3\2\2\2\u00e1\u00bc\3"+
		"\2\2\2\u00e1\u00bd\3\2\2\2\u00e1\u00be\3\2\2\2\u00e1\u00bf\3\2\2\2\u00e1"+
		"\u00cf\3\2\2\2\u00e1\u00dc\3\2\2\2\u00e1\u00dd\3\2\2\2\u00e1\u00df\3\2"+
		"\2\2\u00e2\u00f7\3\2\2\2\u00e3\u00e4\f\b\2\2\u00e4\u00e5\t\3\2\2\u00e5"+
		"\u00f6\5\"\22\t\u00e6\u00e7\f\7\2\2\u00e7\u00e8\t\2\2\2\u00e8\u00f6\5"+
		"\"\22\b\u00e9\u00ea\f\6\2\2\u00ea\u00eb\t\4\2\2\u00eb\u00f6\5\"\22\7\u00ec"+
		"\u00ed\f\5\2\2\u00ed\u00ee\t\5\2\2\u00ee\u00f6\5\"\22\6\u00ef\u00f0\f"+
		"\4\2\2\u00f0\u00f1\7\35\2\2\u00f1\u00f6\5\"\22\5\u00f2\u00f3\f\3\2\2\u00f3"+
		"\u00f4\7\36\2\2\u00f4\u00f6\5\"\22\4\u00f5\u00e3\3\2\2\2\u00f5\u00e6\3"+
		"\2\2\2\u00f5\u00e9\3\2\2\2\u00f5\u00ec\3\2\2\2\u00f5\u00ef\3\2\2\2\u00f5"+
		"\u00f2\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2"+
		"\2\2\u00f8#\3\2\2\2\u00f9\u00f7\3\2\2\2\35(-/:@ILTZ`js\177\u0083\u009a"+
		"\u00a0\u00a7\u00ad\u00b1\u00c1\u00c7\u00cb\u00d2\u00d8\u00e1\u00f5\u00f7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}