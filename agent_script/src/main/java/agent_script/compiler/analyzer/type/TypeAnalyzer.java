package agent_script.compiler.analyzer.type;

import agent_script.antlr.AgentScriptBaseVisitor;
import agent_script.antlr.AgentScriptParser;
import agent_script.compiler.BaseCompilerProcessor;
import agent_script.compiler.CompilerException;
import agent_script.compiler.CompilerMessageReporter;
import agent_script.compiler.NamespaceBundle;
import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.NamespaceDefinition;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Map;

import static agent_script.compiler.CompilerMessageTemplates.A_0023;
import static agent_script.compiler.CompilerMessageTemplates.A_0024;

/**
 * Checks (with heuristics) whether there are any operators with operands of inappropriate types.
 */
public final class TypeAnalyzer extends BaseCompilerProcessor {
    /**
     * The map from parse trees to their inferred types.
     */
    private Map<ParseTree, InferredType> inferredTypes;

    public TypeAnalyzer(CompilerMessageReporter messageReporter) {
        super(messageReporter);
    }

    @Override
    public void process(NamespaceBundle namespaceBundle, Map<Symbol, NamespaceDefinition> namespaceDefinitions) throws CompilerException {
        this.namespaceBundle = namespaceBundle;
        this.namespaceDefinitions = namespaceDefinitions;
        inferredTypes = new HashMap<>();
        new Visitor().visit(namespaceBundle.getParseTree());
        namespaceBundle.setInferredTypes(inferredTypes);
    }

    /**
     * The visitor which traverses the parse tree.
     */
    private class Visitor extends AgentScriptBaseVisitor<Void> {
        @Override
        public Void visitParenWrappedExpression(AgentScriptParser.ParenWrappedExpressionContext ctx) {
            visitChildren(ctx);
            inferredTypes.put(ctx, inferredTypes.get(ctx.expression()));
            return null;
        }

        @Override
        public Void visitUnaryMathExpression(AgentScriptParser.UnaryMathExpressionContext ctx) {
            visitChildren(ctx);

            InferredType innerExpressionType = inferredTypes.get(ctx.innerExpression);
            if (!innerExpressionType.isMaybeNumber()) {
                reportMessage(A_0023.render(getLocation(ctx.operator), ctx.operator.getText(), innerExpressionType));
            }

            inferredTypes.put(ctx, InferredType.NUMBER);
            return null;
        }

        @Override
        public Void visitRelationExpression(AgentScriptParser.RelationExpressionContext ctx) {
            visitChildren(ctx);

            InferredType leftExpressionType = inferredTypes.get(ctx.leftExpression);
            InferredType rightExpressionType = inferredTypes.get(ctx.rightExpression);
            if (!leftExpressionType.isMaybeNumber() || !rightExpressionType.isMaybeNumber()) {
                reportMessage(A_0023.render(
                        getLocation(ctx.operator),
                        ctx.operator.getText(),
                        String.format("%s, %s", leftExpressionType, rightExpressionType)));
            }

            inferredTypes.put(ctx, InferredType.BOOLEAN);
            return null;
        }

        @Override
        public Void visitOrExpression(AgentScriptParser.OrExpressionContext ctx) {
            visitChildren(ctx);

            InferredType leftExpressionType = inferredTypes.get(ctx.leftExpression);
            InferredType rightExpressionType = inferredTypes.get(ctx.rightExpression);
            if (!leftExpressionType.isMaybeBoolean() || !rightExpressionType.isMaybeBoolean()) {
                reportMessage(A_0023.render(
                        getLocation(ctx.operator),
                        ctx.operator.getText(),
                        String.format("%s, %s", leftExpressionType, rightExpressionType)));
            }

            inferredTypes.put(ctx, InferredType.BOOLEAN);
            return null;
        }

        @Override
        public Void visitSymbolExpression(AgentScriptParser.SymbolExpressionContext ctx) {
            inferredTypes.put(ctx, InferredType.UNKNOWN);
            return null;
        }

