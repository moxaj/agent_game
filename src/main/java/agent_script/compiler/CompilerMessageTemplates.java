package agent_script.compiler;

import java.lang.reflect.Field;

import static agent_script.compiler.CompilerMessageTemplate.CompilationStage.*;
import static agent_script.compiler.CompilerMessageTemplate.Level.*;

/**
 * A utility class where all the {@link CompilerMessageTemplate} instances are defined.
 */
public final class CompilerMessageTemplates {
    // Compiler
    public static final CompilerMessageTemplate C_0000 =
            create(COMPILER, ERROR, "could not access root source path: '%s'");
    public static final CompilerMessageTemplate C_0001 =
            create(COMPILER, ERROR, "namespace with given name already defined: '%s'");
    public static final CompilerMessageTemplate C_0002 =
            create(COMPILER, ERROR, "circular dependency between namespaces: %s");
    public static final CompilerMessageTemplate C_0003 =
            create(COMPILER, INFO, "compilation started with root source paths: %s");
    public static final CompilerMessageTemplate C_0004 =
            create(COMPILER, INFO, "compilation finished with %d error(s) in %.3f seconds");

    // Parser
    public static final CompilerMessageTemplate P_0000 =
            create(PARSER, ERROR, "could not read file at path: %s");
    public static final CompilerMessageTemplate P_0001 =
            create(PARSER, ERROR, "%s");

    // Analyzer
    public static final CompilerMessageTemplate A_0000 =
            create(ANALYZER, ERROR, "duplicate meta: '%s'");
    public static final CompilerMessageTemplate A_0001 =
            create(ANALYZER, WARNING, "unknown meta: '%s'");
    public static final CompilerMessageTemplate A_0002 =
            create(ANALYZER, ERROR, "invalid namespace name: '%s'");
    public static final CompilerMessageTemplate A_0003 =
            create(ANALYZER, ERROR, "namespace name does not match the namespace path: '%s' - '%s'");
    public static final CompilerMessageTemplate A_0004 =
            create(ANALYZER, ERROR, "cannot import current namespace: '%s'");
    public static final CompilerMessageTemplate A_0005 =
            create(ANALYZER, ERROR, "invalid namespace alias: '%s'");
    public static final CompilerMessageTemplate A_0006 =
            create(ANALYZER, ERROR, "conflicting namespace alias: '%s'");
    public static final CompilerMessageTemplate A_0007 =
            create(ANALYZER, ERROR, "conflicting namespace name: '%s'");
    public static final CompilerMessageTemplate A_0008 =
            create(ANALYZER, ERROR, "invalid constant name: '%s'");
    public static final CompilerMessageTemplate A_0009 =
            create(ANALYZER, ERROR, "duplicate constant definition: '%s'");
    public static final CompilerMessageTemplate A_0010 =
            create(ANALYZER, ERROR, "invalid function name: '%s'");
    public static final CompilerMessageTemplate A_0011 =
            create(ANALYZER, ERROR, "duplicate function definition: '%s'");
    public static final CompilerMessageTemplate A_0012 =
            create(ANALYZER, ERROR, "missing 'native' meta from constant: '%s'");
    public static final CompilerMessageTemplate A_0013 =
            create(ANALYZER, ERROR, "missing 'native' meta from function: '%s'");
    public static final CompilerMessageTemplate A_0014 =
            create(ANALYZER, ERROR, "unreachable statement");
    public static final CompilerMessageTemplate A_0015 =
            create(ANALYZER, ERROR, "missing return statement from function: '%s'");
    public static final CompilerMessageTemplate A_0016 =
            create(ANALYZER, ERROR, "no such function: '%s' in namespace: '%s'");
    public static final CompilerMessageTemplate A_0017 =
            create(ANALYZER, ERROR, "function is private: '%s'");
    public static final CompilerMessageTemplate A_0018 =
            create(ANALYZER, ERROR, "function called with invalid arity: '%s', expected %d, got %d");
    public static final CompilerMessageTemplate A_0019 =
            create(ANALYZER, ERROR, "no such namespace: '%s'");
    public static final CompilerMessageTemplate A_0020 =
            create(ANALYZER, ERROR, "invalid variable or constant name: '%s'");
    public static final CompilerMessageTemplate A_0021 =
            create(ANALYZER, ERROR, "no such constant or local variable in scope: '%s'");
    public static final CompilerMessageTemplate A_0022 =
            create(ANALYZER, ERROR, "constant is private: '%s'");
    public static final CompilerMessageTemplate A_0023 =
            create(ANALYZER, ERROR, "operator '%s' cannot be applied to operand(s) of type(s): %s");
    public static final CompilerMessageTemplate A_0024 =
            create(ANALYZER, WARNING, "operands are never equal");
    public static final CompilerMessageTemplate A_0025 =
            create(ANALYZER, ERROR, "no such constant: '%s' in namespace: '%s'");
    public static final CompilerMessageTemplate A_0026 =
            create(ANALYZER, WARNING, "unnecessary 'native' meta on constant: '%s'");
    public static final CompilerMessageTemplate A_0027 =
            create(ANALYZER, WARNING, "unnecessary 'native' meta on function: '%s'");

    static {
        // I'm lazy
        for (Field field : CompilerMessageTemplates.class.getFields()) {
            try {
                CompilerMessageTemplate messageTemplate = (CompilerMessageTemplate) field.get(null);
                messageTemplate.setId(field.getName());
            } catch (Exception e) {
                // Should not happen
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates a new compiler message template.
     *
     * @param compilationStage the compilation stage of the template
     * @param level            the level of the template
     * @param messageTemplate  the actual text of the template
     * @return the new message template
     */
    private static CompilerMessageTemplate create(
            CompilerMessageTemplate.CompilationStage compilationStage,
            CompilerMessageTemplate.Level level,
            String messageTemplate) {
        return new CompilerMessageTemplate(compilationStage, level, messageTemplate);
    }
}
