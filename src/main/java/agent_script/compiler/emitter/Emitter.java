package agent_script.compiler.emitter;

import agent_script.antlr.AgentScriptBaseVisitor;
import agent_script.antlr.AgentScriptParser;
import agent_script.compiler.Compiler;
import agent_script.compiler.*;
import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.FunctionDefinition;
import agent_script.compiler.analyzer.namespace.NamespaceDefinition;
import agent_script.compiler.analyzer.type.InferredType;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.stringtemplate.v4.ST;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Emits formatted java source code to be compiled by javac.
 */
public final class Emitter extends CompilerProcessor {
    public Emitter(CompilerMessageReporter messageReporter) {
        super(messageReporter);
    }

    @Override
    public void process(NamespaceBundle namespaceBundle) throws CompilerException {
        this.namespaceBundle = namespaceBundle;

        String s = new Visitor().visit(namespaceBundle.getParseTree());
        String javaSource;
        try {
            javaSource = new Formatter().formatSource(s);
        } catch (FormatterException e) {
            System.out.println(s); // TODO remove this
            // Should not happen
            throw new RuntimeException();
        }

        namespaceBundle.setJavaSource(javaSource);
    }

    /**
     * The visitor which traverses the parse tree.
     */
    private class Visitor extends AgentScriptBaseVisitor<String> {
        /**
         * The piece of code which performs an interrupted check.
         */
        private final String interruptedCheck = "" +
                "if (Thread.currentThread().isInterrupted()) {\n" +
                "   throw new agent_script.runtime.ScriptTimeoutException();\n" +
                "}";

        /**
         * Visits a parse tree which may be null.
         *
         * @param parseTree the parse tree
         * @return the source code emitted for the parse tree
         */
        private String safeVisit(ParseTree parseTree) {
            return parseTree == null ? "" : visit(parseTree);
        }

        /**
         * Visits a list of parse trees and concatenates the emitted source code fragments.
         *
         * @param contexts the parse trees
         * @return the source code emitted
         */
        private String visit(List<? extends ParseTree> contexts) {
            return contexts.stream().map(this::visit).collect(Collectors.joining());
        }

        /**
         * Returns the inferred type for a parse tree.
         *
         * @param parseTree the parse tree
         * @return the inferred type
         */
        private InferredType inferredType(ParseTree parseTree) {
            return namespaceBundle.getInferredTypes().get(parseTree);
        }

        /**
         * Returns the source code which potentially wraps an expression with a boolean conversion.
         *
         * @param parseTree the parse tree
         * @return the wrapped expression
         */
        private String asMaybeBoolean(ParseTree parseTree) {
            String emitted = visit(parseTree);
            return inferredType(parseTree) == InferredType.BOOLEAN
                    ? emitted
                    : new ST("asBoolean(<expression>)")
                    .add("expression", emitted)
                    .render();
        }

        /**
         * Returns the source code which wraps an expression with a boolean conversion.
         *
         * @param parseTree the parse tree
         * @return the wrapped expression
         */
        private String asBoolean(ParseTree parseTree) {
            return new ST("asBoolean(<expression>)")
                    .add("expression", visit(parseTree))
                    .render();
        }

        /**
         * Returns the source code which wraps an expression with a number conversion.
         *
         * @param parseTree the parse tree
         * @return the wrapped expression
         */
        private String asNumber(ParseTree parseTree) {
            String emitted = visit(parseTree);
            return inferredType(parseTree) == InferredType.NUMBER
                    ? emitted
                    : new ST("asNumber(<expression>)")
                    .add("expression", emitted)
                    .render();
        }

        /**
         * Returns the source code which wraps the given expression in a Delay.
         *
         * @param expression the expression
         * @return the wrapped expression
         */
        private String asDelay(String expression) {
            return new ST("delayed(() -> <expression>)")
                    .add("expression", expression)
                    .render();
        }

        /**
         * Returns the unescaped native string.
         *
         * @param nativeString the native string
         * @return the unescaped native string
         */
        private String unescapeNativeString(String nativeString) {
            return nativeString
                    .replaceAll(Pattern.quote("\\\\"), "\\\\")
                    .replaceAll(Pattern.quote("\\\""), "\"")
                    .replaceAll(Pattern.quote("\\{"), "{");
        }

