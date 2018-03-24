grammar AgentScript;

options {
    tokenVocab=AgentScriptLexer;
}

// Main parser rule

script
    : namespaceDefinition
      importDeclarations+=importDeclaration*
      ( constantDefinitions+=constantDefinition
      | functionDefinitions+=functionDefinition
      )*
      EOF
    ;

// Top level

meta
    : LMETA
      metaSymbols+=SYMBOL (COMMA metaSymbols+=SYMBOL)*
      RMETA
    ;

namespaceDefinition
    : meta? NAMESPACE nameSymbol=SYMBOL
    ;

importDeclaration
    : IMPORT nameSymbol=SYMBOL (AS aliasSymbol=SYMBOL)?
    ;

constantDefinition
    : meta? CONSTANT nameSymbol=SYMBOL ASSIGN valueExpression=expression
    ;

functionDefinition
    : meta? FUNCTION nameSymbol=SYMBOL LPAREN
        argumentSymbols+=SYMBOL? (COMMA argumentSymbols+=SYMBOL)* RPAREN
        block
      END
    ;

// Statements

block
    : statements+=statement*
    ;

statement
    : assignStatement
    | ifStatement
    | whileStatement
    | returnStatement
    | functionCallStatement
    | nativeStatement
    ;

assignStatement
    : nameSymbol=SYMBOL ASSIGN valueExpression=expression
    ;

ifStatement
    : IF testExpression=expression
        block
      elseIfStatements+=elseIfStatement*
      elseStatement?
      END
    ;

elseIfStatement
    : ELSE IF testExpression=expression
        block
    ;

elseStatement
    : ELSE
        block
    ;

whileStatement
    : WHILE testExpression=expression
        block
      END
    ;

returnStatement
    : RETURN innerExpression=expression
    ;

functionCallStatement
    : functionName=SYMBOL LPAREN
        argumentExpressions+=expression?
        (COMMA argumentExpressions+=expression)*
      RPAREN
    ;

nativeStatement
    : LNATIVE
        preTokens+=NATIVE_CHARS?
        (LNATIVE_QUOTE
           expressions+=expression
         RNATIVE_QUOTE
         postTokens+=NATIVE_CHARS?)*
      RNATIVE
    ;

// Expression

// TODO since mutual left recursion is not supported,
// either rewrite to layered version: ugly
// OR leave as is: duplicated branches (e.g. functionCallStatement, functionCallExpression)

expression
    : LPAREN innerExpression=expression RPAREN   #parenWrappedExpression
    | BOOLEAN                                    #literalExpression
    | NUMBER                                     #literalExpression
    | STRING                                     #literalExpression
    | NIL                                        #nilExpression
    | LNATIVE
        preTokens+=NATIVE_CHARS?
        (LNATIVE_QUOTE
           expressions+=expression
         RNATIVE_QUOTE
         postTokens+=NATIVE_CHARS?)*
      RNATIVE                                    #nativeExpression
    | functionName=SYMBOL LPAREN
        argumentExpressions+=expression?
        (COMMA argumentExpressions+=expression)*
      RPAREN                                     #functionCallExpression
    | symbol=SYMBOL                              #symbolExpression
    | operator=NOT innerExpression=expression    #unaryBooleanExpression
    | operator=(ADD | SUB)
      innerExpression=expression                 #unaryMathExpression
    | leftExpression=expression
      operator=(MUL | DIV)
      rightExpression=expression                 #binaryMathExpression
    | leftExpression=expression
      operator=(ADD | SUB)
      rightExpression=expression                 #binaryMathExpression
    | leftExpression=expression
      operator=(LT | LE | GT | GE)
      rightExpression=expression                 #relationExpression
    | leftExpression=expression
      operator=(EQ | NE)
      rightExpression=expression                 #equalityExpression
    | leftExpression=expression
      operator=AND
      rightExpression=expression                 #andExpression
    | leftExpression=expression
      operator=OR
      rightExpression=expression                 #orExpression
    ;