package agent_game.agent;

import agent_game.script.IAgentScript;

/**
 * {@link IAgent} implementation.
 */
public class Agent implements IAgent {
    private final String name;
    private final IAgentMemory memory = new AgentMemory();
    private final IAgentScript script;
    private long remainingTimeQuota;

    // TODO game state

    public Agent(String name, IAgentScript script, long timeQuota) {
        this.name = name;
        this.script = script;
        this.remainingTimeQuota = timeQuota;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IAgentMemory getMemory() {
        return memory;
    }

    @Override
    public IAgentScript getScript() {
        return script;
    }

    @Override
    public long getRemainingTimeQuota() {
        return remainingTimeQuota;
    }

    @Override
    public void decreaseRemainingTimeQuota(long amount) {
        remainingTimeQuota -= amount;
        assert remainingTimeQuota >= 0;
    }
}
