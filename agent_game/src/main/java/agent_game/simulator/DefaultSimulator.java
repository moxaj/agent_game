package agent_game.simulator;

import agent_game.game.*;
import agent_script.runtime.Runtime;
import agent_script.runtime.ScriptExecutionException;
import agent_script.runtime.ScriptPanicException;
import agent_script.runtime.ScriptTimeoutException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Default {@link Simulator} implementation.
 */
public class DefaultSimulator implements Simulator {
    /**
     * The global {@link Random} instance used for various game events.
     */
    private static final Random RANDOM = new Random();

    /**
     * The game state.
     */
    private final GameState gameState;

    /**
     * The simulator logger.
     */
    private final Logger logger;

    /**
     * The directory where statistics are written.
     */
    private final Path statisticsDirectory;

    /**
     * The executor service for agent scripts.
     */
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Whether the simulator was shut down externally.
     */
    private boolean shutdown;

    public DefaultSimulator(GameState gameState, Logger logger, Path statisticsDirectory) {
        this.gameState = gameState;
        this.logger = logger;
        this.statisticsDirectory = statisticsDirectory;
        this.shutdown = false;
    }

    /**
     * Collects the given pairs of objects into a map.
     *
     * @param objects the pairs of objects
     * @return the map
     */
    private Map<Object, Object> toMap(Object... objects) {
        assert objects.length % 2 == 0;
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < objects.length; i += 2) {
            map.put(objects[i], objects[i + 1]);
        }

