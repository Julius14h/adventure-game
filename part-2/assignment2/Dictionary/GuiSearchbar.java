package Dictionary;

import SettingsPage.Command;
import SettingsPage.GuiActionPair;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GuiSearchbar implements Command {
    TextField inputTextField; //for user input
    @Override
    public Map getGuiElements() {
        Map<String, GuiActionPair> elements = new HashMap<>();
        elements.put("Search_Bar", new GuiActionPair(SearchBar(), null));
        return elements;

    }

    public TextField SearchBar() {
        TextField searchField = new TextField();
        searchField.setPromptText("Enter your search term and press Enter");

        // Handling "Enter" key press
        searchField.setOnAction(e -> {
            String searchTerm = searchField.getText();
            GUIDefinitionDisplay dict = new GUIDefinitionDisplay();
            String definition = dict.fetchDefinitionFromDictionary(searchTerm);

            Stage popupStage = new Stage();
            Label popupLabel = new Label(definition);
            popupLabel.setStyle("-fx-text-fill: black;");

            // Create a ScrollPane for the Label
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(popupLabel);
            scrollPane.setFitToWidth(true); // Allow the content to resize horizontally
            scrollPane.setFitToHeight(true); // Allow the content to resize vertically
            scrollPane.setPrefSize(500, 500); // Set preferred size for the ScrollPane

            VBox popupLayout = new VBox(scrollPane); // Place ScrollPane inside VBox

            Scene popupScene = new Scene(popupLayout, 500, 500);
            popupScene.setFill(Color.BLACK); // Set background color of the Scene to black
            popupLayout.setStyle("-fx-background-color: black;");
            popupLabel.setWrapText(true);
            popupLabel.setMaxWidth(500);

            popupStage.setScene(popupScene);
            popupStage.setTitle(searchTerm + " definition");


            popupStage.show();
        });

        return searchField;
    }


}


