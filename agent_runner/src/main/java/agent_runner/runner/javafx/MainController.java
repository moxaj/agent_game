package agent_runner.runner.javafx;

import agent_game.game.Agent;
import agent_game.game.GameParameters;
import agent_runner.loader.DefaultLoader;
import agent_runner.visualizer.DefaultVisualizer;
import agent_runner.visualizer.Visualizer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.function.Function;

/**
 * The main JavaFX controller, responsible for providing the simulator visuals.
 */
public class MainController {
    private final Visualizer visualizer = new DefaultVisualizer();

    private ObservableSimulation simulation;

    // Menu

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem stopRestartMenuItem;

    @FXML
    private MenuItem pauseResumeMenuItem;

    @FXML
    private MenuItem stepMenuItem;

    @FXML
    private MenuItem decreaseSpeedMenuItem;

    @FXML
    private MenuItem increaseSpeedMenuItem;

    // Toolbar

    @FXML
    private Label openLabel;

    @FXML
    private Button openButton;

    @FXML
    private Label stopRestartLabel;

    @FXML
    private Button stopRestartButton;

    @FXML
    private Label pauseResumeLabel;

    @FXML
    private Button pauseResumeButton;

    @FXML
    private Label stepLabel;

    @FXML
    private Button stepButton;

    @FXML
    private Label decreaseSpeedLabel;

    @FXML
    private Button decreaseSpeedButton;

    @FXML
    private Label increaseSpeedLabel;

    @FXML
    private Button increaseSpeedButton;

    @FXML
    private Label speedLabel;

    // Settings

    @FXML
    private TextField timeQuotaTextField;

    @FXML
    private TextField initialEnergyTextField;

    @FXML
    private TextField energyLossTextField;

    @FXML
    private TextField energyRefillTextField;

    @FXML
    private TextField energyFrequencyTextField;

    @FXML
    private TextField visionRangeTextField;

    // Game

    @FXML
    private TextField roundTextField;

    @FXML
    private TextField finishedTextField;

    // Agents

    @FXML
    private TableView<ObservableAgent> agentsTableView;

    // Inspections

    @FXML
    private TextArea memoryTextArea;

    @FXML
    private TextArea teamMemoryTextArea;

    @FXML
    private TextArea statisticsTextArea;

    @FXML
    private Canvas gameCanvas;

