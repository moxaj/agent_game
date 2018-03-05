package agent_game.compiler;

import agent_game.agent.IAgentAction;
import agent_game.agent.IAgentMemory;
import agent_game.agent.IAgentPerception;
import agent_game.script.*;
import agent_game.simulator.IAgentGameSimulatorEnvironment;
import org.stringtemplate.v4.ST;

// TODO abstract: IAgentScriptEmitter?
/**
 * Java code generation templates.
 */
public class AgentScriptTemplates {
    /**
     * The name of the {@link IAgentGameSimulatorEnvironment} parameter.
     */
    private static final String PARAMETER_ENVIRONMENT = "environment";

    /**
     * The name of the {@link IAgentMemory} parameter.
     */
    private static final String PARAMETER_MEMORY = "memory";

    /**
     * The name of the {@link IAgentPerception} parameter.
     */
    private static final String PARAMETER_PERCEPTION = "perception";

    /**
     * The name of the hidden {@link IAgentAction} variable.
     */
    private static final String LOCAL_ACTION = "$action";

    /**
     * The name of the hidden {@link IAgentScriptLocalStack} variable.
     */
    private static final String LOCAL_LOCAL_STACK = "$localStack";

    /**
     * Returns the fully qualified name of a class.
     *
     * @param c the class
     * @return the fully qualified name of the class
     */
    private static String qualifiedName(Class<?> c) {
        return c.getCanonicalName();
    }

    /**
     * Returns the code fragment which coerces an expression to a boolean.
     *
     * @param expression the expression
     * @return the code fragment which coerces the expression to a boolean
     */
    private static String asBoolean(String expression) {
        return new ST("<runtimeClass>.asBoolean(<expression>)")
                .add("runtimeClass", qualifiedName(AgentScriptRuntime.class))
                .add("expression", expression)
                .render();
    }

    /**
     * Returns the code fragment which coerces an expression to a number.
     *
     * @param expression the expression
     * @return the code fragment which coerces the expression to a number
     */
    private static String asNumber(String expression) {
        return new ST("<runtimeClass>.asNumber(<expression>)")
                .add("runtimeClass", qualifiedName(AgentScriptRuntime.class))
                .add("expression", expression)
                .render();
    }

    /**
     * Returns the code block prefixed by a timeout check.
     *
     * @param block the code block
     * @return the code block prefixed by a timeout check
     */
    private static String withTimedOutCheck(String block) {
        return new ST("" +
                "<environment>.checkTimedOut();\n" +
                "<block>")
                .add("environment", PARAMETER_ENVIRONMENT)
                .add("block", block)
                .render();
    }

    /**
     * Wraps the code block with an emulated local variable stack.
     *
     * @param block the code block to wrap
     * @return the wrapped code block
     */
    private static String withLexicalScope(String block) {
        return block.isEmpty()
                ? ""
                : new ST("" +
                "<localStack> = <localStack>.push();\n" +
                "<block>\n" +
                "<localStack> = <localStack>.pop();")
                .add("localStack", LOCAL_LOCAL_STACK)
                .add("block", block)
                .render();
    }

    /**
     * @param expression
     * @return
     */
    public static String wrappedExpression(String expression) {
        return new ST("" +
                "(<expression>)")
                .add("expression", expression)
                .render();
    }

    /**
     * @param packageName
     * @param className
     * @param constDeclarations
     * @param bodyStatements
     * @return
     */
    public static String script(String packageName, String className, String constDeclarations, String bodyStatements) {
        return new ST("" +
                "package <packageName>;\n" +

                "public class <className> implements <interfaceName> {\n" +
                "    <constDeclarations>\n" +

                "    @Override\n" +
                "    public <actionType> execute(\n" +
                "        <environmentType> <environment>,\n" +
                "        <memoryType> <memory>,\n" +
                "        <perceptionType> <perception>)\n" +
                "        throws InterruptedException {\n" +

                "        <actionType> <action> = null;\n" +
                "        <localStackType> <localStack> = <localStackType>.EMPTY;\n" +

                "        <bodyStatements>\n" +

                "        if (<action> == null) {\n" +
                "             throw new <exceptionType>();" +
                "        }\n" +
                "        return <action>;\n" +
                "    }\n" +
                "}")
                .add("packageName", packageName)
                .add("className", className)
                .add("interfaceName", qualifiedName(IAgentScript.class))
                .add("constDeclarations", constDeclarations)
                .add("actionType", qualifiedName(IAgentAction.class))
                .add("action", LOCAL_ACTION)
                .add("environmentType", qualifiedName(IAgentGameSimulatorEnvironment.class))
                .add("environment", PARAMETER_ENVIRONMENT)
                .add("memoryType", qualifiedName(IAgentMemory.class))
                .add("memory", PARAMETER_MEMORY)
                .add("perceptionType", qualifiedName(IAgentPerception.class))
                .add("perception", PARAMETER_PERCEPTION)
                .add("localStackType", qualifiedName(AgentScriptLocalStack.class))
                .add("localStack", LOCAL_LOCAL_STACK)
                .add("bodyStatements", bodyStatements)
                .add("exceptionType", qualifiedName(AgentScriptRuntimeException.class)) // TODO reason
                .render();
    }

    /**
     * @param name
     * @param value
     * @return
     */
    public static String constDeclaration(String name, String value) {
        return new ST("" +
                "private static final Object <name> = <value>;")
                .add("name", name)
                .add("value", value)
                .render();
    }

