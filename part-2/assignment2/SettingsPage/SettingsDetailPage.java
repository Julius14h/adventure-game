package SettingsPage;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Map;

/**
 * SettingsDetailPage class represents the detailed settings interface for a specific command.
 */
public class SettingsDetailPage {
    private VBox layout = new VBox(); // The layout for the detail settings
    private Command command; // The command for which to display the settings

    public SettingsDetailPage(Command command) {
        this.command = command;
        initializeLayout();
    }

    /**
     * Initializes the layout and displays settings for the command.
     */
    private void initializeLayout() {
        Map<String, GuiActionPair> guiElements = command.getGuiElements();

        // Set layout style and top padding
        layout.setStyle("-fx-background-color: black;");
        layout.setSpacing(10); // Set spacing between elements

        // Add top padding to avoid top-edge alignment
        Pane topPadding = new Pane();
        topPadding.setMinHeight(0.5);
        layout.getChildren().add(topPadding);

        for (GuiActionPair pair : guiElements.values()) {
            Node guiElement = pair.getGuiElement();

            // Customize Button
            if (guiElement instanceof Button) {
                customizeButton((Button) guiElement, 200, 30);
            }

            // Customize CheckBox
            if (guiElement instanceof CheckBox) {
                customizeCheckBox((CheckBox) guiElement, 200, 30);
            }

            // Customize Slider
            if (guiElement instanceof Slider) {
                customizeSlider((Slider) guiElement, 200);
            }

            layout.getChildren().add(guiElement);
        }
    }

    // Customize Button style
    private void customizeButton(Button button, int w, int h) {
        button.setPrefSize(w, h);
        button.setFont(new Font("Arial", 16));
        button.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    // Customize CheckBox style
    private void customizeCheckBox(CheckBox checkBox, int w, int h) {
        checkBox.setPrefSize(w, h);
        checkBox.setFont(new Font("Arial", 16));
        checkBox.setStyle("-fx-background-color: #17871b; -fx-text-fill: white; -fx-border-color: #17871b;");
    }

    // Customize Slider style
    private void customizeSlider(Slider slider, int w) {
        slider.setPrefWidth(w);
        slider.setStyle("-fx-control-inner-background: #17871b;");
    }

    public VBox getLayout() {
        return layout;
    }
}