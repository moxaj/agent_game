package agent_game.compiler;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AgentScriptCompilerTest {
    private static String readResource(String resource) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(resource).toURI())));
    }

    @Test
    public void testCompiler() throws URISyntaxException, IOException, AgentScriptCompilerException {
        new AgentScriptCompiler().compile("AgentScript", readResource("test.as"));
    }
}