        /**
         * Returns the unescaped string.
         *
         * @param string the string
         * @return the unescaped string
         */
        private String unescapeString(String string) {
            return string.replaceAll(Pattern.quote("\\'"), "'");
        }

        /**
         * Visits a native expression / statement.
         *
         * @param preTokens          the native tokens before any unquotes
         * @param expressionContexts the unquoted expressions
         * @param postTokens         the native tokens before each unquoted expression
         * @return the source code
         */
        private String visitNative(
                List<Token> preTokens,
                List<AgentScriptParser.ExpressionContext> expressionContexts,
                List<Token> postTokens) {

            Map<Integer, String> emittedParts = new TreeMap<>();

            preTokens.forEach(token -> emittedParts.put(token.getStartIndex(), unescapeNativeString(token.getText())));
            expressionContexts.forEach(expressionContext ->
                    emittedParts.put(expressionContext.getStart().getStartIndex(), visit(expressionContext)));
            postTokens.forEach(token -> emittedParts.put(token.getStartIndex(), unescapeNativeString(token.getText())));

            return String.join("", emittedParts.values());
        }

        /**
         * Visits a function call expression / statement.
         *
         * @param functionName the name of the function
         * @param argumentCtx  the argument contexts
         * @return the source code
         */
        private String visitFunctionCall(Symbol functionName, List<AgentScriptParser.ExpressionContext> argumentCtx) {
            NamespaceDefinition namespaceDefinition = resolvedNamespaceDefinition(functionName.getNamespaceSymbol());

            String javaClassPrefix;
            if (functionName.isNameSymbol()) {
                javaClassPrefix = "";
            } else {
                Symbol namespaceName = namespaceDefinition.getName();
                javaClassPrefix = namespaceName.getJavaPackageName() + "." + namespaceName.getJavaClassName() + ".";
            }

            return new ST("<functionName>(<argumentNames>)")
                    .add("functionName", javaClassPrefix + Compiler.munge(functionName.getNameFragment()))
                    .add("argumentNames", argumentCtx.stream()
                            .map(expressionContext -> {
                                String expression = visit(expressionContext);
                                return resolvedFunctionDefinition(functionName).isMacro() ? asDelay(expression) : expression;
                            })
                            .collect(Collectors.joining(", ")))
                    .render();
        }

        @Override
        public String visitScript(AgentScriptParser.ScriptContext ctx) {
            NamespaceDefinition namespaceDefinition = namespaceBundle.getNamespaceDefinition();
            return new ST("" +
                    "package <packageName>;\n" +
                    "\n" +
                    "import static agent_script.runtime.Runtime.*;\n" +
                    "\n" +
                    "@SuppressWarnings(\"unchecked\")" +
                    "public class <className> {\n" +
                    "    <constantDefinitions>\n" +
                    "    <functionDefinitions>\n" +
                    "}")
                    .add("packageName", namespaceDefinition.getName().getJavaPackageName())
                    .add("className", namespaceDefinition.getName().getJavaClassName())
                    .add("constantDefinitions", visit(ctx.constantDefinitions))
                    .add("functionDefinitions", visit(ctx.functionDefinitions))
                    .render();
        }

        @Override
        public String visitConstantDefinition(AgentScriptParser.ConstantDefinitionContext ctx) {
            NamespaceDefinition namespaceDefinition = namespaceBundle.getNamespaceDefinition();
            Symbol constantName = Symbol.asNameSymbol(ctx.nameSymbol.getText());
            assert constantName != null;
            return new ST("<visibility> static final Object <name> = <value>;")
                    .add("visibility", namespaceDefinition.getConstantDefinitions().get(constantName).isPrivate() ? "private" : "public")
                    .add("name", Compiler.munge(constantName.toString()))
                    .add("value", visit(ctx.valueExpression))
                    .render();
        }