    /**
     * @param bodyStatements
     * @return
     */
    public static String strategy(String bodyStatements) {
        return new ST("" +
                "<bodyStatements>")
                .add("bodyStatements", bodyStatements)
                .render();
    }

    /**
     * @param name
     * @param value
     * @return
     */
    public static String localAssignStatement(String name, String value) {
        return new ST("<localStack>.setLocal(\"<name>\", <value>);")
                .add("localStack", LOCAL_LOCAL_STACK)
                .add("name", name)
                .add("value", value)
                .render();
    }

    /**
     * @param name
     * @param value
     * @return
     */
    public static String memoryAssignStatement(String name, String value) {
        return new ST("<memory>.setValue(\"<name>\", <value>);")
                .add("memory", PARAMETER_MEMORY)
                .add("name", name)
                .add("value", value)
                .render();
    }

    /**
     * @param condition
     * @param bodyStatements
     * @param elseIfStatements
     * @param elseStatement
     * @return
     */
    public static String ifStatement(String condition, String bodyStatements, String elseIfStatements, String
            elseStatement) {
        return new ST("" +
                "if (<condition>) {\n" +
                "    <bodyStatements>\n" +
                "} <elseIfStatements> <elseStatement>")
                .add("condition", asBoolean(condition))
                .add("bodyStatements", withLexicalScope(bodyStatements))
                .add("elseIfStatements", elseIfStatements)
                .add("elseStatement", elseStatement)
                .render();
    }

    /**
     * @param condition
     * @param bodyStatements
     * @return
     */
    public static String elseIfStatement(String condition, String bodyStatements) {
        return new ST("" +
                "else if (<condition>) {\n" +
                "    <bodyStatements>\n" +
                "}")
                .add("condition", asBoolean(condition))
                .add("bodyStatements", withLexicalScope(bodyStatements))
                .render();
    }

    /**
     * @param bodyStatements
     * @return
     */
    public static String elseStatement(String bodyStatements) {
        return new ST("" +
                "else {\n" +
                "    <bodyStatements>\n" +
                "}")
                .add("bodyStatements", withLexicalScope(bodyStatements))
                .render();
    }

    /**
     * @param testCondition
     * @param bodyStatements
     * @return
     */
    public static String whileStatement(String testCondition, String bodyStatements) {
        return new ST("" +
                "while (<testCondition>) {\n" +
                "    <bodyStatements>\n" +
                "}")
                .add("testCondition", asBoolean(testCondition))
                .add("bodyStatements", withTimedOutCheck(withLexicalScope(bodyStatements)))
                .render();
    }

    /**
     * @param value
     * @return
     */
    public static String agentActionStatement(String value) {
        return new ST("" +
                "if (<action> != null) {\n" +
                "    throw new <exceptionType>();\n" +
                "} else {\n" +
                "    <action> = <value>;\n" +
                "}")
                .add("action", LOCAL_ACTION)
                .add("exceptionType", qualifiedName(AgentScriptRuntimeException.class)) // TODO reason
                .add("value", "null") // TODO parse value
                .render();
    }

    /**
     * @param name
     * @return
     */
    public static String localIdentifierExpression(String name) {
        return new ST("" +
                "<localStack>.getLocal(\"<name>\")")
                .add("localStack", LOCAL_LOCAL_STACK)
                .add("name", name)
                .render();
    }

    /**
     * @param name
     * @return
     */
    public static String constIdentifierExpression(String name) {
        return new ST("" +
                "<name>")
                .add("name", name)
                .render();
    }

    /**
     * @param name
     * @return
     */
    public static String memoryIdentifierExpression(String name) {
        return new ST("" +
                "<memory>.getValue(\"<name>\")")
                .add("memory", PARAMETER_MEMORY)
                .add("name", name)
                .render();
    }

    /**
     * @param expression
     * @return
     */
    public static String unaryBooleanExpression(String expression) {
        return new ST("" +
                "<runtimeClass>.not(<expression>)")
                .add("runtimeClass", AgentScriptRuntime.class)
                .add("expression", expression)
                .render();
    }

    /**
     * @param leftExpression
     * @param operator
     * @param rightExpression
     * @return
     */
    public static String binaryBooleanChainExpression(String leftExpression, String operator, String rightExpression) {
        return new ST("" +
                "<leftExpression> <operator> <rightExpression>")
                .add("leftExpression", asBoolean(leftExpression))
                .add("operator", operator)
                .add("rightExpression", asBoolean(rightExpression))
                .render();
    }

    /**
     * @param leftExpression
     * @param operator
     * @param rightExpression
     * @return
     */
    public static String binaryBooleanRelationExpression(String leftExpression, String operator, String
            rightExpression) {
        return new ST("" +
                "<leftExpression> <operator> <rightExpression>")
                .add("leftExpression", asNumber(leftExpression))
                .add("operator", operator)
                .add("rightExpression", asNumber(rightExpression))
                .render();
    }

    /**
     * @param leftExpression
     * @param operator
     * @param rightExpression
     * @return
     */
    public static String binaryNumberExpression(String leftExpression, String operator, String rightExpression) {
        return new ST("" +
                "<leftExpression> <operator> <rightExpression>")
                .add("leftExpression", asNumber(leftExpression))
                .add("operator", operator)
                .add("rightExpression", asNumber(rightExpression))
                .render();
    }
}
