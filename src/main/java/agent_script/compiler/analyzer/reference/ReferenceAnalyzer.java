package agent_script.compiler.analyzer.reference;

import agent_script.antlr.AgentScriptBaseVisitor;
import agent_script.antlr.AgentScriptParser;
import agent_script.compiler.CompilerException;
import agent_script.compiler.CompilerMessageReporter;
import agent_script.compiler.CompilerProcessor;
import agent_script.compiler.NamespaceBundle;
import agent_script.compiler.analyzer.Location;
import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.ConstantDefinition;
import agent_script.compiler.analyzer.namespace.FunctionDefinition;
import agent_script.compiler.analyzer.namespace.NamespaceDefinition;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.Trees;

import java.util.*;

import static agent_script.compiler.CompilerMessageTemplates.*;

/**
 * Ensures that all references to local variables / constants / functions are valid.
 */
public final class ReferenceAnalyzer extends CompilerProcessor {
    /**
     * The symbol table.
     */
    private Map<ParseTree, Set<Symbol>> symbolTable;

    public ReferenceAnalyzer(CompilerMessageReporter messageReporter) {
        super(messageReporter);
    }

    @Override
    public void process(NamespaceBundle namespaceBundle) throws CompilerException {
        this.namespaceBundle = namespaceBundle;
        symbolTable = new HashMap<>();
        new Visitor().visit(namespaceBundle.getParseTree());
        namespaceBundle.setSymbolTable(symbolTable);
    }

    /**
     * The visitor which traverses the parse tree.
     */
    private class Visitor extends AgentScriptBaseVisitor<Void> {
        /**
         * Returns the siblings after a given parse tree.
         *
         * @param parseTree the parse tree
         * @return the siblings
         */
        private List<ParseTree> getNextStatements(ParseTree parseTree) {
            List<ParseTree> nextStatements = new ArrayList<>();

            ParseTree blockParseTree = (ParseTree) Trees.getAncestors(parseTree).stream()
                    .filter(ancestorParseTree -> ancestorParseTree instanceof AgentScriptParser.BlockContext)
                    .reduce((first, second) -> second)
                    .orElseThrow(RuntimeException::new);

            boolean after = false;
            for (int i = 0; i < blockParseTree.getChildCount(); i++) {
                ParseTree parseTree2 = blockParseTree.getChild(i);
                if (after) {
                    nextStatements.add(parseTree2);
                } else if (parseTree2 == parseTree) {
                    after = true;
                }
            }

            return nextStatements;
        }

        /**
         * Make a given parse tree inherit the symbols of its parent.
         *
         * @param parseTree the parse tree
         */
        private void inheritSymbols(ParseTree parseTree) {
            symbolTable
                    .computeIfAbsent(parseTree, key -> new HashSet<>())
                    .addAll(symbolTable.getOrDefault(parseTree.getParent(), new HashSet<>()));
        }

        /**
         * Checks whether a function name refers to an existing function and was invoked with the correct arity.
         *
         * @param ctx                 the context of the function
         * @param functionNameToken   the function name token
         * @param passedArgumentCount the number of arguments passedArgumentCount
         */
        private void visitFunctionCall(ParserRuleContext ctx, Token functionNameToken, int passedArgumentCount) {
            inheritSymbols(ctx);

            Location location = getLocation(functionNameToken);
            String functionNameText = functionNameToken.getText();
            Symbol functionName = Symbol.asMaybeQualifiedSymbol(functionNameText);
            if (functionName == null) {
                reportMessage(A_0010.render(location, functionNameText));
                return;
            }

            FunctionDefinition functionDefinition;
            if (functionName.isNameSymbol()) {
                functionDefinition = resolvedFunctionDefinition(functionName);
                if (functionDefinition == null) {
                    reportMessage(A_0016.render(location, functionName.getNameSymbol(), functionName.getNamespaceSymbol()));
                    return;
                }
            } else {
                Symbol namespaceName = functionName.getNamespaceSymbol();
                NamespaceDefinition namespaceDefinition = resolvedNamespaceDefinition(namespaceName);
                if (namespaceDefinition == null) {
                    reportMessage(A_0019.render(location, namespaceName));
                    return;
                }

                functionDefinition = namespaceDefinition.getFunctionDefinitions().get(functionName.getNameSymbol());
                if (functionDefinition == null) {
                    reportMessage(A_0016.render(location, functionName.getNameSymbol(), functionName.getNamespaceSymbol()));
                    return;
                }

                if (functionDefinition.isPrivate()) {
                    reportMessage(A_0017.render(location, functionName));
                }
            }

            int functionArity = functionDefinition.getArity();
            if (functionArity != passedArgumentCount) {
                reportMessage(A_0018.render(location, functionDefinition.getName(), functionArity, passedArgumentCount));
            }
        }