        @Override
        public String visitFunctionDefinition(AgentScriptParser.FunctionDefinitionContext ctx) {
            NamespaceDefinition namespaceDefinition = namespaceBundle.getNamespaceDefinition();
            Symbol functionName = Symbol.asNameSymbol(ctx.nameSymbol.getText());
            assert functionName != null;
            FunctionDefinition functionDefinition = namespaceDefinition.getFunctionDefinitions().get(functionName);
            return new ST("" +
                    "<visibility> static Object <name>(<argumentNames>) {\n" +
                    "    <interruptedCheck>\n" +
                    "    <bodyStatements>\n" +
                    "    <inferredReturnStatement>\n" +
                    "}")
                    .add("visibility", functionDefinition.isPrivate() ? "private" : "public")
                    .add("name", Compiler.munge(functionName.toString()))
                    .add("argumentNames", ctx.argumentSymbols.stream()
                            .map(argumentSymbol -> "Object " + Compiler.munge(argumentSymbol.getText()))
                            .collect(Collectors.joining(",")))
                    .add("interruptedCheck", interruptedCheck)
                    .add("bodyStatements", visit(ctx.bodyStatements))
                    .add("inferredReturnStatement", functionDefinition.hasExplicitReturn() ? "" : "return null;")
                    .render();
        }

        @Override
        public String visitAssignStatement(AgentScriptParser.AssignStatementContext ctx) {
            Map<ParseTree, Set<Symbol>> symbolTable = namespaceBundle.getSymbolTable();
            Symbol variableName = Symbol.asNameSymbol(ctx.nameSymbol.getText());
            assert variableName != null;
            return new ST("<type> <name> = <value>;")
                    .add("type", symbolTable.get(ctx).contains(variableName) ? "" : "Object")
                    .add("name", Compiler.munge(variableName.toString()))
                    .add("value", visit(ctx.valueExpression))
                    .render();
        }

        @Override
        public String visitIfStatement(AgentScriptParser.IfStatementContext ctx) {
            return new ST("" +
                    "if (<testExpression>) {\n" +
                    "    <bodyStatements>" +
                    "} <elseIfStatements> <elseStatement>")
                    .add("testExpression", asBoolean(ctx.testExpression))
                    .add("bodyStatements", visit(ctx.bodyStatements))
                    .add("elseIfStatements", visit(ctx.elseIfStatements))
                    .add("elseStatement", safeVisit(ctx.elseStatement()))
                    .render();
        }

        @Override
        public String visitElseIfStatement(AgentScriptParser.ElseIfStatementContext ctx) {
            return new ST("" +
                    "else if (<testExpression>) {\n" +
                    "    <bodyStatements>" +
                    "}")
                    .add("testExpression", asBoolean(ctx.testExpression))
                    .add("bodyStatements", visit(ctx.bodyStatements))
                    .render();
        }

        @Override
        public String visitElseStatement(AgentScriptParser.ElseStatementContext ctx) {
            return new ST("" +
                    "else {\n" +
                    "    <bodyStatements>" +
                    "}")
                    .add("bodyStatements", visit(ctx.bodyStatements))
                    .render();
        }

        @Override
        public String visitWhileStatement(AgentScriptParser.WhileStatementContext ctx) {
            return new ST("" +
                    "while (<testExpression>) {\n" +
                    "    <interruptedCheck>" +
                    "    <bodyStatements>" +
                    "}")
                    .add("testExpression", asBoolean(ctx.testExpression))
                    .add("interruptedCheck", interruptedCheck)
                    .add("bodyStatements", visit(ctx.bodyStatements))
                    .render();
        }

        @Override
        public String visitReturnStatement(AgentScriptParser.ReturnStatementContext ctx) {
            return new ST("return <value>;")
                    .add("value", visit(ctx.expression()))
                    .render();
        }

        @Override
        public String visitFunctionCallStatement(AgentScriptParser.FunctionCallStatementContext ctx) {
            Symbol functionName = Symbol.asMaybeQualifiedSymbol(ctx.functionName.getText());
            assert functionName != null;
            return new ST("<expression>;")
                    .add("expression", visitFunctionCall(resolvedFunctionDefinition(functionName).getName(), ctx.argumentExpressions))
                    .render();
        }

        @Override
        public String visitNativeStatement(AgentScriptParser.NativeStatementContext ctx) {
            return new ST("<nativeStatement>;")
                    .add("nativeStatement", visitNative(ctx.preTokens, ctx.expressions, ctx.postTokens))
                    .render();
        }

