import cacheparts.CacheEngine;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    public static void main(String args[]){
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Cache Simulator");
        stage.setResizable(false);

        GridPane gridPaneFirst = createCacheGrid();
        GridPane gridPaneSecond = createCacheGrid();
        GridPane gridPaneThird = createCacheGrid();

        TextField textField = new TextField();
        textField.setMinWidth(630); // Set preferred width to 300 pixels
        textField.setMaxHeight(50); // Set preferred height to 50 pixels
        textField.setFont(new Font(25));
        textField.setPromptText("E.g 8");

        Label label = new Label("Enter Memory Blocks Here (N):");
        label.setFont(new Font(20));

        Button forwardButton = new Button("Step Forward");
        Button finalMemorySnapshotButton = new Button("Final Memory Snapshot");
        Button endButton = new Button("End Current Execution");
        Button startButton = new Button("Start Simulation");

        forwardButton.setDisable(true);
        finalMemorySnapshotButton.setDisable(true);

        endButton.setDisable(true);

        TextArea stepsArea = new TextArea();
        stepsArea.setFont(new Font(16));
        stepsArea.setEditable(false);

        CacheEngine[] simulator = new CacheEngine[1];

        textField.setOnAction(event -> {
            startSimulation(simulator, textField, stepsArea, endButton, startButton);
            forwardButton.setDisable(false);
            finalMemorySnapshotButton.setDisable(false);
        });

        startButton.setOnAction(event -> {
            startSimulation(simulator, textField, stepsArea, endButton, startButton);
            forwardButton.setDisable(false);
            finalMemorySnapshotButton.setDisable(false);
        });


        forwardButton.setOnAction(event -> {
            boolean flag = simulator[0].performStep(CACHE_LINES);
            if(flag){
                stepsArea.clear();
                stepsArea.appendText(simulator[0].getCurrentStepInformation());
            }
            else {
                forwardButton.setDisable(true);
                finalMemorySnapshotButton.setDisable(true);
                showPopup("Simulation Complete.");
            }
        });

        finalMemorySnapshotButton.setOnAction(event -> {
            simulator[0].performAllStep(stepsArea, CACHE_LINES);
            forwardButton.setDisable(true);
            finalMemorySnapshotButton.setDisable(true);
            showPopup("Simulation Complete.");
        });

        endButton.setOnAction(event -> {
            if (simulator[0] != null) {
                simulator[0] = null;
                stepsArea.clear();
                textField.setEditable(true);
                endButton.setDisable(true);
                startButton.setDisable(false);
                textField.setDisable(false);
                forwardButton.setDisable(true);
                finalMemorySnapshotButton.setDisable(true);
                textField.clear();
                CACHE_LINES.forEach(i -> {
                    i.forEach(h-> {
                        h.forEach(j -> {
                            j.setText("N/A");
                        });
                    });
                });
            }
        });

        HBox buttonsPane = new HBox(10, finalMemorySnapshotButton, forwardButton, endButton, startButton);
        buttonsPane.setAlignment(Pos.TOP_CENTER);

        HBox textPane = new HBox(textField);
        textPane.setAlignment(Pos.TOP_CENTER);


        VBox box = new VBox(20, label, textPane, buttonsPane, gridPaneFirst,gridPaneSecond,gridPaneThird, stepsArea);
        box.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(stepsArea, Priority.ALWAYS);


        Scene scene = new Scene(box, 900, 900);
        stage.setScene(scene);
        stage.show();
    }

    private static final int WINDOW_WIDTH = 850;
    private static final int N_BLOCKS = 8;
    private static final int COLUMN_WIDTH = (WINDOW_WIDTH - (N_BLOCKS + 1) * 10) / N_BLOCKS;
    private static final int ROW_HEIGHT = 50;

    public List<List<List<Label>>> CACHE_LINES = new ArrayList<>();
    private GridPane createCacheGrid() {
        CACHE_LINES.add(new ArrayList<>());
        CACHE_LINES.get(CACHE_LINES.size()-1).add(new ArrayList<>());
        CACHE_LINES.get(CACHE_LINES.size()-1).add(new ArrayList<>());

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(1); // Spacing between columns
        grid.setVgap(1); // Spacing between rows
        grid.setPadding(new Insets(10, 10, 10, 10)); // Padding around the grid

        // Create header for Blocks
        for (int block = 1; block <= N_BLOCKS; block++) {
            Label blockLabel = createStyledLabel("Block " + block, COLUMN_WIDTH, ROW_HEIGHT, "8", true);
            grid.add(blockLabel, block, 0);
        }

        // Create rows for Sets and MRU Status
        for (int set = 1; set <= 2; set++) {
            Label setLabel = createStyledLabel("Set " + set, COLUMN_WIDTH, ROW_HEIGHT, "6", true);

            grid.add(setLabel, 0, set);

            for (int block = 1; block <= N_BLOCKS; block++) {
                Label cacheValue = createStyledLabel("N/A", COLUMN_WIDTH, ROW_HEIGHT, "8", false);
                CACHE_LINES.get(CACHE_LINES.size()-1).get(set-1).add(cacheValue);
                grid.add(cacheValue, block, set);
            }
        }

        return grid;
    }

    private Label createStyledLabel(String text, int width, int height, String size, boolean bold) {
        Label label = new Label(text);
        label.setMinSize(width, height);
        label.setMaxSize(width, height);
        label.setPrefSize(width, height);
        if(!bold){
            label.setStyle("-fx-border-color: black; -fx-padding: " + size + "; -fx-alignment: center;");
        }
        else {
            label.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-padding: " + size + "; -fx-alignment: center;");
        }
        label.setWrapText(true);
        return label;
    }

    private void startSimulation(CacheEngine[] engine, TextField textField, TextArea stepsArea, Button endButton, Button startButton) {
        String text = textField.getText(); // Get input text

        textField.setEditable(false);
        textField.setDisable(true);
        //MachinePartsInitializer initializer = new MachinePartsInitializer("toread.txt");
        //MachineParts parts = initializer.initialize();

        engine[0] = new CacheEngine(Integer.parseInt(text));
        //stepsArea.appendText();
        endButton.setDisable(false);
        startButton.setDisable(true);
    }

    private void showPopup(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText(null);
        alert.setContentText(str);

        // Optional: If you want the alert to be modal to the whole application, not just the current stage
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }
}
