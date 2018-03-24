package agent_script.compiler.analyzer.native_meta;

import agent_script.antlr.AgentScriptBaseVisitor;
import agent_script.antlr.AgentScriptParser;
import agent_script.compiler.*;
import agent_script.compiler.analyzer.Location;
import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.ConstantDefinition;
import agent_script.compiler.analyzer.namespace.FunctionDefinition;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static agent_script.compiler.CompilerMessageTemplates.*;

/**
 * Ensures that all functions / constants with native expressions / statements have a 'native' meta.
 */
public final class NativeMetaAnalyzer extends CompilerProcessor implements ICompilerProcessor {
    public NativeMetaAnalyzer(CompilerMessageReporter messageReporter) {
        super(messageReporter);
    }

    @Override
    public void process(NamespaceBundle namespaceBundle) throws CompilerException {
        this.namespaceBundle = namespaceBundle;
        new Visitor().visit(namespaceBundle.getParseTree());
    }

    /**
     * The visitor which traverses the parse tree.
     */
    private class Visitor extends AgentScriptBaseVisitor<Void> {
        private final Set<Symbol> constantsUsingNative = new HashSet<>();
        private final Set<Symbol> functionsUsingNative = new HashSet<>();

        /**
         * Visits a native expression / statement.
         *
         * @param ctx the native expression / statement
         */
        private void visitNativeContext(ParserRuleContext ctx) {
            List<? extends Tree> ancestorTrees = Trees.getAncestors(ctx);
            ancestorTrees.stream()
                    .filter(tree -> tree instanceof AgentScriptParser.ConstantDefinitionContext)
                    .findFirst()
                    .ifPresent(tree -> constantsUsingNative.add(
                            Symbol.asNameSymbol(((AgentScriptParser.ConstantDefinitionContext) tree).nameSymbol.getText())));

            ancestorTrees.stream()
                    .filter(tree -> tree instanceof AgentScriptParser.FunctionDefinitionContext)
                    .findFirst()
                    .ifPresent(tree -> functionsUsingNative.add(
                            Symbol.asNameSymbol(((AgentScriptParser.FunctionDefinitionContext) tree).nameSymbol.getText())));
        }

        @Override
        public Void visitConstantDefinition(AgentScriptParser.ConstantDefinitionContext ctx) {
            super.visitConstantDefinition(ctx);

            Symbol constantName = Symbol.asNameSymbol(ctx.nameSymbol.getText());
            if (constantName == null) {
                return null;
            }

            ConstantDefinition constantDefinition = resolvedConstantDefinition(constantName);
            Location location = constantDefinition.getLocation();
            boolean usesNative = constantsUsingNative.contains(constantName);
            boolean isNative = constantDefinition.isNative();
            if (usesNative && !isNative) {
                reportMessage(A_0012.render(location, constantName));
            } else if (!usesNative && isNative) {
                reportMessage(A_0026.render(location, constantName));
            }

            return null;
        }

        @Override
        public Void visitFunctionDefinition(AgentScriptParser.FunctionDefinitionContext ctx) {
            super.visitFunctionDefinition(ctx);

            Symbol functionName = Symbol.asNameSymbol(ctx.nameSymbol.getText());
            if (functionName == null) {
                return null;
            }

            FunctionDefinition functionDefinition = resolvedFunctionDefinition(functionName);
            Location location = functionDefinition.getLocation();
            boolean usesNative = functionsUsingNative.contains(functionName);
            boolean isNative = functionDefinition.isNative();
            if (usesNative && !isNative) {
                reportMessage(A_0013.render(location, functionName));
            } else if (!usesNative && isNative) {
                reportMessage(A_0027.render(location, functionName));
            }

            return null;
        }

        @Override
        public Void visitNativeStatement(AgentScriptParser.NativeStatementContext ctx) {
            visitNativeContext(ctx);
            return null;
        }

        @Override
        public Void visitNativeExpression(AgentScriptParser.NativeExpressionContext ctx) {
            visitNativeContext(ctx);
            return null;
        }
    }
}
