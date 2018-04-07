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
 *
 */
public final class DefaultLoader implements Loader {
    /**
     * Loads the settings.
     *
     * @param settingsPath
     * @return the loaded settings
     */
    private Settings loadSettings(Path settingsPath) {
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

    private Logger makeLogger(Path loggingDirectory, String loggerType) {
        return makeLogger(loggingDirectory, loggerType, loggerType);
    }

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

    private Symbol parseMainFunctionName(String mainFunctionNameStr, String agentName) {
        String[] symbolParts = mainFunctionNameStr.split("::");
        if (symbolParts.length != 2 || symbolParts[1].split("\\.").length > 1) {
            throw new LoaderException(String.format("invalid 'mainFunctionName': '%s' in agent: '%s'", mainFunctionNameStr,
                    agentName));
        }

        return Symbol.create(symbolParts[0].split("\\."), symbolParts[1]);
    }

    private List<Agent> makeAgents(TeamSettings[] teams, Path loggingDirectory, Compiler compiler, Path settingsPath) {
        if (teams == null) {
            throw new LoaderException("missing 'simulatorSettings.teams'");
        }

        int[] agentIndex = {0};
        return Arrays.stream(teams).flatMap(teamSettings -> {
            String teamName = teamSettings.getName();
            if (teamName == null) {
                throw new LoaderException("missing team name");
            }

            Map<Object, Object> teamMemory = new HashMap<>();
            return Arrays.stream(teamSettings.getAgents()).map(agentSettings -> {
                String agentName = agentSettings.getName();
                if (agentName == null) {
                    throw new LoaderException(String.format("missing agent name in team: '%s'", teamName));
                }

                Logger agentLogger = makeLogger(loggingDirectory, "agent", agentName);

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

                Symbol mainFunctionName = parseMainFunctionName(mainFunctionNameStr, agentName);
                Symbol mainNamespaceName = mainFunctionName.getNamespaceSymbol();
                Class<?> agentClass = agentClasses.get(mainNamespaceName);
                if (agentClass == null) {
                    throw new LoaderException(
                            String.format("no such main namespace: '%s' in agent: '%s'", mainNamespaceName, agentName));
                }

                String methodName = mainFunctionName.getNameSymbol().toString();
                Method mainMethod = Arrays.stream(agentClass.getMethods())
                        .filter(method -> method.getName().equals(DefaultCompiler.munge(methodName)))
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

                AgentScript agentScript = (state, memory, teamMemory2, statistics) -> {
                    try {
                        return (AgentAction) mainMethod.invoke(null, state, memory, teamMemory2, statistics);
                    } catch (IllegalAccessException e) {
                        throw new LoaderException(); // Should not happen
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

                return new Agent(agentIndex[0]++, agentName, teamName, agentLogger, agentScript, teamMemory);
            });
        })
                .collect(Collectors.toList());
    }

    private Arena makeArena(ArenaSettings arenaSettings) {
        if (arenaSettings == null) {
            throw new LoaderException("missing 'simulatorSettings.arenaSettings'");
        }

        int width = arenaSettings.getWidth();
        if (width == -1) {
            throw new LoaderException("missing 'arenaSettings.width'");
        }

        int height = arenaSettings.getHeight();
        if (height == -1) {
            throw new LoaderException("missing 'arenaSettings.height'");
        }

        return new Arena(width, height);
    }

    private GameParameters makeGameParameters(GameSettings gameSettings) {
        // TODO check for nulls
        GameParameters gameParameters = new GameParameters();
        gameParameters.setTimeQuota(gameSettings.getTimeQuota());
        gameParameters.setInitialEnergy(gameSettings.getInitialEnergy());
        gameParameters.setEnergyLoss(gameSettings.getEnergyLoss());
        gameParameters.setEnergyRefill(gameSettings.getEnergyRefill());
        gameParameters.setEnergyFrequency(gameSettings.getEnergyFrequency());
        gameParameters.setVisionRange(gameSettings.getVisionRange());
        return gameParameters;
    }

    private DefaultSimulator makeSimulator(Path settingsPath) {
        // Read settings
        Settings settings = loadSettings(settingsPath);

        // Process logging settings
        Path loggingDirectory = makeLoggingDirectory(settings.getLoggingSettings());

        // Make a compiler
        Compiler compiler = new DefaultCompiler(makeLogger(loggingDirectory, "compiler"), Paths.get("as_cache"));

        // Process simulator settings
        SimulatorSettings simulatorSettings = settings.getSimulatorSettings();
        if (simulatorSettings == null) {
            throw new LoaderException("missing 'simulatorSettings'");
        }

        List<Agent> agents = makeAgents(simulatorSettings.getTeams(), loggingDirectory, compiler, settingsPath);
        Arena arena = makeArena(simulatorSettings.getArenaSettings());

        // Process statistics settings
        Path statisticsDirectory = makeStatisticsDirectory(simulatorSettings.getStatisticsSettings());

        // Create simulator and invoke it
        Logger simulatorLogger = makeLogger(loggingDirectory, "simulator");
        return new DefaultSimulator(
                simulatorLogger, statisticsDirectory, agents, arena, makeGameParameters(simulatorSettings.getGameSettings()));
    }

    @Override
    public DefaultSimulator load(Path settingsPath) {
        return makeSimulator(settingsPath);
    }
}