        @Override
        public Void visitAndExpression(AgentScriptParser.AndExpressionContext ctx) {
            visitChildren(ctx);

            InferredType leftExpressionType = inferredTypes.get(ctx.leftExpression);
            InferredType rightExpressionType = inferredTypes.get(ctx.rightExpression);
            if (!leftExpressionType.isMaybeBoolean() || !rightExpressionType.isMaybeBoolean()) {
                reportMessage(A_0023.render(
                        getLocation(ctx.operator),
                        ctx.operator.getText(),
                        String.format("%s, %s", leftExpressionType, rightExpressionType)));
            }

            inferredTypes.put(ctx, InferredType.BOOLEAN);
            return null;
        }

        @Override
        public Void visitNativeExpression(AgentScriptParser.NativeExpressionContext ctx) {
            visitChildren(ctx);
            inferredTypes.put(ctx, InferredType.UNKNOWN);
            return null;
        }

        @Override
        public Void visitEqualityExpression(AgentScriptParser.EqualityExpressionContext ctx) {
            visitChildren(ctx);

            InferredType leftExpressionType = inferredTypes.get(ctx.leftExpression);
            InferredType rightExpressionType = inferredTypes.get(ctx.rightExpression);
            String operatorText = ctx.operator.getText();
            if ("==".equals(operatorText)
                    && leftExpressionType != InferredType.UNKNOWN
                    && rightExpressionType != InferredType.UNKNOWN
                    && leftExpressionType != rightExpressionType) {
                reportMessage(A_0024.render(getLocation(ctx.operator), operatorText));
            }

            inferredTypes.put(ctx, InferredType.BOOLEAN);
            return null;
        }

        @Override
        public Void visitFunctionCallExpression(AgentScriptParser.FunctionCallExpressionContext ctx) {
            inferredTypes.put(ctx, InferredType.UNKNOWN);
            return null;
        }

        @Override
        public Void visitNilExpression(AgentScriptParser.NilExpressionContext ctx) {
            inferredTypes.put(ctx, InferredType.NIL);
            return null;
        }

        @Override
        public Void visitLiteralExpression(AgentScriptParser.LiteralExpressionContext ctx) {
            if (ctx.BOOLEAN() != null) {
                inferredTypes.put(ctx, InferredType.BOOLEAN);
            } else if (ctx.NUMBER() != null) {
                inferredTypes.put(ctx, InferredType.NUMBER);
            } else if (ctx.STRING() != null) {
                inferredTypes.put(ctx, InferredType.STRING);
            } else {
                throw new RuntimeException();
            }

            return null;
        }

        @Override
        public Void visitBinaryMathExpression(AgentScriptParser.BinaryMathExpressionContext ctx) {
            visitChildren(ctx);

            InferredType leftExpressionType = inferredTypes.get(ctx.leftExpression);
            InferredType rightExpressionType = inferredTypes.get(ctx.rightExpression);
            if (!leftExpressionType.isMaybeNumber() || !rightExpressionType.isMaybeNumber()) {
                reportMessage(A_0023.render(
                        getLocation(ctx.operator),
                        ctx.operator.getText(),
                        String.format("%s, %s", leftExpressionType, rightExpressionType)));
            }

            inferredTypes.put(ctx, InferredType.NUMBER);
            return null;
        }

        @Override
        public Void visitUnaryBooleanExpression(AgentScriptParser.UnaryBooleanExpressionContext ctx) {
            visitChildren(ctx);

            InferredType innerExpressionType = inferredTypes.get(ctx.innerExpression);
            if (!innerExpressionType.isMaybeBoolean()) {
                reportMessage(A_0023.render(getLocation(ctx.operator), ctx.operator.getText(), innerExpressionType));
            }

            inferredTypes.put(ctx, InferredType.BOOLEAN);
            return null;
        }
    }
}
