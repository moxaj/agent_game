package agent_game.simulator;

import agent_game.agent.IAgent;
import agent_game.agent.IAgentAction;
import agent_game.agent.IAgentPerception;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * The agent game simulator.
 */
public class AgentGameSimulator {
    private final Logger logger;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final List<IAgent> agents = new ArrayList<>();

    // TODO work in progress

    public AgentGameSimulator(Logger logger) {
        this.logger = logger;
    }

    public void runAgentScript(IAgent agent) {
        IAgentPerception perception = null; // TODO construct current perception
        IAgentGameSimulatorEnvironment environment = null; // TODO setup environment

        Future<IAgentAction> scriptFuture = executorService.submit(() -> {
            try {
                return agent.getScript().execute(environment, agent.getMemory(), perception);
            } catch (InterruptedException e) {
                return null;
            }
        });

        IAgentAction agentAction = null;
        long timeElapsed = 0;

        try {
            long timeBefore = System.currentTimeMillis();
            agentAction = scriptFuture.get(agent.getRemainingTimeQuota(), TimeUnit.MILLISECONDS);
            timeElapsed = System.currentTimeMillis() - timeBefore;
        } catch (InterruptedException e) {
            // Should not happen
            throw new RuntimeException();
        } catch (ExecutionException e) {
            // TODO log agent script exception
            scriptFuture.cancel(true);
        } catch (TimeoutException e) {
            // TODO log agent timeout
            scriptFuture.cancel(true);
        }

        if (agentAction == null) {
            // TODO terminate simulation
            return;
        }

        agent.decreaseRemainingTimeQuota(timeElapsed);
        // TODO apply agentAction
    }
}
