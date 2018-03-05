package agent_game.compiler;

import agent_game.antlr.AgentScriptParser;
import agent_game.antlr.AgentScriptVisitor;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link AgentScriptVisitor} implementation.
 * <p>
 * Visits the ANTLR generated AST and emits java source code.
 */
public class AgentScriptEmitterVisitor extends AbstractParseTreeVisitor<String> implements AgentScriptVisitor<String> {
    /**
     * The package name of the generated java class.
     */
    private final String javaPackageName;

    /**
     * The name of the generated java class.
     */
    private final String javaClassName;

    public AgentScriptEmitterVisitor(String javaPackageName, String javaClassName) {
        this.javaPackageName = javaPackageName;
        this.javaClassName = javaClassName;
    }

    /**
     * Visits the parse tree in a safe manner.
     *
     * @param parseTree the {@link ParseTree} to visit
     * @return the visited parseTree
     */
    private String safeVisit(ParseTree parseTree) {
        return parseTree == null ? "" : visit(parseTree);
    }

    /**
     * Visits each parse tree and concatenates their output.
     *
     * @param parseTrees the {@link ParseTree} instances to visit
     * @return the concatenated output
     */
    private String visitEach(List<? extends ParseTree> parseTrees) {
        return parseTrees.stream().map(this::visit).collect(Collectors.joining());
    }

    @Override
    public String visitScript(AgentScriptParser.ScriptContext ctx) {
        String source = AgentScriptTemplates.script(
                javaPackageName,
                javaClassName,
                ctx.constDeclarations.stream().map(this::visit).collect(Collectors.joining()),
                visit(ctx.strategy()));

        String formattedSource;
        try {
            formattedSource = new Formatter().formatSource(source);

            // Debug
            System.out.println(formattedSource);
        } catch (FormatterException e) {
            // Should not happen

            // Debug
            System.out.println(source);

            throw new RuntimeException(e);
        }

        return formattedSource;
    }

    @Override
    public String visitConstDeclaration(AgentScriptParser.ConstDeclarationContext ctx) {
        return AgentScriptTemplates.constDeclaration(ctx.name.getText(), visit(ctx.value));
    }

    @Override
    public String visitStrategy(AgentScriptParser.StrategyContext ctx) {
        return AgentScriptTemplates.strategy(visitEach(ctx.bodyStatements));
    }

    @Override
    public String visitStatement(AgentScriptParser.StatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitLocalAssignStatement(AgentScriptParser.LocalAssignStatementContext ctx) {
        return AgentScriptTemplates.localAssignStatement(ctx.name.getText(), visit(ctx.value));
    }

    @Override
    public String visitMemoryAssignStatement(AgentScriptParser.MemoryAssignStatementContext ctx) {
        return AgentScriptTemplates.memoryAssignStatement(ctx.name.getText(), visit(ctx.value));
    }

    @Override
    public String visitIfStatement(AgentScriptParser.IfStatementContext ctx) {
        return AgentScriptTemplates.ifStatement(
                visit(ctx.testCondition),
                visitEach(ctx.bodyStatements),
                visitEach(ctx.elseIfStatements),
                safeVisit(ctx.elseStatement()));
    }

    @Override
    public String visitElseIfStatement(AgentScriptParser.ElseIfStatementContext ctx) {
        return AgentScriptTemplates.elseIfStatement(
                visit(ctx.testCondition),
                visitEach(ctx.bodyStatements));
    }

    @Override
    public String visitElseStatement(AgentScriptParser.ElseStatementContext ctx) {
        return AgentScriptTemplates.elseStatement(visitEach(ctx.bodyStatements));
    }

    @Override
    public String visitWhileStatement(AgentScriptParser.WhileStatementContext ctx) {
        return AgentScriptTemplates.whileStatement(
                visit(ctx.testCondition),
                visitEach(ctx.bodyStatements));
    }

    @Override
    public String visitActionStatement(AgentScriptParser.ActionStatementContext ctx) {
        return ""; // TODO
    }

    @Override
    public String visitExpression(AgentScriptParser.ExpressionContext ctx) {
        TerminalNode terminalNode;

        if ((terminalNode = ctx.BOOLEAN_LITERAL()) != null) {
            return terminalNode.getText();
        }

        if ((terminalNode = ctx.NUMBER_LITERAL()) != null) {
            return terminalNode.getText();
        }

        return visitChildren(ctx);
    }

    @Override
    public String visitVariableExpression(AgentScriptParser.VariableExpressionContext ctx) {
        if (ctx.innerExpression != null) {
            return AgentScriptTemplates.wrappedExpression(visit(ctx.innerExpression));
        }

        TerminalNode terminalNode;

        if ((terminalNode = ctx.LOCAL_IDENTIFIER()) != null) {
            return AgentScriptTemplates.localIdentifierExpression(terminalNode.getText());
        }

        if ((terminalNode = ctx.CONST_IDENTIFIER()) != null) {
            return AgentScriptTemplates.constIdentifierExpression(terminalNode.getText());
        }

        if ((terminalNode = ctx.MEMORY_IDENTIFIER()) != null) {
            return AgentScriptTemplates.memoryIdentifierExpression(terminalNode.getText());
        }

        throw new RuntimeException();
    }

    @Override
    public String visitBooleanExpression(AgentScriptParser.BooleanExpressionContext ctx) {
        if (ctx.innerExpression != null) {
            return AgentScriptTemplates.wrappedExpression(visit(ctx.innerExpression));
        }

        if (ctx.negatedBooleanExpression != null) {
            return AgentScriptTemplates.unaryBooleanExpression(visit(ctx.negatedBooleanExpression));
        }

        if (ctx.leftBooleanExpression != null) {
            return AgentScriptTemplates.binaryBooleanChainExpression(
                    visit(ctx.leftBooleanExpression),
                    ctx.operator.getText(),
                    visit(ctx.rightBooleanExpression));
        }

        if (ctx.leftNumberExpression != null) {
            return AgentScriptTemplates.binaryBooleanRelationExpression(
                    visit(ctx.leftNumberExpression),
                    ctx.operator.getText(),
                    visit(ctx.rightNumberExpression));
        }

        AgentScriptParser.VariableExpressionContext variableExpressionContext = ctx.variableExpression();
        if (variableExpressionContext != null) {
            return visit(variableExpressionContext);
        }

        TerminalNode terminalNode = ctx.BOOLEAN_LITERAL();
        if (terminalNode != null) {
            return terminalNode.getText();
        }

        throw new RuntimeException();
    }

    @Override
    public String visitNumberExpression(AgentScriptParser.NumberExpressionContext ctx) {
        if (ctx.innerExpression != null) {
            return AgentScriptTemplates.wrappedExpression(visit(ctx.innerExpression));
        }

        if (ctx.leftExpression != null) {
            return AgentScriptTemplates.binaryNumberExpression(
                    visit(ctx.leftExpression),
                    ctx.operator.getText(),
                    visit(ctx.rightExpression));
        }

        AgentScriptParser.VariableExpressionContext variableExpressionContext = ctx.variableExpression();
        if (variableExpressionContext != null) {
            return visit(variableExpressionContext);
        }

        TerminalNode terminalNode = ctx.NUMBER_LITERAL();
        if (terminalNode != null) {
            return terminalNode.getText();
        }

        throw new RuntimeException();
    }
}
