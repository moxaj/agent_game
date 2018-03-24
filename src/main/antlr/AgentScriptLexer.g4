lexer grammar AgentScriptLexer;

// Keywords

NAMESPACE: 'namespace';
IMPORT:    'import';
AS:        'as';
CONSTANT:  'constant';
FUNCTION:  'function';
END:       'end';
IF:        'if';
ELSE:      'else';
RETURN:    'return';
WHILE:     'while';

// Common

COMMA:  ',';
LPAREN: '(';
RPAREN: ')';
DOT:    '.';
SCOPE:  '::';

// Literals

BOOLEAN: 'true' | 'false';
NUMBER:  ('0' | ([1-9][0-9]*))('.' [0-9]*[1-9])?;
STRING:  '\'' ('\\\\' | '\\\'' | ~[\\'\r\n])* '\'';
NIL:     'nil';

// Operators

ASSIGN: '=';
LT:     '<';
LE:     '<=';
GT:     '>';
GE:     '>=';
EQ:     '==';
NE:     '!=';
AND:    '&&';
OR:     '||';
NOT:    '!';
ADD:    '+';
SUB:    '-';
MUL:    '*';
DIV:    '/';

// Meta

LMETA: '#[';
RMETA: ']';

// Native

LNATIVE:       '"' -> pushMode(NATIVE_MODE);
RNATIVE_QUOTE: '}' -> pushMode(NATIVE_MODE);

// Symbols

fragment WORD:        [a-zA-Z_][a-zA-Z_0-9]*;
fragment NESTED_WORD: WORD (DOT WORD)+;
SYMBOL: WORD | NESTED_WORD | ((WORD | NESTED_WORD) SCOPE WORD);

// Comments

COMMENT: '//' ~[\r\n]* -> skip;

// Misc

WHITESPACE: [ \t\n\r]+ -> skip;

// Native

mode NATIVE_MODE;
NATIVE_CHARS:  ('\\\\' | '\\' RNATIVE | '\\' LNATIVE_QUOTE | ~["{\\])+;
RNATIVE:       '"' -> popMode;
LNATIVE_QUOTE: '{' -> popMode;
