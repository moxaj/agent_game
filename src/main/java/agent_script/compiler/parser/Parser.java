package agent_script.compiler.parser;

import agent_script.antlr.AgentScriptLexer;
import agent_script.antlr.AgentScriptParser;
import agent_script.compiler.CompilerException;
import agent_script.compiler.CompilerMessageReporter;
import agent_script.compiler.CompilerProcessor;
import agent_script.compiler.NamespaceBundle;
import agent_script.compiler.analyzer.Location;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static agent_script.compiler.CompilerMessageTemplates.P_0000;
import static agent_script.compiler.CompilerMessageTemplates.P_0001;

/**
 * Parses the settings source code into a parse tree.
 */
public final class Parser extends CompilerProcessor {
    public Parser(CompilerMessageReporter messageReporter) {
        super(messageReporter);
    }

    @Override
    public void process(NamespaceBundle namespaceBundle) throws CompilerException {
        this.namespaceBundle = namespaceBundle;

        Path sourcePath = namespaceBundle.getSourcePath();
        String sourcePathStr = sourcePath.toAbsolutePath().toString();

        String scriptSource;
        try {
            scriptSource = new String(Files.readAllBytes(sourcePath));
        } catch (IOException e) {
            reportMessage(P_0000.render(null, sourcePathStr));
            throw new CompilerException(e);
        }

        ANTLRErrorListener errorListener = new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int
                    charPositionInLine, String msg, RecognitionException e) {
                reportMessage(P_0001.render(new Location(sourcePath, line, charPositionInLine), msg));
                throw new CompilerException(e);
            }
        };

        AgentScriptLexer lexer = new AgentScriptLexer(CharStreams.fromString(scriptSource));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        AgentScriptParser parser = new AgentScriptParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        namespaceBundle.setParseTree(parser.script());
    }
}
