package agent_game.simulator;

/**
 * Represents the simulator environment visible to the agent scripts. Provides access
 * to some meta facilities (e.g. logging).
 */
public interface IAgentGameSimulatorEnvironment {
    /**
     * Checks whether the agent has timed out.
     *
     * @throws InterruptedException if the agent times out
     */
    default void checkTimedOut() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
    }

    // TODO logging
}
