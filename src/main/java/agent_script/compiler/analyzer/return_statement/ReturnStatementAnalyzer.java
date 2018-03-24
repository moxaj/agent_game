package agent_script.compiler.analyzer.return_statement;

import agent_script.antlr.AgentScriptBaseVisitor;
import agent_script.antlr.AgentScriptParser;
import agent_script.compiler.*;
import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.FunctionDefinition;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static agent_script.compiler.CompilerMessageTemplates.A_0014;
import static agent_script.compiler.CompilerMessageTemplates.A_0015;

/**
 * Warns if there are any missing return statements or unreachable statements in a function.
 */
public class ReturnStatementAnalyzer extends CompilerProcessor implements ICompilerProcessor {
    public ReturnStatementAnalyzer(CompilerMessageReporter messageReporter) {
        super(messageReporter);
    }

    @Override
    public void process(NamespaceBundle namespaceBundle) throws CompilerException {
        this.namespaceBundle = namespaceBundle;
        new Visitor().visit(namespaceBundle.getParseTree());
    }

    /**
     * Represents the return state.
     */
    private enum ReturnState {
        /**
         * No return statements up to the current statement.
         */
        NEVER,

        /**
         * Return statements in some previous statements, but not in all.
         */
        SOMETIMES,

        /**
         * Return statements in all prior statements.
         */
        ALWAYS,

        /**
         * There were statements after an ALWAYS state, leading to unreachable code.
         */
        INVALID;

        /**
         * Combines this return state with another which belongs to the next statement.
         *
         * @param other the other return state
         * @return the combined return state
         */
        public ReturnState combineSequential(ReturnState other) {
            if (this == INVALID || other == INVALID || this == ALWAYS) {
                return INVALID;
            }

            if (other == ALWAYS) {
                return ALWAYS;
            }

            if (this == NEVER && other == NEVER) {
                return NEVER;
            }

            return SOMETIMES;
        }
    }

    /**
     * The visitor which traverses the parse tree.
     */
    private class Visitor extends AgentScriptBaseVisitor<Void> {
        private final Map<ParseTree, ReturnState> returnStates = new HashMap<>();

        /**
         * Folds {@link ReturnState#combineSequential(ReturnState)} over a list of statement contexts.
         *
         * @param contexts the statement contexts
         * @return the final return state
         */
        private ReturnState accumulateSequential(List<? extends ParserRuleContext> contexts) {
            contexts.forEach(this::visit);

            int size = contexts.size();
            if (size == 0) {
                return ReturnState.NEVER;
            }

            ReturnState returnState = returnStates.get(contexts.get(0));
            for (ParserRuleContext ctx : contexts.subList(1, size)) {
                returnState = returnState.combineSequential(returnStates.get(ctx));
                if (returnState == ReturnState.INVALID) {
                    reportMessage(A_0014.render(getLocation(ctx.start)));
                    return ReturnState.INVALID;
                }
            }

            return returnState;
        }

        @Override
        public Void visitFunctionDefinition(AgentScriptParser.FunctionDefinitionContext ctx) {
            Symbol functionName = Symbol.asNameSymbol(ctx.nameSymbol.getText());
            if (functionName == null) {
                return null;
            }

            FunctionDefinition functionDefinition = resolvedFunctionDefinition(functionName);

            switch (accumulateSequential(ctx.bodyStatements)) {
                case NEVER:
                    functionDefinition.setHasExplicitReturn(false);
                    break;
                case SOMETIMES:
                    functionDefinition.setHasExplicitReturn(false);
                    reportMessage(A_0015.render(getLocation(ctx.nameSymbol), functionName));
                    return null;
                case ALWAYS:
                    functionDefinition.setHasExplicitReturn(true);
                    break;
                case INVALID:
                    functionDefinition.setHasExplicitReturn(false);
                    break;
                default:
                    throw new RuntimeException();
            }

            return null;
        }

        @Override
        public Void visitStatement(AgentScriptParser.StatementContext ctx) {
            ParserRuleContext childCtx;
            if ((childCtx = ctx.returnStatement()) != null) {
                visit(childCtx);
                returnStates.put(ctx, ReturnState.ALWAYS);
            } else if ((childCtx = ctx.ifStatement()) != null) {
                visit(childCtx);
                returnStates.put(ctx, returnStates.get(childCtx));
            } else if ((childCtx = ctx.whileStatement()) != null) {
                visit(childCtx);
                returnStates.put(ctx, returnStates.get(childCtx));
            } else {
                returnStates.put(ctx, ReturnState.NEVER);
            }

            return null;
        }

        @Override
        public Void visitIfStatement(AgentScriptParser.IfStatementContext ctx) {
            List<ReturnState> ifReturnStates = new ArrayList<>();
            ifReturnStates.add(accumulateSequential(ctx.bodyStatements));
            ifReturnStates.addAll(ctx.elseIfStatements
                    .stream()
                    .map(elseIfStatementContext -> accumulateSequential(elseIfStatementContext.bodyStatements))
                    .collect(Collectors.toList()));

            AgentScriptParser.ElseStatementContext elseStatementContext = ctx.elseStatement();
            ifReturnStates.add(elseStatementContext == null
                    ? ReturnState.NEVER
                    : accumulateSequential(elseStatementContext.bodyStatements));

            ReturnState returnState;
            if (ifReturnStates.stream().allMatch(ReturnState.NEVER::equals)) {
                returnState = ReturnState.NEVER;
            } else if (ifReturnStates.stream().allMatch(ReturnState.ALWAYS::equals)) {
                returnState = ReturnState.ALWAYS;
            } else if (ifReturnStates.stream().anyMatch(ReturnState.INVALID::equals)) {
                returnState = ReturnState.INVALID;
            } else {
                returnState = ReturnState.SOMETIMES;
            }

            returnStates.put(ctx, returnState);
            return null;
        }

        @Override
        public Void visitWhileStatement(AgentScriptParser.WhileStatementContext ctx) {
            ReturnState returnState = accumulateSequential(ctx.bodyStatements);
            if (returnState == ReturnState.ALWAYS) {
                returnState = ReturnState.SOMETIMES;
            }

            returnStates.put(ctx, returnState);
            return null;
        }
    }
}