        @Override
        public String visitParenWrappedExpression(AgentScriptParser.ParenWrappedExpressionContext ctx) {
            return new ST("(<innerExpression>)")
                    .add("innerExpression", visit(ctx.innerExpression))
                    .render();
        }

        @Override
        public String visitUnaryMathExpression(AgentScriptParser.UnaryMathExpressionContext ctx) {
            return new ST("(<operator> <innerExpression>)")
                    .add("operator", ctx.operator.getText())
                    .add("innerExpression", asNumber(ctx.innerExpression))
                    .render();
        }

        @Override
        public String visitRelationExpression(AgentScriptParser.RelationExpressionContext ctx) {
            return new ST("(<leftExpression> <operator> <rightExpression>)")
                    .add("leftExpression", asNumber(ctx.leftExpression))
                    .add("operator", ctx.operator.getText())
                    .add("rightExpression", asNumber(ctx.rightExpression))
                    .render();
        }

        @Override
        public String visitOrExpression(AgentScriptParser.OrExpressionContext ctx) {
            return new ST("(<leftExpression> || <rightExpression>)")
                    .add("leftExpression", asMaybeBoolean(ctx.leftExpression))
                    .add("rightExpression", asMaybeBoolean(ctx.rightExpression))
                    .render();
        }

        @Override
        public String visitSymbolExpression(AgentScriptParser.SymbolExpressionContext ctx) {
            Symbol symbolName = Symbol.asMaybeQualifiedSymbol(ctx.symbol.getText());
            assert symbolName != null;
            return Compiler.munge(symbolName.toString());
        }

        @Override
        public String visitAndExpression(AgentScriptParser.AndExpressionContext ctx) {
            return new ST("(<leftExpression> && <rightExpression>)")
                    .add("leftExpression", asMaybeBoolean(ctx.leftExpression))
                    .add("rightExpression", asMaybeBoolean(ctx.rightExpression))
                    .render();
        }

        @Override
        public String visitNativeExpression(AgentScriptParser.NativeExpressionContext ctx) {
            return new ST("<nativeStatement>")
                    .add("nativeStatement", visitNative(ctx.preTokens, ctx.expressions, ctx.postTokens))
                    .render();
        }

        @Override
        public String visitEqualityExpression(AgentScriptParser.EqualityExpressionContext ctx) {
            return new ST("<negate>eq(<leftExpression>, <rightExpression>)")
                    .add("negate", "==".equals(ctx.operator.getText()) ? "" : "!")
                    .add("leftExpression", visit(ctx.leftExpression))
                    .add("rightExpression", visit(ctx.rightExpression))
                    .render();
        }

        @Override
        public String visitFunctionCallExpression(AgentScriptParser.FunctionCallExpressionContext ctx) {
            Symbol functionName = Symbol.asMaybeQualifiedSymbol(ctx.functionName.getText());
            assert functionName != null;
            return visitFunctionCall(resolvedFunctionDefinition(functionName).getName(), ctx.argumentExpressions);
        }

        @Override
        public String visitNilExpression(AgentScriptParser.NilExpressionContext ctx) {
            return "null";
        }

        @Override
        public String visitLiteralExpression(AgentScriptParser.LiteralExpressionContext ctx) {
            TerminalNode stringToken = ctx.STRING();
            if (stringToken != null) {
                String s = unescapeString(stringToken.getText());
                return "\"" + s.substring(1, s.length() - 1) + "\"";
            }

            return ctx.getText();
        }

        @Override
        public String visitBinaryMathExpression(AgentScriptParser.BinaryMathExpressionContext ctx) {
            return new ST("(<leftExpression> <operator> <rightExpression>)")
                    .add("leftExpression", asNumber(ctx.leftExpression))
                    .add("operator", ctx.operator.getText())
                    .add("rightExpression", asNumber(ctx.rightExpression))
                    .render();
        }

        @Override
        public String visitUnaryBooleanExpression(AgentScriptParser.UnaryBooleanExpressionContext ctx) {
            return new ST("(!<innerExpression>)")
                    .add("innerExpression", asMaybeBoolean(ctx.innerExpression))
                    .render();
        }
    }
}
