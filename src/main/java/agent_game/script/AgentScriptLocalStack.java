package agent_game.script;

import java.util.HashMap;
import java.util.Map;

// TODO evaluate whether this implementation is fast enough / runs into memory issues

/**
 * {@link IAgentScriptLocalStack} implementation.
 */
public class AgentScriptLocalStack implements IAgentScriptLocalStack {
    public static final AgentScriptLocalStack EMPTY = new AgentScriptLocalStack(null);

    private final AgentScriptLocalStack parent;
    private final Map<String, Object> locals = new HashMap<>();

    protected AgentScriptLocalStack(AgentScriptLocalStack parent) {
        this.parent = parent;

        if (parent != null) {
            locals.putAll(parent.locals);
        }
    }

    @Override
    public Object getLocal(String name) {
        return locals.get(name);
    }

    @Override
    public void setLocal(String name, Object value) {
        if (parent != null && parent.locals.containsKey(name)) {
            parent.setLocal(name, value);
        }

        locals.put(name, value);
    }

    @Override
    public AgentScriptLocalStack push() {
        return new AgentScriptLocalStack(this);
    }

    @Override
    public AgentScriptLocalStack pop() {
        return parent;
    }
}
