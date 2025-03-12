package SettingsPage;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * SettingsPage class represents the main settings interface in the application.
 * It allows users to select features (commands) and view their detailed settings.
 */
public class SettingsPage {
    private VBox layout = new VBox(); // The main layout for the settings page

    private Map<String, CheckBox> settingsCheckboxes = new HashMap<>(); // Maps feature names to checkboxes

    private Button closeButton = new Button("Close"); // A button to close the settings page

    private List<Command> commands; // List of available commands (features)

    private SettingsDetailPage settingsDetailPage; // The detail page for displaying selected command's settings

    private Stage stage; // Stage

    public SettingsPage(List<Command> commands, Stage stage) {
        this.commands = commands;
        this.stage = stage;
        initializeLayout();
    }

    public VBox getLayout() {
        return layout;
    }

    /**
     * Initializes the layout and user interface components of the settings page.
     */
    private void initializeLayout() {
        // Initialize checkboxes for each command and add them to the layout
        layout.setStyle("-fx-background-color: black;");
        layout.setSpacing(10);

        // Adding space between checkbox and the top of scene
        Pane topPadding = new Pane();
        topPadding.setMinHeight(0.5);
        layout.getChildren().add(topPadding);

        for (Command command : commands) {
            String commandName = command.getClass().getSimpleName();
            CheckBox checkBox = new CheckBox(commandName);
            customizeCheckBox(checkBox, 200, 30);
            checkBox.setOnAction(e -> toggleCommand(commandName, checkBox.isSelected()));
            layout.getChildren().add(checkBox);
        }

        // Add close button to layout
        // customizeButton(closeButton, 200, 50);
        // closeButton.setOnAction(e -> closeStage());
        // layout.getChildren().add(closeButton);
    }

    private void customizeCheckBox(CheckBox checkBox, int w, int h) {
        checkBox.setPrefSize(w, h);
        checkBox.setFont(new Font("Arial", 16));
        checkBox.setStyle("-fx-background-color: #17871b; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 5;");
        checkBox.setPadding(new Insets(5, 10, 5, 10));
    }

    private void customizeButton(Button button, int w, int h) {
        button.setPrefSize(w, h);
        button.setFont(new Font("Arial", 16));
        button.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }


    private void reinitializeLayout() {
        for( Node node: layout.getChildren()){
            if (node instanceof CheckBox){
                ((CheckBox) node).setSelected(false);
            }
        }
    }


    /**
     * Closes the current stage.
     */
    private void closeStage() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Toggles the display of the detail page for a given command.
     * @param commandName The name of the command to toggle.
     * @param isSelected Indicates whether the command is selected or not.
     */
    private void toggleCommand(String commandName, boolean isSelected) {
        if (isSelected) {
            Command selectedCommand = commands.stream()
                    .filter(cmd -> cmd.getClass().getSimpleName().equals(commandName))
                    .findFirst().orElse(null);

            if (selectedCommand != null) {
                openDetailPageInNewStage(selectedCommand);
            }
        } else {
            // nothing
        }
        reinitializeLayout();
    }

    private void openDetailPageInNewStage(Command command) {
        SettingsDetailPage detailPage = new SettingsDetailPage(command);
        Scene scene = new Scene(detailPage.getLayout(), 300, 400);
        Stage detailStage = new Stage();
        detailStage.setScene(scene);
        detailStage.setTitle(command.getClass().getSimpleName());
        detailStage.initModality(Modality.APPLICATION_MODAL); // Set as modal window
        detailStage.show();
    }

    /**
     * Closes the settings page.
     */
    private void close() {
        stage.close(); // turn off stage
    }
}