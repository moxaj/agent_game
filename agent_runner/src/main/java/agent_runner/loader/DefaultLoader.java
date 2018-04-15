package agent_runner.loader;

import agent_game.game.*;
import agent_game.simulator.DefaultSimulator;
import agent_game.simulator.Simulator;
import agent_runner.loader.settings.*;
import agent_script.ResourceUtils;
import agent_script.compiler.Compiler;
import agent_script.compiler.CompilerException;
import agent_script.compiler.DefaultCompiler;
import agent_script.compiler.analyzer.Symbol;
import agent_script.runtime.ScriptExecutionException;
import agent_script.runtime.ScriptPanicException;
import agent_script.runtime.ScriptTimeoutException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The default {@link Loader} implementation.
 */
public final class DefaultLoader implements Loader {
    /**
     * @param settingsPath the path to the settings file
     * @return the settings
     */
    private Settings readSettings(Path settingsPath) {
        YamlReader reader = null;
        try {
            reader = new YamlReader(new FileReader(settingsPath.toFile()));
            return reader.read(Settings.class);
        } catch (IOException e) {
            throw new LoaderException(String.format("could not read the settings from '%s'", settingsPath), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
    }

    /**
     * @param loggingSettings the logging settings
     * @return a path to a fresh logging directory
     */
    private Path makeLoggingDirectory(LoggingSettings loggingSettings) {
        if (loggingSettings == null) {
            throw new LoaderException("missing 'loggingSettings'");
        }

        String loggingDirectoryStr = loggingSettings.getOutputPath();
        if (loggingDirectoryStr == null) {
            throw new LoaderException("missing 'loggingSettings.outputPath'");
        }

        Path loggingDirectory;
        try {
            loggingDirectory = Paths.get(loggingDirectoryStr);
        } catch (InvalidPathException e) {
            throw new LoaderException(
                    String.format("invalid 'loggingSettings.outputPath': '%s'", loggingDirectoryStr), e);
        }

        try {
            Files.createDirectories(loggingDirectory);
        } catch (IOException e) {
            throw new LoaderException(
                    String.format("could not create logging directory at: '%s'", loggingDirectory.toAbsolutePath().toString()), e);
        }

        return loggingDirectory;
    }

    /**
     * @param statisticsSettings the statistics settings
     * @return a path to a fresh statistics directory
     */
    private Path makeStatisticsDirectory(StatisticsSettings statisticsSettings) {
        if (statisticsSettings == null) {
            throw new LoaderException("missing 'statisticsSettings'");
        }

        String statisticsDirectoryStr = statisticsSettings.getOutputPath();
        if (statisticsDirectoryStr == null) {
            throw new LoaderException("missing 'statisticsSettings.outputPath'");
        }

        Path statisticsDirectory;
        try {
            statisticsDirectory = Paths.get(statisticsDirectoryStr);
        } catch (InvalidPathException e) {
            throw new LoaderException(
                    String.format("invalid 'statisticsSettings.outputPath': '%s'", statisticsDirectoryStr), e);
        }

        try {
            Files.createDirectories(statisticsDirectory);
        } catch (IOException e) {
            throw new LoaderException(
                    String.format("could not create statistics directory at: '%s'", statisticsDirectory.toAbsolutePath().toString()), e);
        }

        return statisticsDirectory;
    }

    /**
     * @param loggingDirectory the directory of the logger
     * @param loggerType       the type of the logger
     * @return a fresh {@link Logger} instance
     */
    private Logger makeLogger(Path loggingDirectory, String loggerType) {
        return makeLogger(loggingDirectory, loggerType, loggerType);
    }

    /**
     * @param loggingDirectory the directory of the logger
     * @param loggerType       the type of the logger
     * @param loggerName       the name of the logger
     * @return a fresh {@link Logger} instance
     */
    private Logger makeLogger(Path loggingDirectory, String loggerType, String loggerName) {
        Logger logger = Logger.getLogger(loggerName + "Logger");
        logger.setUseParentHandlers(false);

        Path logPath = loggingDirectory.resolve(loggerName + ".log");

        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(logPath.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new LoaderException(
                    String.format("could not create %s log file at: '%s'", loggerType, logPath.toAbsolutePath().toString()), e);
        }

        fileHandler.setFormatter(FORMATTER);
        logger.addHandler(fileHandler);

        Runtime.getRuntime().addShutdownHook(new Thread(fileHandler::close));
        return logger;
    }

    /**
     * @param teamSettingsArray the team settings
     * @param loggingDirectory  the logging directory
     * @param compiler          the constructed compiler
     * @param settingsPath      the path to the settings file
     * @return the teams
     */
    private List<Team> makeTeams(TeamSettings[] teamSettingsArray, Path loggingDirectory, Compiler compiler, Path settingsPath) {
        if (teamSettingsArray == null) {
            throw new LoaderException("missing 'simulatorSettings.teams'");
        }

        int[] agentIndex = {0};
        return Arrays.stream(teamSettingsArray).map(teamSettings -> {
            String teamName = teamSettings.getName();
            if (teamName == null) {
                throw new LoaderException("missing team name");
            }

            Team team = new Team(teamName, new HashMap<>());
            AgentSettings[] agentsSettingsArray = teamSettings.getAgents();
            if (agentsSettingsArray == null) {
                throw new LoaderException(String.format("missing 'agents' in team: '%s'", teamName));
            }

            team.setAgents(Arrays.stream(agentsSettingsArray).map(agentSettings -> {
                String agentName = agentSettings.getName();
                if (agentName == null) {
                    throw new LoaderException(String.format("missing agent name in team: '%s'", teamName));
                }

                String agentRootSourcePathStr = agentSettings.getRootSourcePath();
                if (agentRootSourcePathStr == null) {
                    throw new LoaderException(String.format("missing 'rootSourcePath' in agent: '%s'", agentName));
                }

                Map<Symbol, Class<?>> agentClasses;
                try {
                    agentClasses = ResourceUtils.withResourcePath(Simulator.class, "/agent_game/game/ext", extRootSourcePath -> {
                        try {
                            return compiler.compile(new Path[]{
                                    extRootSourcePath,
                                    settingsPath.getParent().resolve(agentRootSourcePathStr)
                            });
                        } catch (CompilerException e) {
                            throw new LoaderException(
                                    String.format("compiler exception when compiling sources for agent: '%s'", agentName), e);
                        }
                    });
                } catch (URISyntaxException e) {
                    throw new LoaderException(String.format("invalid 'rootSourcePath' in agent: '%s'", agentName));
                }

                String mainFunctionNameStr = agentSettings.getMainFunctionName();
                if (mainFunctionNameStr == null) {
                    throw new LoaderException(String.format("missing 'mainFunctionName' in agent: '%s'", agentName));
                }

                Symbol mainFunctionName = Symbol.parseQualifiedSymbolUnsafe(mainFunctionNameStr);
                if (mainFunctionName == null) {
                    throw new LoaderException(String.format("invalid 'mainFunctionName' in agent: '%s'", agentName));
                }

                Symbol mainNamespaceName = mainFunctionName.getNamespaceSymbol();
                Class<?> agentClass = agentClasses.get(mainNamespaceName);
                if (agentClass == null) {
                    throw new LoaderException(
                            String.format("no such main namespace: '%s' in agent: '%s'", mainNamespaceName, agentName));
                }

                String methodName = mainFunctionName.getNameSymbol().toString();
                Method mainMethod = Arrays.stream(agentClass.getMethods())
                        .filter(method -> method.getName().equals(Compiler.munge(methodName)))
                        .findFirst()
                        .orElseThrow(() -> new LoaderException(
                                String.format("no such main function: '%s' in agent: '%s'", mainFunctionName, agentName)));

                int mainMethodExpectedArity = Arrays.stream(AgentScript.class.getMethods())
                        .filter(method -> method.getName().equals("execute"))
                        .map(method -> method.getParameterTypes().length)
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
                int mainMethodActualArity = mainMethod.getParameterTypes().length;
                if (mainMethodActualArity != mainMethodExpectedArity) {
                    throw new LoaderException(
                            String.format("invalid main method arity: '%d' in agent: '%s'", mainMethodActualArity, agentName));
                }

                AgentScript agentScript = (state, memory, teamMemory, statistics) -> {
                    try {
                        return (AgentAction) mainMethod.invoke(null, state, memory, teamMemory, statistics);
                    } catch (IllegalAccessException e) {
                        // Should not happen
                        throw new LoaderException();
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        if (cause instanceof ScriptPanicException) {
                            throw (ScriptPanicException) cause;
                        }

                        if (cause instanceof ScriptTimeoutException) {
                            throw (ScriptTimeoutException) cause;
                        }

                        throw new ScriptExecutionException(cause);
                    }
                };

                return new Agent(agentName, agentIndex[0]++, team, agentScript, makeLogger(loggingDirectory, "agent", agentName));
            }).collect(Collectors.toList()));

            return team;
        }).collect(Collectors.toList());
    }

    /**
     * @param arenaSettings the arena settings
     * @return the {@link Arena} created from the settings
     */
    private Arena makeArena(ArenaSettings arenaSettings) {
        if (arenaSettings == null) {
            throw new LoaderException("missing 'simulatorSettings.arenaSettings'");
        }

        Integer width = arenaSettings.getWidth();
        if (width == null) {
            throw new LoaderException("missing 'simulatorSettings.arenaSettings.width'");
        }

        Integer height = arenaSettings.getHeight();
        if (height == null) {
            throw new LoaderException("missing 'simulatorSettings.arenaSettings.height'");
        }

        return new Arena(width, height);
    }

    /**
     * @param gameSettings the game settings
     * @return the {@link GameParameters} created from the settings
     */
    private GameParameters makeGameParameters(GameSettings gameSettings) {
        Integer timeQuota = gameSettings.getTimeQuota();
        if (timeQuota == null) {
            throw new LoaderException("missing 'simulatorSettings.gameSettings.timeQouta'");
        }

        Integer initialEnergy = gameSettings.getInitialEnergy();
        if (initialEnergy == null) {
            throw new LoaderException("missing 'simulatorSettings.gameSettings.initialEnergy'");
        }

        Integer energyLoss = gameSettings.getEnergyLoss();
        if (energyLoss == null) {
            throw new LoaderException("missing 'simulatorSettings.gameSettings.energyLoss'");
        }

        Integer energyRefill = gameSettings.getEnergyRefill();
        if (energyRefill == null) {
            throw new LoaderException("missing 'simulatorSettings.gameSettings.energyRefill'");
        }

        Integer energyFrequency = gameSettings.getEnergyFrequency();
        if (energyFrequency == null) {
            throw new LoaderException("missing 'simulatorSettings.gameSettings.energyFrequency'");
        }

        Integer visionRange = gameSettings.getVisionRange();
        if (visionRange == null) {
            throw new LoaderException("missing 'simulatorSettings.gameSettings.visionRange'");
        }

        return new GameParameters(timeQuota, initialEnergy, energyLoss, energyRefill, energyFrequency, visionRange);
    }

    @Override
    public Simulator load(Path settingsPath) {
        // Read settings
        Settings settings = readSettings(settingsPath);

        // Process logging settings
        Path loggingDirectory = makeLoggingDirectory(settings.getLoggingSettings());

        // Make a compiler
        Compiler compiler = new DefaultCompiler(makeLogger(loggingDirectory, "compiler"), Paths.get("as_cache"));

        // Process simulator settings
        SimulatorSettings simulatorSettings = settings.getSimulatorSettings();
        if (simulatorSettings == null) {
            throw new LoaderException("missing 'simulatorSettings'");
        }

        // Create simulator
        return new DefaultSimulator(
                new GameState(
                        makeTeams(simulatorSettings.getTeams(), loggingDirectory, compiler, settingsPath),
                        makeArena(simulatorSettings.getArenaSettings()),
                        makeGameParameters(simulatorSettings.getGameSettings())),
                makeLogger(loggingDirectory, "simulator"),
                makeStatisticsDirectory(simulatorSettings.getStatisticsSettings()));
    }
}
