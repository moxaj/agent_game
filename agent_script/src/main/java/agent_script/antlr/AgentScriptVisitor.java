// Generated from AgentScript.g4 by ANTLR 4.7.1
package agent_script.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AgentScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AgentScriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#script}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScript(AgentScriptParser.ScriptContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#meta}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeta(AgentScriptParser.MetaContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#namespaceDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespaceDefinition(AgentScriptParser.NamespaceDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(AgentScriptParser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#constantDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDefinition(AgentScriptParser.ConstantDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(AgentScriptParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(AgentScriptParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(AgentScriptParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#assignStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStatement(AgentScriptParser.AssignStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(AgentScriptParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#elseIfStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfStatement(AgentScriptParser.ElseIfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#elseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStatement(AgentScriptParser.ElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(AgentScriptParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(AgentScriptParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#functionCallStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallStatement(AgentScriptParser.FunctionCallStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AgentScriptParser#nativeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNativeStatement(AgentScriptParser.NativeStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenWrappedExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenWrappedExpression(AgentScriptParser.ParenWrappedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMathExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMathExpression(AgentScriptParser.UnaryMathExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationExpression(AgentScriptParser.RelationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(AgentScriptParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code symbolExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbolExpression(AgentScriptParser.SymbolExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(AgentScriptParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nativeExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNativeExpression(AgentScriptParser.NativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(AgentScriptParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(AgentScriptParser.FunctionCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nilExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNilExpression(AgentScriptParser.NilExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpression(AgentScriptParser.LiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryMathExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryMathExpression(AgentScriptParser.BinaryMathExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryBooleanExpression}
	 * labeled alternative in {@link AgentScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryBooleanExpression(AgentScriptParser.UnaryBooleanExpressionContext ctx);
}