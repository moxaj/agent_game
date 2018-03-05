package agent_game.runner;

import agent_game.compiler.AgentScriptCompiler;
import agent_game.compiler.AgentScriptCompilerException;
import agent_game.compiler.IAgentScriptCompiler;
import agent_game.script.IAgentScript;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AgentGameRunner {
    public static void main(String[] args) {
        new AgentGameRunner().run(args);
    }

    private static String readResource(String resource) throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(resource).toURI())));
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    private Logger makeLogger() throws IOException {
        Logger logger = Logger.getLogger("AgentGameLogger");

        Handler systemHandler = new FileHandler("logs/system.log");
        systemHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(systemHandler);

        return logger;
    }

    private IAgentScriptCompiler makeCompiler() throws IOException {
        return new AgentScriptCompiler();
    }

    public void run(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Invalid command line arguments.");
        }

        InputStream setupInputStream = ClassLoader.getSystemResourceAsStream(args[0]);
        if (setupInputStream == null) {
            throw new RuntimeException("Input setup could not be found.");
        }

        IAgentScriptCompiler agentScriptCompiler;
        try {
            agentScriptCompiler = makeCompiler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, IAgentScript> agentScripts = new HashMap<>();

        // TODO abstract
        try {
            JsonObject setup = Json.createReader(setupInputStream).readObject();
            // TODO map

            for (Map.Entry<String, JsonValue> agent : setup.getJsonObject("agents").entrySet()) {
                String agentName = agent.getKey();
                JsonValue agentValue = agent.getValue();
                if (agentValue.getValueType() != JsonValue.ValueType.STRING) {
                    throw new JsonException("Invalid setup.");
                }

                String agentScriptName = ((JsonString) agent.getValue()).getString();
                agentScripts.put(
                        agentName,
                        agentScriptCompiler.compile(agentScriptName, readResource(agentScriptName)));
            }
        } catch (JsonException e) {
            throw new RuntimeException("Setup could not be parsed.");
        } catch (AgentScriptCompilerException | IOException e) {
            throw new RuntimeException(e);
        }

        Logger logger;
        try {
            logger = makeLogger();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
