// Generated from AgentScriptLexer.g4 by ANTLR 4.7.1
package agent_script.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AgentScriptLexer extends Lexer {
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
		NATIVE_MODE=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "NATIVE_MODE"
	};

	public static final String[] ruleNames = {
		"NAMESPACE", "IMPORT", "AS", "CONSTANT", "FUNCTION", "END", "IF", "ELSE", 
		"RETURN", "WHILE", "COMMA", "LPAREN", "RPAREN", "DOT", "SCOPE", "BOOLEAN", 
		"NUMBER", "STRING", "NIL", "ASSIGN", "LT", "LE", "GT", "GE", "EQ", "NE", 
		"AND", "OR", "NOT", "ADD", "SUB", "MUL", "DIV", "LMETA", "RMETA", "LNATIVE", 
		"RNATIVE_QUOTE", "WORD", "NESTED_WORD", "SYMBOL", "COMMENT", "WHITESPACE", 
		"NATIVE_CHARS", "RNATIVE", "LNATIVE_QUOTE"
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


	public AgentScriptLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "AgentScriptLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2-\u0146\b\1\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u00b2\n\21\3\22\3\22\3\22\7\22\u00b7\n"+
		"\22\f\22\16\22\u00ba\13\22\5\22\u00bc\n\22\3\22\3\22\7\22\u00c0\n\22\f"+
		"\22\16\22\u00c3\13\22\3\22\5\22\u00c6\n\22\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\7\23\u00ce\n\23\f\23\16\23\u00d1\13\23\3\23\3\23\3\24\3\24\3\24\3"+
		"\24\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\32\3"+
		"\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3"+
		"\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3%\3%\3&\3&\3&\3&\3\'\3\'"+
		"\7\'\u010a\n\'\f\'\16\'\u010d\13\'\3(\3(\3(\3(\6(\u0113\n(\r(\16(\u0114"+
		"\3)\3)\3)\3)\5)\u011b\n)\3)\3)\3)\5)\u0120\n)\3*\3*\3*\3*\7*\u0126\n*"+
		"\f*\16*\u0129\13*\3*\3*\3+\6+\u012e\n+\r+\16+\u012f\3+\3+\3,\3,\3,\3,"+
		"\3,\3,\3,\6,\u013b\n,\r,\16,\u013c\3-\3-\3-\3-\3.\3.\3.\3.\2\2/\4\3\6"+
		"\4\b\5\n\6\f\7\16\b\20\t\22\n\24\13\26\f\30\r\32\16\34\17\36\20 \21\""+
		"\22$\23&\24(\25*\26,\27.\30\60\31\62\32\64\33\66\348\35:\36<\37> @!B\""+
		"D#F$H%J&L\'N\2P\2R(T)V*X+Z,\\-\4\2\3\n\3\2\63;\3\2\62;\6\2\f\f\17\17)"+
		")^^\5\2C\\aac|\6\2\62;C\\aac|\4\2\f\f\17\17\5\2\13\f\17\17\"\"\5\2$$^"+
		"^}}\2\u0155\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2\n\3\2\2\2\2\f\3\2\2"+
		"\2\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2\2\24\3\2\2\2\2\26\3\2\2\2\2"+
		"\30\3\2\2\2\2\32\3\2\2\2\2\34\3\2\2\2\2\36\3\2\2\2\2 \3\2\2\2\2\"\3\2"+
		"\2\2\2$\3\2\2\2\2&\3\2\2\2\2(\3\2\2\2\2*\3\2\2\2\2,\3\2\2\2\2.\3\2\2\2"+
		"\2\60\3\2\2\2\2\62\3\2\2\2\2\64\3\2\2\2\2\66\3\2\2\2\28\3\2\2\2\2:\3\2"+
		"\2\2\2<\3\2\2\2\2>\3\2\2\2\2@\3\2\2\2\2B\3\2\2\2\2D\3\2\2\2\2F\3\2\2\2"+
		"\2H\3\2\2\2\2J\3\2\2\2\2L\3\2\2\2\2R\3\2\2\2\2T\3\2\2\2\2V\3\2\2\2\3X"+
		"\3\2\2\2\3Z\3\2\2\2\3\\\3\2\2\2\4^\3\2\2\2\6h\3\2\2\2\bo\3\2\2\2\nr\3"+
		"\2\2\2\f{\3\2\2\2\16\u0084\3\2\2\2\20\u0088\3\2\2\2\22\u008b\3\2\2\2\24"+
		"\u0090\3\2\2\2\26\u0097\3\2\2\2\30\u009d\3\2\2\2\32\u009f\3\2\2\2\34\u00a1"+
		"\3\2\2\2\36\u00a3\3\2\2\2 \u00a5\3\2\2\2\"\u00b1\3\2\2\2$\u00bb\3\2\2"+
		"\2&\u00c7\3\2\2\2(\u00d4\3\2\2\2*\u00d8\3\2\2\2,\u00da\3\2\2\2.\u00dc"+
		"\3\2\2\2\60\u00df\3\2\2\2\62\u00e1\3\2\2\2\64\u00e4\3\2\2\2\66\u00e7\3"+
		"\2\2\28\u00ea\3\2\2\2:\u00ed\3\2\2\2<\u00f0\3\2\2\2>\u00f2\3\2\2\2@\u00f4"+
		"\3\2\2\2B\u00f6\3\2\2\2D\u00f8\3\2\2\2F\u00fa\3\2\2\2H\u00fd\3\2\2\2J"+
		"\u00ff\3\2\2\2L\u0103\3\2\2\2N\u0107\3\2\2\2P\u010e\3\2\2\2R\u011f\3\2"+
		"\2\2T\u0121\3\2\2\2V\u012d\3\2\2\2X\u013a\3\2\2\2Z\u013e\3\2\2\2\\\u0142"+
		"\3\2\2\2^_\7p\2\2_`\7c\2\2`a\7o\2\2ab\7g\2\2bc\7u\2\2cd\7r\2\2de\7c\2"+
		"\2ef\7e\2\2fg\7g\2\2g\5\3\2\2\2hi\7k\2\2ij\7o\2\2jk\7r\2\2kl\7q\2\2lm"+
		"\7t\2\2mn\7v\2\2n\7\3\2\2\2op\7c\2\2pq\7u\2\2q\t\3\2\2\2rs\7e\2\2st\7"+
		"q\2\2tu\7p\2\2uv\7u\2\2vw\7v\2\2wx\7c\2\2xy\7p\2\2yz\7v\2\2z\13\3\2\2"+
		"\2{|\7h\2\2|}\7w\2\2}~\7p\2\2~\177\7e\2\2\177\u0080\7v\2\2\u0080\u0081"+
		"\7k\2\2\u0081\u0082\7q\2\2\u0082\u0083\7p\2\2\u0083\r\3\2\2\2\u0084\u0085"+
		"\7g\2\2\u0085\u0086\7p\2\2\u0086\u0087\7f\2\2\u0087\17\3\2\2\2\u0088\u0089"+
		"\7k\2\2\u0089\u008a\7h\2\2\u008a\21\3\2\2\2\u008b\u008c\7g\2\2\u008c\u008d"+
		"\7n\2\2\u008d\u008e\7u\2\2\u008e\u008f\7g\2\2\u008f\23\3\2\2\2\u0090\u0091"+
		"\7t\2\2\u0091\u0092\7g\2\2\u0092\u0093\7v\2\2\u0093\u0094\7w\2\2\u0094"+
		"\u0095\7t\2\2\u0095\u0096\7p\2\2\u0096\25\3\2\2\2\u0097\u0098\7y\2\2\u0098"+
		"\u0099\7j\2\2\u0099\u009a\7k\2\2\u009a\u009b\7n\2\2\u009b\u009c\7g\2\2"+
		"\u009c\27\3\2\2\2\u009d\u009e\7.\2\2\u009e\31\3\2\2\2\u009f\u00a0\7*\2"+
		"\2\u00a0\33\3\2\2\2\u00a1\u00a2\7+\2\2\u00a2\35\3\2\2\2\u00a3\u00a4\7"+
		"\60\2\2\u00a4\37\3\2\2\2\u00a5\u00a6\7<\2\2\u00a6\u00a7\7<\2\2\u00a7!"+
		"\3\2\2\2\u00a8\u00a9\7v\2\2\u00a9\u00aa\7t\2\2\u00aa\u00ab\7w\2\2\u00ab"+
		"\u00b2\7g\2\2\u00ac\u00ad\7h\2\2\u00ad\u00ae\7c\2\2\u00ae\u00af\7n\2\2"+
		"\u00af\u00b0\7u\2\2\u00b0\u00b2\7g\2\2\u00b1\u00a8\3\2\2\2\u00b1\u00ac"+
		"\3\2\2\2\u00b2#\3\2\2\2\u00b3\u00bc\7\62\2\2\u00b4\u00b8\t\2\2\2\u00b5"+
		"\u00b7\t\3\2\2\u00b6\u00b5\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2"+
		"\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb"+
		"\u00b3\3\2\2\2\u00bb\u00b4\3\2\2\2\u00bc\u00c5\3\2\2\2\u00bd\u00c1\7\60"+
		"\2\2\u00be\u00c0\t\3\2\2\u00bf\u00be\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1"+
		"\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4\3\2\2\2\u00c3\u00c1\3\2"+
		"\2\2\u00c4\u00c6\t\2\2\2\u00c5\u00bd\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"%\3\2\2\2\u00c7\u00cf\7)\2\2\u00c8\u00c9\7^\2\2\u00c9\u00ce\7^\2\2\u00ca"+
		"\u00cb\7^\2\2\u00cb\u00ce\7)\2\2\u00cc\u00ce\n\4\2\2\u00cd\u00c8\3\2\2"+
		"\2\u00cd\u00ca\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd"+
		"\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d2\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2"+
		"\u00d3\7)\2\2\u00d3\'\3\2\2\2\u00d4\u00d5\7p\2\2\u00d5\u00d6\7k\2\2\u00d6"+
		"\u00d7\7n\2\2\u00d7)\3\2\2\2\u00d8\u00d9\7?\2\2\u00d9+\3\2\2\2\u00da\u00db"+
		"\7>\2\2\u00db-\3\2\2\2\u00dc\u00dd\7>\2\2\u00dd\u00de\7?\2\2\u00de/\3"+
		"\2\2\2\u00df\u00e0\7@\2\2\u00e0\61\3\2\2\2\u00e1\u00e2\7@\2\2\u00e2\u00e3"+
		"\7?\2\2\u00e3\63\3\2\2\2\u00e4\u00e5\7?\2\2\u00e5\u00e6\7?\2\2\u00e6\65"+
		"\3\2\2\2\u00e7\u00e8\7#\2\2\u00e8\u00e9\7?\2\2\u00e9\67\3\2\2\2\u00ea"+
		"\u00eb\7(\2\2\u00eb\u00ec\7(\2\2\u00ec9\3\2\2\2\u00ed\u00ee\7~\2\2\u00ee"+
		"\u00ef\7~\2\2\u00ef;\3\2\2\2\u00f0\u00f1\7#\2\2\u00f1=\3\2\2\2\u00f2\u00f3"+
		"\7-\2\2\u00f3?\3\2\2\2\u00f4\u00f5\7/\2\2\u00f5A\3\2\2\2\u00f6\u00f7\7"+
		",\2\2\u00f7C\3\2\2\2\u00f8\u00f9\7\61\2\2\u00f9E\3\2\2\2\u00fa\u00fb\7"+
		"%\2\2\u00fb\u00fc\7]\2\2\u00fcG\3\2\2\2\u00fd\u00fe\7_\2\2\u00feI\3\2"+
		"\2\2\u00ff\u0100\7$\2\2\u0100\u0101\3\2\2\2\u0101\u0102\b%\2\2\u0102K"+
		"\3\2\2\2\u0103\u0104\7\177\2\2\u0104\u0105\3\2\2\2\u0105\u0106\b&\2\2"+
		"\u0106M\3\2\2\2\u0107\u010b\t\5\2\2\u0108\u010a\t\6\2\2\u0109\u0108\3"+
		"\2\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"O\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u0112\5N\'\2\u010f\u0110\5\36\17\2"+
		"\u0110\u0111\5N\'\2\u0111\u0113\3\2\2\2\u0112\u010f\3\2\2\2\u0113\u0114"+
		"\3\2\2\2\u0114\u0112\3\2\2\2\u0114\u0115\3\2\2\2\u0115Q\3\2\2\2\u0116"+
		"\u0120\5N\'\2\u0117\u0120\5P(\2\u0118\u011b\5N\'\2\u0119\u011b\5P(\2\u011a"+
		"\u0118\3\2\2\2\u011a\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011d\5 "+
		"\20\2\u011d\u011e\5N\'\2\u011e\u0120\3\2\2\2\u011f\u0116\3\2\2\2\u011f"+
		"\u0117\3\2\2\2\u011f\u011a\3\2\2\2\u0120S\3\2\2\2\u0121\u0122\7\61\2\2"+
		"\u0122\u0123\7\61\2\2\u0123\u0127\3\2\2\2\u0124\u0126\n\7\2\2\u0125\u0124"+
		"\3\2\2\2\u0126\u0129\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128"+
		"\u012a\3\2\2\2\u0129\u0127\3\2\2\2\u012a\u012b\b*\3\2\u012bU\3\2\2\2\u012c"+
		"\u012e\t\b\2\2\u012d\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u012d\3\2"+
		"\2\2\u012f\u0130\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u0132\b+\3\2\u0132"+
		"W\3\2\2\2\u0133\u0134\7^\2\2\u0134\u013b\7^\2\2\u0135\u0136\7^\2\2\u0136"+
		"\u013b\5Z-\2\u0137\u0138\7^\2\2\u0138\u013b\5\\.\2\u0139\u013b\n\t\2\2"+
		"\u013a\u0133\3\2\2\2\u013a\u0135\3\2\2\2\u013a\u0137\3\2\2\2\u013a\u0139"+
		"\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d"+
		"Y\3\2\2\2\u013e\u013f\7$\2\2\u013f\u0140\3\2\2\2\u0140\u0141\b-\4\2\u0141"+
		"[\3\2\2\2\u0142\u0143\7}\2\2\u0143\u0144\3\2\2\2\u0144\u0145\b.\4\2\u0145"+
		"]\3\2\2\2\23\2\3\u00b1\u00b8\u00bb\u00c1\u00c5\u00cd\u00cf\u010b\u0114"+
		"\u011a\u011f\u0127\u012f\u013a\u013c\5\7\3\2\b\2\2\6\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}