        @Override
        public Void visitChildren(RuleNode node) {
            inheritSymbols(node);
            return super.visitChildren(node);
        }

        @Override
        public Void visitImportDeclaration(AgentScriptParser.ImportDeclarationContext ctx) {
            Symbol namespaceName = Symbol.asNamespaceSymbol(ctx.nameSymbol.getText());
            if (!namespaceBundle.getNamespaceDefinitions().containsKey(namespaceName)) {
                reportMessage(A_0019.render(getLocation(ctx.nameSymbol), namespaceName));
                return null;
            }

            return null;
        }

        @Override
        public Void visitFunctionDefinition(AgentScriptParser.FunctionDefinitionContext ctx) {
            inheritSymbols(ctx);
            ctx.argumentSymbols.forEach(token ->
                    symbolTable.computeIfAbsent(ctx.block(), key -> new HashSet<>()).add(Symbol.asNameSymbol(token.getText())));
            return super.visitFunctionDefinition(ctx);
        }

        @Override
        public Void visitAssignStatement(AgentScriptParser.AssignStatementContext ctx) {
            inheritSymbols(ctx);

            String variableNameText = ctx.nameSymbol.getText();
            Symbol variableName = Symbol.asNameSymbol(variableNameText);
            if (variableName == null) {
                reportMessage(A_0020.render(getLocation(ctx.nameSymbol), variableNameText));
                return super.visitAssignStatement(ctx);
            }

            getNextStatements(ctx.getParent()).forEach(siblingCtx ->
                    symbolTable.computeIfAbsent(siblingCtx, key -> new HashSet<>()).add(variableName));
            return super.visitAssignStatement(ctx);
        }

        @Override
        public Void visitFunctionCallStatement(AgentScriptParser.FunctionCallStatementContext ctx) {
            inheritSymbols(ctx);
            visitFunctionCall(ctx, ctx.functionName, ctx.argumentExpressions.size());
            return super.visitFunctionCallStatement(ctx);
        }

        @Override
        public Void visitSymbolExpression(AgentScriptParser.SymbolExpressionContext ctx) {
            inheritSymbols(ctx);
            Location location = getLocation(ctx.symbol);
            String symbolNameText = ctx.symbol.getText();
            Symbol symbolName = Symbol.asMaybeQualifiedSymbol(symbolNameText);
            if (symbolName == null) {
                reportMessage(A_0020.render(location, symbolNameText));
                return null;
            }

            if (symbolName.isNameSymbol()) {
                if (!symbolTable.get(ctx).contains(symbolName)) {
                    ConstantDefinition constantDefinition =
                            namespaceBundle.getNamespaceDefinition().getConstantDefinitions().get(symbolName.getNameSymbol());
                    if (constantDefinition == null) {
                        reportMessage(A_0021.render(location, symbolName));
                        return null;
                    }
                }
            } else {
                Symbol namespaceName = symbolName.getNamespaceSymbol();
                NamespaceDefinition namespaceDefinition = resolvedNamespaceDefinition(namespaceName);
                if (namespaceDefinition == null) {
                    reportMessage(A_0019.render(location, namespaceName));
                    return null;
                }

                ConstantDefinition constantDefinition = namespaceDefinition.getConstantDefinitions().get(symbolName.getNameSymbol());
                if (constantDefinition == null) {
                    reportMessage(A_0025.render(location, symbolName.getNameSymbol(), symbolName.getNamespaceSymbol()));
                    return null;
                }

                if (constantDefinition.isPrivate()) {
                    reportMessage(A_0022.render(location, symbolName));
                    return null;
                }
            }

            return null;
        }

        @Override
        public Void visitFunctionCallExpression(AgentScriptParser.FunctionCallExpressionContext ctx) {
            inheritSymbols(ctx);
            visitFunctionCall(ctx, ctx.functionName, ctx.argumentExpressions.size());
            return super.visitFunctionCallExpression(ctx);
        }
    }
}
