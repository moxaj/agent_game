grammar AgentScript;

/// ======================================================
/// Parser rules
/// ======================================================

script
    : constDeclarations+=constDeclaration* strategy
    ;

constDeclaration
    : name=CONST_IDENTIFIER '=' value=expression
    ;

strategy
    : 'strategy'
          bodyStatements+=statement*
      'end'
    ;

statement
    : localAssignStatement
    | memoryAssignStatement
    | ifStatement
    | whileStatement
    | actionStatement
    ;

localAssignStatement
    : name=LOCAL_IDENTIFIER '=' value=expression
    ;

memoryAssignStatement
    : name=MEMORY_IDENTIFIER '=' value=expression
    ;

ifStatement
    : 'if' testCondition=expression
          bodyStatements+=statement*
      elseIfStatements+=elseIfStatement*
      elseStatement?
      'end'
    ;

elseIfStatement
    : 'else' 'if' testCondition=expression
          bodyStatements+=statement*
    ;

elseStatement
    : 'else'
          bodyStatements+=statement*
    ;

whileStatement
    : 'while' testCondition=expression
          bodyStatements+=statement*
      'end'
    ;

actionStatement
    : ACTION_IDENTIFIER
    ;

expression
    : BOOLEAN_LITERAL
    | NUMBER_LITERAL
    | variableExpression
    | booleanExpression
    | numberExpression
    ;

variableExpression
    : LOCAL_IDENTIFIER
    | CONST_IDENTIFIER
    | MEMORY_IDENTIFIER
    | '(' innerExpression=variableExpression ')'
    ;

booleanExpression
    : BOOLEAN_LITERAL
    | variableExpression
    | '!' negatedBooleanExpression=booleanExpression
    | leftBooleanExpression=booleanExpression operator=('&&' | '||') rightBooleanExpression=booleanExpression
    | leftNumberExpression=numberExpression operator=('<' | '>' | '<=' | '>=' | '==') rightNumberExpression=numberExpression
    | '(' innerExpression=booleanExpression ')'
    ;

numberExpression
    : NUMBER_LITERAL
    | variableExpression
    | leftExpression=numberExpression operator=('*' | '/' | '+' | '-') rightExpression=numberExpression
    // TODO unary +- ?
    | '(' innerExpression=numberExpression ')'
    ;

/// ======================================================
/// Lexer rules
/// ======================================================

BOOLEAN_LITERAL
    : 'true'
    | 'false'
    ;

NUMBER_LITERAL
    : ('0' | ([1-9][0-9]*))('.' [0-9]*[1-9])?
    ;

fragment IDENTIFIER
    : [a-zA-Z0-9_]*
    ;

LOCAL_IDENTIFIER
    : [a-z] IDENTIFIER
    ;

CONST_IDENTIFIER
    : [A-Z] IDENTIFIER
    ;

MEMORY_IDENTIFIER
    : '_' IDENTIFIER
    ;

ACTION_IDENTIFIER
    : ':' IDENTIFIER
    ;

// Ignore whitespace

WHITESPACE
    : [ \t\n\r]+ -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