    private void setupMenu() {
        openMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    ObservableSimulation.State simulationState = simulation.getState();
                    return !(simulationState == ObservableSimulation.State.NOT_LOADED
                            || simulationState == ObservableSimulation.State.STOPPED);
                },
                simulation.stateProperty()));
        openMenuItem.setOnAction(event -> openSettings());

        exitMenuItem.setOnAction(event -> exit());

        stopRestartMenuItem.textProperty().bind(Bindings.createStringBinding(
                () -> simulation.getState() == ObservableSimulation.State.STOPPED
                        ? "Restart simulation"
                        : "Stop simulation",
                simulation.stateProperty()));
        stopRestartMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    ObservableSimulation.State simulationState = simulation.getState();
                    return !(simulationState == ObservableSimulation.State.STOPPED
                            || simulationState == ObservableSimulation.State.PAUSED);
                },
                simulation.stateProperty()));
        stopRestartMenuItem.setOnAction(event -> {
            if (simulation.getState() == ObservableSimulation.State.STOPPED) {
                simulation.restart();
            } else {
                simulation.stop();
            }
        });

        pauseResumeMenuItem.textProperty().bind(Bindings.createStringBinding(
                () -> simulation.getState() == ObservableSimulation.State.PAUSED
                        ? "Resume simulation"
                        : "Pause simulation",
                simulation.stateProperty()));
        pauseResumeMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    ObservableSimulation.State simulationState = simulation.getState();
                    return !(simulationState == ObservableSimulation.State.PAUSED
                            || simulationState == ObservableSimulation.State.RUNNING);
                },
                simulation.stateProperty()));
        pauseResumeMenuItem.setOnAction(event -> {
            if (simulation.getState() == ObservableSimulation.State.PAUSED) {
                simulation.resume();
            } else {
                simulation.pause();
            }
        });

        stepMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> simulation.getState() != ObservableSimulation.State.PAUSED,
                simulation.stateProperty()));
        stepMenuItem.setOnAction(event -> simulation.step());

        decreaseSpeedMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> simulation.getState() == ObservableSimulation.State.NOT_LOADED || !simulation.canDecreaseSpeed(),
                simulation.stateProperty(),
                simulation.speedProperty()));
        decreaseSpeedMenuItem.setOnAction(event -> simulation.decreaseSpeed());

        increaseSpeedMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> simulation.getState() == ObservableSimulation.State.NOT_LOADED || !simulation.canIncreaseSpeed(),
                simulation.stateProperty(),
                simulation.speedProperty()));
        increaseSpeedMenuItem.setOnAction(event -> simulation.increaseSpeed());
    }

    private void setupToolbar() {
        openLabel.getTooltip().setText(openMenuItem.getText());
        openButton.setTooltip(openLabel.getTooltip());
        openButton.setGraphic(new FontIcon("fas-folder-open"));
        openButton.disableProperty().bind(openMenuItem.disableProperty());
        openButton.setOnAction(openMenuItem.getOnAction());

        stopRestartLabel.getTooltip().textProperty().bind(stopRestartMenuItem.textProperty());
        stopRestartButton.setTooltip(stopRestartLabel.getTooltip());
        stopRestartButton.graphicProperty().bind(Bindings.createObjectBinding(
                () -> simulation.getState() == ObservableSimulation.State.STOPPED
                        ? new FontIcon("fas-redo")
                        : new FontIcon("fas-stop"),
                simulation.stateProperty()));
        stopRestartButton.disableProperty().bind(stopRestartMenuItem.disableProperty());
        stopRestartButton.setOnAction(stopRestartMenuItem.getOnAction());

        pauseResumeLabel.getTooltip().textProperty().bind(pauseResumeMenuItem.textProperty());
        pauseResumeButton.setTooltip(pauseResumeLabel.getTooltip());
        pauseResumeButton.graphicProperty().bind(Bindings.createObjectBinding(
                () -> simulation.getState() == ObservableSimulation.State.PAUSED
                        ? new FontIcon("fas-play")
                        : new FontIcon("fas-pause"),
                simulation.stateProperty()));
        pauseResumeButton.disableProperty().bind(pauseResumeMenuItem.disableProperty());
        pauseResumeButton.setOnAction(pauseResumeMenuItem.getOnAction());

        stepLabel.getTooltip().setText(stepMenuItem.getText());
        stepButton.setTooltip(stepLabel.getTooltip());
        stepButton.setGraphic(new FontIcon("fas-step-forward"));
        stepButton.disableProperty().bind(stepMenuItem.disableProperty());
        stepButton.setOnAction(stepMenuItem.getOnAction());

        decreaseSpeedLabel.getTooltip().setText(decreaseSpeedMenuItem.getText());
        decreaseSpeedButton.setTooltip(decreaseSpeedLabel.getTooltip());
        decreaseSpeedButton.setGraphic(new FontIcon("fas-backward"));
        decreaseSpeedButton.disableProperty().bind(decreaseSpeedMenuItem.disableProperty());
        decreaseSpeedButton.setOnAction(decreaseSpeedMenuItem.getOnAction());

        increaseSpeedLabel.getTooltip().setText(increaseSpeedMenuItem.getText());
        increaseSpeedButton.setTooltip(increaseSpeedLabel.getTooltip());
        increaseSpeedButton.setGraphic(new FontIcon("fas-forward"));
        increaseSpeedButton.disableProperty().bind(increaseSpeedMenuItem.disableProperty());
        increaseSpeedButton.setOnAction(increaseSpeedMenuItem.getOnAction());

        speedLabel.textProperty().bind(Bindings.createIntegerBinding(() ->
                (int) Math.pow(2, simulation.getSpeed()), simulation.speedProperty()).asString("%2dx"));
    }

    private void populateGameParametersTextFields() {
        GameParameters gameParameters = simulation.getGameState().getParameters();
        timeQuotaTextField.setText(Integer.toString(gameParameters.getTimeQuota()));
        initialEnergyTextField.setText(Integer.toString(gameParameters.getInitialEnergy()));
        energyLossTextField.setText(Integer.toString(gameParameters.getEnergyLoss()));
        energyRefillTextField.setText(Integer.toString(gameParameters.getEnergyRefill()));
        energyFrequencyTextField.setText(Integer.toString(gameParameters.getEnergyFrequency()));
        visionRangeTextField.setText(Integer.toString(gameParameters.getVisionRange()));
    }

    private void populateGameLabels() {
        roundTextField.textProperty().bind(simulation.getObservableGameState().roundProperty().asString());
        finishedTextField.textProperty().bind(simulation.getObservableGameState().finishedProperty().asString());
    }

    private void setupAgentsTableView() {
        ObservableList<TableColumn<ObservableAgent, ?>> agentsTableViewColumns = agentsTableView.getColumns();

        TableColumn<ObservableAgent, Number> indexColumn = new TableColumn<>("Index");
        indexColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIndex()));
        agentsTableViewColumns.add(indexColumn);

        TableColumn<ObservableAgent, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        agentsTableViewColumns.add(nameColumn);

        TableColumn<ObservableAgent, String> teamNameColumn = new TableColumn<>("Team name");
        teamNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeamName()));
        agentsTableViewColumns.add(teamNameColumn);

        TableColumn<ObservableAgent, String> stateColumn = new TableColumn<>("State");
        stateColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(
                () -> {
                    switch (cellData.getValue().getState()) {
                        case ALIVE:
                            return "alive";
                        case NO_ENERGY:
                            return "no energy";
                        case PANICKED:
                            return "panicked";
                        case TIMED_OUT:
                            return "timed out";
                        case CRASHED:
                            return "crashed";
                        default:
                            // Should not happen
                            throw new RuntimeException();
                    }
                },
                cellData.getValue().stateProperty()));
        agentsTableViewColumns.add(stateColumn);

        TableColumn<ObservableAgent, Number> energyColumn = new TableColumn<>("Energy");
        energyColumn.setCellValueFactory(cellData -> cellData.getValue().energyProperty());
        agentsTableViewColumns.add(energyColumn);
    }

    private void populateAgentsTableView() {
        ObservableList<ObservableAgent> agentsTableViewItems = agentsTableView.getItems();
        agentsTableViewItems.clear();

        simulation.getGameState().getAgents().stream()
                .sorted(Comparator.comparing(Agent::getIndex))
                .forEach(agent -> agentsTableViewItems.add(new ObservableAgent(agent, simulation.roundProperty())));
    }

    private StringBinding makeInspectionBinding(Function<ObservableAgent, String> agentStringFunction) {
        return Bindings.createStringBinding(
                () -> {
                    switch (simulation.getState()) {
                        case NOT_LOADED:
                            return "";
                        case STOPPED:
                        case PAUSED:
                            ObservableAgent observableAgent = agentsTableView.getSelectionModel().getSelectedItem();
                            return observableAgent == null
                                    ? "<select agent to show inspection>"
                                    : agentStringFunction.apply(observableAgent);
                        case RUNNING:
                            return "<pause simulation to show inspection>";
                        default:
                            // Should not happen
                            throw new RuntimeException();
                    }
                },
                simulation.stateProperty(), simulation.roundProperty(), agentsTableView.getSelectionModel().selectedItemProperty());
    }

    private void setupInspections() {
        memoryTextArea.textProperty().bind(makeInspectionBinding(observableAgent -> observableAgent.getMemory().toString()));
        teamMemoryTextArea.textProperty().bind(makeInspectionBinding(observableAgent -> observableAgent.getTeamMemory().toString()));
        statisticsTextArea.textProperty().bind(makeInspectionBinding(observableAgent -> observableAgent.getStatistics().toString()));
    }

    private void setupVisualizer() {
        visualizer.setup(simulation.getGameState(), gameCanvas);
        visualizer.repaint();
    }

    private void openSettings() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose simulation settings");
        fileChooser.setInitialDirectory(Paths.get(".").toFile());

        File chosenFile = fileChooser.showOpenDialog(openLabel.getScene().getWindow());
        if (chosenFile == null) {
            return;
        }

        simulation.setSimulator(new DefaultLoader().load(chosenFile.toPath()));
        simulation.restart();

        populateGameParametersTextFields();
        populateGameLabels();
        populateAgentsTableView();
        setupVisualizer();
    }

    private void exit() {
        Stage stage = (Stage) openLabel.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    // Public API

    public void initialize() {
        simulation = new ObservableSimulation(() -> Platform.runLater(visualizer::repaint));
        setupMenu();
        setupToolbar();
        setupAgentsTableView();
        setupInspections();
    }

    public void onStageClosed() {
        simulation.shutdown();
    }
}
