package agent_script.compiler;

import agent_script.compiler.analyzer.Location;

/**
 * Represents a compiler message template.
 */
public class CompilerMessageTemplate {
    /**
     * The compilation stage where the message was produced.
     */
    private final CompilationStage compilationStage;
    /**
     * The level of the message.
     */
    private final Level level;
    /**
     * The raw message template to be rendered later.
     */
    private final String messageTemplate;
    /**
     * The unique identifier of the message.
     */
    private /*final*/ String id;

    public CompilerMessageTemplate(CompilationStage compilationStage, Level level, String messageTemplate) {
        this.compilationStage = compilationStage;
        this.level = level;
        this.messageTemplate = messageTemplate;
    }

    /**
     * @return the value of {@link #id}
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the value of {@link #id}
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the value of {@link #compilationStage}
     */
    public CompilationStage getCompilationStage() {
        return compilationStage;
    }

    /**
     * @return the value of {@link #level}
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @return the value of {@link #messageTemplate}
     */
    public String getMessageTemplate() {
        return messageTemplate;
    }

    /**
     * Renders the message template.
     *
     * @param location            the location to which the message points
     * @param messageTemplateArgs the template arguments
     * @return the rendered message
     */
    public CompilerMessage render(Location location, Object... messageTemplateArgs) {
        return new CompilerMessage(
                getId(),
                getCompilationStage(),
                getLevel(),
                location,
                String.format(getMessageTemplate(), messageTemplateArgs));
    }

    @Override
    public String toString() {
        return String.format("%s: %s", id, messageTemplate);
    }

    /**
     * The severity of the message.
     */
    enum Level {
        /**
         * Information about the compilation process.
         */
        INFO,

        /**
         * Most likely an error, but won't halt compilation.
         */
        WARNING,

        /**
         * Severe error which halts the compilation.
         */
        ERROR;

        /**
         * Maps the level to an appropriate {@link java.util.logging.Level} instance.
         *
         * @return the {@link java.util.logging.Level} instance
         */
        public java.util.logging.Level toLoggingLevel() {
            switch (this) {
                case INFO:
                    return java.util.logging.Level.INFO;
                case WARNING:
                    return java.util.logging.Level.WARNING;
                case ERROR:
                    return java.util.logging.Level.SEVERE;
                default:
                    throw new RuntimeException();
            }
        }
    }

    /**
     * The compilation stage.
     */
    enum CompilationStage {
        /**
         * General compiler stage.
         */
        COMPILER,

        /**
         * Loading and parsing of the sources.
         */
        PARSER,

        /**
         * Analysis of the parse trees.
         */
        ANALYZER,

        /**
         * Java compilation and class loading.
         */
        JAVA;
    }
}