        return map;
    }

    /**
     * Collects the given objects into a list.
     *
     * @param objects the objects
     * @return the list
     */
    private List<Object> toList(Object... objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }

    /**
     * @param agent the agent
     * @return the state of the agent
     */
    private Map<Object, Object> calculateAgentState(Agent agent) {
        Set<ArenaCell> visibleArenaCells = visibleCells(agent);
        return toMap(
                "name", agent.getName(),
                "team_name", agent.getTeam().getName(),
                "position", toList(agent.getX(), agent.getY()),
                "direction", agent.getDirection(),
                "energy", agent.getEnergy(),
                "agents", visibleArenaCells.stream()
                        .map(ArenaCell::getAgent)
                        .filter(Objects::nonNull)
                        .map(visibleAgent -> toMap(
                                "name", visibleAgent.getName(),
                                "team_name", visibleAgent.getTeam().getName(),
                                "position", toList(visibleAgent.getX(), visibleAgent.getY()),
                                "direction", visibleAgent.getDirection(),
                                "energy", visibleAgent.getEnergy()))
                        .collect(Collectors.toList()),
                "drinks", visibleArenaCells.stream()
                        .filter(ArenaCell::hasEnergy)
                        .map(arenaCell -> toList(arenaCell.getX(), arenaCell.getY()))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Executes an agent script.
     *
     * @param agent the agent
     * @return the action to be taken by the agent
     */
    private AgentAction executeAgentScript(Agent agent) {
        Runtime.redirectOut(new LoggingPrintStream(agent.getLogger()));
        Future<AgentAction> agentActionFuture = executorService.submit(() -> agent.getScript().execute(
                calculateAgentState(agent), agent.getMemory(), agent.getTeam().getMemory(), agent.getStatistics()));
        try {
            return agentActionFuture.get(gameState.getParameters().getTimeQuota(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // Should not happen
            throw new RuntimeException();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();

            if (cause instanceof ScriptTimeoutException) {
                // TODO
                throw new RuntimeException();
            }

            if (cause instanceof ScriptPanicException) {
                throw (ScriptPanicException) cause;
            }

            throw new ScriptExecutionException(cause);
        } catch (TimeoutException e) {
            agentActionFuture.cancel(true);
            throw new ScriptTimeoutException(e);
        } finally {
            Runtime.restoreOut();
        }
    }

    /**
     * Calculates the set of visible cells to an agent.
     *
     * @param agent the agent
     * @return the set of visible cells
     */
    private Set<ArenaCell> visibleCells(Agent agent) {
        Set<ArenaCell> visibleArenaCells = new HashSet<>();

        int x = agent.getX();
        int y = agent.getY();
        int direction = agent.getDirection();

        Arena arena = gameState.getArena();

        int visionRange = gameState.getParameters().getVisionRange();
        for (int i = 1; i <= visionRange; i++) {
            for (int j = -i; j <= i; j++) {
                int u, v;
                switch (direction) {
                    case 0:
                        u = x + j;
                        v = y + i;
                        break;
                    case 1:
                        u = x + i;
                        v = y + j;
                        break;
                    case 2:
                        u = x + j;
                        v = y - i;
                        break;
                    case 3:
                        u = x - i;
                        v = y + j;
                        break;
                    default:
                        // Should not happen
                        throw new RuntimeException();
                }

                ArenaCell arenaCell = arena.getCellSafe(u, v);
                if (arenaCell != null) {
                    visibleArenaCells.add(arenaCell);
                }
            }
        }

        return visibleArenaCells;
    }

    /**
     * Spawns an agent at a random location.
     *
     * @param agent the agent to spawn
     */
    private void spawnAgent(Agent agent) {
        Arena arena = gameState.getArena();

        int x, y;
        ArenaCell arenaCell;
        do {
            x = RANDOM.nextInt(arena.getWidth());
            y = RANDOM.nextInt(arena.getHeight());
            arenaCell = arena.getCell(x, y);
        } while (arenaCell.getAgent() != null);

        arenaCell.setAgent(agent);
        agent.reset(x, y, 0, gameState.getParameters().getInitialEnergy());
    }

    /**
     * Spawn an energy drink at a random location.
     */
    private void spawnEnergyDrink() {
        Arena arena = gameState.getArena();

        int x, y;
        ArenaCell arenaCell;
        do {
            x = RANDOM.nextInt(arena.getWidth());
            y = RANDOM.nextInt(arena.getHeight());
            arenaCell = arena.getCell(x, y);
        } while (arenaCell.hasEnergy() || arenaCell.getAgent() != null);

        arenaCell.setHasEnergy(true);
    }

    /**
     * Applies an agent action.
     *
     * @param agent       the agent who took the action
     * @param agentAction the action to take
     */
    private void applyAgentAction(Agent agent, AgentAction agentAction) {
        Arena arena = gameState.getArena();

        switch (agentAction) {
            case STAY:
                // Do nothing
                break;
            case MOVE:
                int x = agent.getX();
                int y = agent.getY();

                int u, v;
                switch (agent.getDirection()) {
                    case 0:
                        u = x;
                        v = y + 1;
                        break;
                    case 1:
                        u = x + 1;
                        v = y;
                        break;
                    case 2:
                        u = x;
                        v = y - 1;
                        break;
                    case 3:
                        u = x - 1;
                        v = y;
                        break;
                    default:
                        // Should not happen
                        throw new RuntimeException();
                }

                ArenaCell arenaCell = arena.getCellSafe(u, v);
                if (arenaCell != null && arenaCell.getAgent() == null) {
                    arena.getCell(x, y).setAgent(null);
                    arenaCell.setAgent(agent);
                    agent.setX(u);
                    agent.setY(v);
                    if (arenaCell.hasEnergy()) {
                        agent.setEnergy(agent.getEnergy() + gameState.getParameters().getEnergyRefill());
                        arenaCell.setHasEnergy(false);
                    }
                }

                break;
            case TURN_LEFT:
                agent.setDirection((agent.getDirection() + 3) % 4);
                break;
            case TURN_RIGHT:
                agent.setDirection((agent.getDirection() + 1) % 4);
                break;
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Removes an agent from the simulation.
     *
     * @param agent the agent to remove
     */
    private void eliminateAgent(Agent agent, Agent.State agentState, String logMessage) {
        gameState.getArena().getCell(agent.getX(), agent.getY()).setAgent(null);
        agent.setState(agentState);
        agent.setEnergy(0);
        logger.log(Level.INFO, logMessage);
    }

    /**
     * Writes the agent statistics to the disk.
     */
    private void writeAgentStatistics() {
        gameState.getTeams().forEach(team -> {
            team.getAgents().forEach(agent -> {
                Path agentStatisticsPath = statisticsDirectory.resolve(agent.getName() + ".txt");
                try {
                    Files.createDirectories(agentStatisticsPath.getParent());
                    try (BufferedWriter writer = Files.newBufferedWriter(agentStatisticsPath, Charset.forName("UTF-8"))) {
                        writer.write(agent.getStatistics().toString());
                    }
                } catch (IOException e) {
                    logger.log(Level.INFO, String.format("failed to write agent statistics for agent: '%s', at: '%s'",
                            agent.getName(), agentStatisticsPath.toAbsolutePath().toString()));
                }
            });
        });
    }

    @Override
    public void shutdown() {
        logger.log(Level.INFO, "simulation shut down");
        shutdown = true;
        executorService.shutdownNow();
        writeAgentStatistics();
    }

    @Override
    public void reset() {
        logger.log(Level.INFO, "simulation restarted");
        gameState.getArena().reset();
        gameState.getTeams().forEach(team -> {
            team.reset();
            team.getAgents().forEach(this::spawnAgent);
        });
        gameState.setFinished(false);
        gameState.setRound(0);
    }

    @Override
    public void step() {
        if (gameState.isFinished() || shutdown) {
            return;
        }

        GameParameters gameParameters = gameState.getParameters();
        List<Team> teams = gameState.getTeams();

        // Exit conditions
        List<Team> remainingTeams = teams.stream().filter(team -> !team.isEliminated()).collect(Collectors.toList());
        int remainingTeamCount = remainingTeams.size();
        if (remainingTeamCount < 2) {
            if (remainingTeamCount == 0) {
                // TODO
            } else {
                // TODO
            }

            logger.log(Level.INFO, "simulation finished");
            gameState.setFinished(true);
            return;
        }

        // Next round
        gameState.setRound(gameState.getRound() + 1);

        // Possibly spawn energy
        if (gameState.getRound() % gameParameters.getEnergyFrequency() == 0) {
            spawnEnergyDrink();
        }

        // Iterate each team
        teams.forEach(team -> {
            // Iterate each agent
            team.getAgents().forEach(agent -> {
                // Skip if eliminated
                if (agent.isEliminated()) {
                    return;
                }

                String agentName = agent.getName();

                // Check if agent has any energy left
                agent.setEnergy(agent.getEnergy() - gameParameters.getEnergyLoss());
                if (agent.getEnergy() <= 0) {
                    eliminateAgent(agent, Agent.State.NO_ENERGY, String.format("agent has no energy left: '%s'", agentName));
                    return;
                }

                // Compute and apply the agent action
                AgentAction agentAction;
                try {
                    agentAction = executeAgentScript(agent);
                } catch (ScriptPanicException e) {
                    eliminateAgent(
                            agent, Agent.State.PANICKED, String.format("agent '%s' panicked with message: %s", agentName, e.getMessage()));
                    return;
                } catch (ScriptTimeoutException e) {
                    eliminateAgent(
                            agent, Agent.State.TIMED_OUT, String.format("agent '%s' timed out", agentName));
                    return;
                } catch (ScriptExecutionException e) {
                    eliminateAgent(
                            agent, Agent.State.CRASHED, String.format("agent '%s' crashed with exception: %s", agentName, e.getMessage()));
                    return;
                }

                applyAgentAction(agent, agentAction);
            });
        });
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }
}
