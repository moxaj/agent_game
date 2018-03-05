package agent_game.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link IAgentMemory} implementation.
 */
public class AgentMemory implements IAgentMemory {
    private final Map<String, Object> values = new HashMap<>();

    @Override
    public Object getValue(String name) {
        return values.get(name);
    }

    @Override
    public void setValue(String name, Object value) {
        values.put(name, value);
    }
}
