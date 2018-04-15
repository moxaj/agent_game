package agent_runner.runner.javafx;

import agent_game.game.Agent;
import agent_game.game.GameParameters;
import agent_game.game.Team;
import agent_runner.loader.DefaultLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The main JavaFX controller, responsible for providing the simulator visuals and controls.
 */
public class MainController {
    /**
     * The controlled game simulation.
     */
    private Simulation simulation;

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

    // Agents

    @FXML
    private TableView<Agent> agentsTableView;

    // Inspections

    @FXML
    private TextArea memoryTextArea;

    @FXML
    private TextArea teamMemoryTextArea;

    @FXML
    private TextArea statisticsTextArea;

    // Canvas

    @FXML
    private Canvas mainCanvas;

    private MainCanvasController mainCanvasController;

    // Charts

    @FXML
    private BarChart<String, Number> agentEnergyBarChart;

    @FXML
    private BarChart<String, Number> teamEnergyBarChart;

    /**
     * Initializes the menu elements.
     */
    private void setupMenu() {
        openMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    DefaultSimulation.State simulationState = simulation.getState();
                    return !(simulationState == DefaultSimulation.State.NOT_LOADED
                            || simulationState == DefaultSimulation.State.STOPPED);
                },
                simulation.stateProperty()));
        openMenuItem.setOnAction(event -> openSettings());

        exitMenuItem.setOnAction(event -> exit());

        stopRestartMenuItem.textProperty().bind(Bindings.createStringBinding(
                () -> simulation.getState() == DefaultSimulation.State.STOPPED
                        ? "Restart simulation"
                        : "Stop simulation",
                simulation.stateProperty()));
        stopRestartMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    DefaultSimulation.State simulationState = simulation.getState();
                    return !(simulationState == DefaultSimulation.State.STOPPED
                            || simulationState == DefaultSimulation.State.PAUSED);
                },
                simulation.stateProperty()));
        stopRestartMenuItem.setOnAction(event -> {
            if (simulation.getState() == DefaultSimulation.State.STOPPED) {
                simulation.restart();
            } else {
                simulation.stop();
            }
        });

        pauseResumeMenuItem.textProperty().bind(Bindings.createStringBinding(
                () -> simulation.getState() == DefaultSimulation.State.PAUSED
                        ? "Resume simulation"
                        : "Pause simulation",
                simulation.stateProperty()));
        pauseResumeMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    DefaultSimulation.State simulationState = simulation.getState();
                    return !(simulationState == DefaultSimulation.State.PAUSED
                            || simulationState == DefaultSimulation.State.RUNNING);
                },
                simulation.stateProperty()));
        pauseResumeMenuItem.setOnAction(event -> {
            if (simulation.getState() == DefaultSimulation.State.PAUSED) {
                simulation.resume();
            } else {
                simulation.pause();
            }
        });

        stepMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> simulation.getState() != DefaultSimulation.State.PAUSED,
                simulation.stateProperty()));
        stepMenuItem.setOnAction(event -> simulation.step());

        decreaseSpeedMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> simulation.getState() == DefaultSimulation.State.NOT_LOADED || !simulation.canDecreaseSpeed(),
                simulation.stateProperty(),
                simulation.speedProperty()));
        decreaseSpeedMenuItem.setOnAction(event -> simulation.decreaseSpeed());

        increaseSpeedMenuItem.disableProperty().bind(Bindings.createBooleanBinding(
                () -> simulation.getState() == DefaultSimulation.State.NOT_LOADED || !simulation.canIncreaseSpeed(),
                simulation.stateProperty(),
                simulation.speedProperty()));
        increaseSpeedMenuItem.setOnAction(event -> simulation.increaseSpeed());
    }

    /**
     * Initializes the toolbar elements.
     */
    private void setupToolbar() {
        openLabel.getTooltip().setText(openMenuItem.getText());
        openButton.setTooltip(openLabel.getTooltip());
        openButton.setGraphic(new FontIcon("fas-folder-open"));
        openButton.disableProperty().bind(openMenuItem.disableProperty());
        openButton.setOnAction(openMenuItem.getOnAction());

        stopRestartLabel.getTooltip().textProperty().bind(stopRestartMenuItem.textProperty());
        stopRestartButton.setTooltip(stopRestartLabel.getTooltip());
        stopRestartButton.graphicProperty().bind(Bindings.createObjectBinding(
                () -> simulation.getState() == DefaultSimulation.State.STOPPED
                        ? new FontIcon("fas-redo")
                        : new FontIcon("fas-stop"),
                simulation.stateProperty()));
        stopRestartButton.disableProperty().bind(stopRestartMenuItem.disableProperty());
        stopRestartButton.setOnAction(stopRestartMenuItem.getOnAction());

        pauseResumeLabel.getTooltip().textProperty().bind(pauseResumeMenuItem.textProperty());
        pauseResumeButton.setTooltip(pauseResumeLabel.getTooltip());
        pauseResumeButton.graphicProperty().bind(Bindings.createObjectBinding(
                () -> simulation.getState() == DefaultSimulation.State.PAUSED
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

        speedLabel.textProperty().bind(Bindings.format("%2dx", simulation.speedProperty()));
    }

    /**
     * Initializes the game parameter text fields.
     */
    private void setupGameParameterTextFields() {
        ObservableValue<Boolean> disable = Bindings.createBooleanBinding(
                () -> simulation.getState() == DefaultSimulation.State.NOT_LOADED,
                simulation.stateProperty());
        timeQuotaTextField.disableProperty().bind(disable);
        initialEnergyTextField.disableProperty().bind(disable);
        energyLossTextField.disableProperty().bind(disable);
        energyRefillTextField.disableProperty().bind(disable);
        energyFrequencyTextField.disableProperty().bind(disable);
        visionRangeTextField.disableProperty().bind(disable);
    }

    /**
     * Populates the game parameter text fields.
     */
    private void populateGameParameterTextFields() {
        GameParameters gameParameters = simulation.getGameState().getParameters();
        timeQuotaTextField.setText(Integer.toString(gameParameters.getTimeQuota()));
        initialEnergyTextField.setText(Integer.toString(gameParameters.getInitialEnergy()));
        energyLossTextField.setText(Integer.toString(gameParameters.getEnergyLoss()));
        energyRefillTextField.setText(Integer.toString(gameParameters.getEnergyRefill()));
        energyFrequencyTextField.setText(Integer.toString(gameParameters.getEnergyFrequency()));
        visionRangeTextField.setText(Integer.toString(gameParameters.getVisionRange()));
    }

    /**
     * Initializes the agent table view.
     */
    private void setupAgentTableView() {
        ObservableValue<Boolean> disable = Bindings.createBooleanBinding(
                () -> simulation.getState() == DefaultSimulation.State.NOT_LOADED,
                simulation.stateProperty());
        agentsTableView.disableProperty().bind(disable);
        agentsTableView.setPlaceholder(new Label(""));

        ObservableList<TableColumn<Agent, ?>> agentsTableViewColumns = agentsTableView.getColumns();

        TableColumn<Agent, Number> indexColumn = new TableColumn<>("Index");
        indexColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIndex()));
        agentsTableViewColumns.add(indexColumn);

        TableColumn<Agent, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        agentsTableViewColumns.add(nameColumn);

        TableColumn<Agent, String> teamNameColumn = new TableColumn<>("Team name");
        teamNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeam().getName()));
        agentsTableViewColumns.add(teamNameColumn);

        TableColumn<Agent, Number> energyColumn = new TableColumn<>("Energy");
        energyColumn.setCellValueFactory(cellData ->
                Bindings.createIntegerBinding(() -> cellData.getValue().getEnergy(), simulation.getTick()));
        agentsTableViewColumns.add(energyColumn);

        TableColumn<Agent, String> stateColumn = new TableColumn<>("State");
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
                simulation.getTick()));
        agentsTableViewColumns.add(stateColumn);
    }

    /**
     * Populates the agent table view.
     */
    private void populateAgentTableView() {
        ObservableList<Agent> agentsTableViewItems = agentsTableView.getItems();
        agentsTableViewItems.clear();
        agentsTableViewItems.addAll(simulation.getGameState().getTeams()
                .stream()
                .flatMap(team -> team.getAgents().stream())
                .sorted(Comparator.comparing(Agent::getIndex))
                .collect(Collectors.toList()));
    }

    /**
     * @param agentStringFunction the function which returns some string property of the agent
     * @return the observable value
     */
    private ObservableStringValue makeObservableAgentProperty(Function<Agent, String> agentStringFunction) {
        return Bindings.createStringBinding(
                () -> {
                    switch (simulation.getState()) {
                        case NOT_LOADED:
                            return "";
                        case STOPPED:
                        case PAUSED:
                            Agent Agent = agentsTableView.getSelectionModel().getSelectedItem();
                            return Agent == null
                                    ? "<select agent to show inspection>"
                                    : agentStringFunction.apply(Agent);
                        case RUNNING:
                            return "<pause simulation to show inspection>";
                        default:
                            // Should not happen
                            throw new RuntimeException();
                    }
                },
                simulation.stateProperty(),
                simulation.getTick(),
                agentsTableView.getSelectionModel().selectedItemProperty());
    }

    /**
     * Initializes the agent inspection elements.
     */
    private void setupAgentInspections() {
        ObservableValue<Boolean> disable = Bindings.createBooleanBinding(
                () -> simulation.getState() == DefaultSimulation.State.NOT_LOADED,
                simulation.stateProperty());

        memoryTextArea.textProperty().bind(makeObservableAgentProperty(agent -> agent.getMemory().toString()));
        memoryTextArea.disableProperty().bind(disable);

        teamMemoryTextArea.textProperty().bind(makeObservableAgentProperty(agent -> agent.getTeam().getMemory().toString()));
        teamMemoryTextArea.disableProperty().bind(disable);

        statisticsTextArea.textProperty().bind(makeObservableAgentProperty(agent -> agent.getStatistics().toString()));
        statisticsTextArea.disableProperty().bind(disable);
    }

    /**
     * Initializes a bar chart.
     *
     * @param barChart the bar chart
     */
    private void setupEnergyBarChar(BarChart<String, Number> barChart) {
        barChart.setAnimated(false);
        barChart.setHorizontalGridLinesVisible(true);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setCategoryGap(0);
        barChart.setBarGap(10);
        barChart.setLegendSide(Side.BOTTOM);
        barChart.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);

        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setLowerBound(0);
        yAxis.setAutoRanging(false);
        yAxis.tickUnitProperty().bind(yAxis.upperBoundProperty().divide(5));
        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickVisible(false);
    }

    /**
     * Initializes the agent energy bar chart.
     */
    private void setupAgentEnergyBarChart() {
        setupEnergyBarChar(agentEnergyBarChart);
    }

    /**
     * Initializes the team energy bar chart.
     */
    private void setupTeamEnergyBarChart() {
        setupEnergyBarChar(teamEnergyBarChart);
    }

    /**
     * Populates the agent energy bar chart.
     */
    private void populateAgentEnergyBarChart() {
        List<Agent> agents = simulation.getGameState().getAgents();
        agents.forEach(agent -> {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(agent.getName());

            XYChart.Data<String, Number> agentData = new XYChart.Data<>();
            agentData.setXValue("");
            agentData.YValueProperty().bind(Bindings.createIntegerBinding(agent::getEnergy, simulation.getTick()));
            series.getData().add(agentData);

            agentEnergyBarChart.getData().add(series);
        });

        NumberAxis yAxis = (NumberAxis) agentEnergyBarChart.getYAxis();
        yAxis.upperBoundProperty().bind(Bindings.createIntegerBinding(
                () -> (int) Math.pow(10, Math.ceil(Math.log10(agents.stream().mapToInt(Agent::getEnergy).max().getAsInt()))),
                simulation.getTick()));
    }

    /**
     * Populates the team energy bar chart.
     */
    private void populateTeamEnergyBarChart() {
        List<Team> teams = simulation.getGameState().getTeams();
        teams.forEach(team -> {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(team.getName());

            XYChart.Data<String, Number> teamData = new XYChart.Data<>();
            teamData.setXValue("");
            teamData.YValueProperty().bind(Bindings.createIntegerBinding(team::getEnergy, simulation.getTick()));
            series.getData().add(teamData);

            teamEnergyBarChart.getData().add(series);
        });

        NumberAxis yAxis = (NumberAxis) teamEnergyBarChart.getYAxis();
        yAxis.upperBoundProperty().bind(Bindings.createIntegerBinding(
                () -> (int) Math.pow(10, Math.ceil(Math.log10(teams.stream().mapToInt(Team::getEnergy).max().getAsInt()))),
                simulation.getTick()));
    }

    /**
     * Opens and processes a settings file.
     */
    private void openSettings() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose simulation settings");
        fileChooser.setInitialDirectory(Paths.get(".").toFile());

        File chosenFile = fileChooser.showOpenDialog(openLabel.getScene().getWindow());
        if (chosenFile == null) {
            return;
        }

        simulation.initialize(new DefaultLoader().load(chosenFile.toPath()));
        mainCanvasController.initialize(simulation.getGameState(), simulation.getTick());

        simulation.restart();

        populateGameParameterTextFields();
        populateAgentTableView();
        setupAgentInspections();
        populateAgentEnergyBarChart();
        populateTeamEnergyBarChart();
    }

    /**
     * Exits the application.
     */
    private void exit() {
        Stage stage = (Stage) openLabel.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Initializes all elements.
     */
    public void initialize() {
        simulation = new DefaultSimulation();
        mainCanvasController = new MainCanvasController(mainCanvas);
        setupMenu();
        setupToolbar();
        setupGameParameterTextFields();
        setupAgentTableView();
        setupAgentEnergyBarChart();
        setupTeamEnergyBarChart();
    }

    /**
     * Callback called when the window is closed.
     */
    public void onStageClosed() {
        simulation.shutdown();
    }
}
