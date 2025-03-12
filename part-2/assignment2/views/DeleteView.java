package views;

import AdventureModel.AdventureGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.sql.PseudoColumnUsage;
import java.util.ArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Class LoadView.
 *
 * Loads Serialized adventure games.
 */
public class DeleteView {

    private AdventureGameView adventureGameView;
    private Label deleteGameLabel;
    private Button deleteGameButton;
    private Button closeWindowButton;

    private ListView<String> GameList;
    private String filename = null;

    /**
     * DeleteView constructor
     *_________________________________
     * Initializes the delete view
     * @param adventureGameView the adventure game view
     */
    public DeleteView(AdventureGameView adventureGameView){


        Stage stage = new Stage();

        this.adventureGameView = adventureGameView;
        deleteGameLabel = new Label(String.format(""));

        GameList = new ListView<>(); //to hold all the file names

        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");
        deleteGameLabel.setId("CurrentGame"); // DO NOT MODIFY ID
        GameList.setId("GameList");  // DO NOT MODIFY ID
        getFiles(GameList); //get files for file selector
        GameList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        deleteGameButton = new Button("Delete Game");
        deleteGameButton.setId("DeleteGame"); // DO NOT MODIFY ID
        AdventureGameView.makeButtonAccessible(deleteGameButton, "delete game", "This is the button to delete a game", "Use this button to indicate a game file you would like to delete.");

        closeWindowButton = new Button("Close Window");
        closeWindowButton.setId("closeWindowButton"); // DO NOT MODIFY ID
        closeWindowButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        closeWindowButton.setPrefSize(200, 50);
        closeWindowButton.setFont(new Font(16));
        closeWindowButton.setOnAction(e -> dialog.close());
        AdventureGameView.makeButtonAccessible(closeWindowButton, "close window", "This is a button to close the load game window", "Use this button to close the load game window.");

        //on selection, call deleteGame
        deleteGameButton.setOnAction(e -> {
            try {
                deleteGame(deleteGameLabel, GameList);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox deleteGameBox = new VBox(10, deleteGameLabel, GameList, deleteGameButton, closeWindowButton);

        // Default styles which can be modified

        GameList.setPrefHeight(100);
        deleteGameLabel.setStyle("-fx-text-fill: #e8e6e3");
        deleteGameLabel.setFont(new Font(16));
        deleteGameButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        deleteGameButton.setPrefSize(200, 50);
        deleteGameButton.setFont(new Font(16));
        deleteGameBox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(deleteGameBox);
        Scene dialogScene = new Scene(dialogVbox, 300, 400);
        dialog.setScene(dialogScene);
        dialog.show();

    }


    /**
     * getFiles
     * __________________________________
     * Get Files to display in the on screen ListView
     * Populate the listView attribute with .ser file names
     * Files will be located in the Games/Saved directory
     *
     * @param listView the ListView containing all the .ser files in the Games/Saved directory.
     */
    private void getFiles(ListView<String> listView) {
        // "implement getFiles" ChatGPT, 28 Oct.
        // version, OpenAI, 25 Sept. 2023, chat.openai.com/chat.
        File gamesSavedFile = new File("Games/Saved");
        if (!gamesSavedFile.exists()) {
            gamesSavedFile.mkdir();
        }

        if (gamesSavedFile.exists() && gamesSavedFile.isDirectory()) {
            // confirm the directory exists
            // List all files in the directory
            File[] savedFilesList = gamesSavedFile.listFiles();

            if (savedFilesList != null) {
                // Create a list to store the names of .ser files
                ArrayList<String> filesToPopulate = new ArrayList<>();
                for (File file: savedFilesList) {
                    if (file.isFile() && file.getName().endsWith(".ser")) {
                        filesToPopulate.add(file.getName());
                    }
                }

                // Populate the ListView with the file names
                listView.getItems().addAll(filesToPopulate);
            }
        }

    }


    /**
     * deleteGame
     * __________________________________
     * Select the Game
     * Try to load a game from the Games/Saved
     * If successful, stop any articulation and put the name of the loaded file in the selectGameLabel.
     * If unsuccessful, stop any articulation and start an entirely new game from scratch.
     * In this case, change the selectGameLabel to indicate a new game has been loaded.
     *
     * @param deleteGameLabel the label to use to print errors and or successes to the user.
     * @param GameList the ListView to populate
     */
    private void deleteGame(Label deleteGameLabel, ListView<String> GameList) throws IOException {

        try {
            String selectedGame = GameList.getSelectionModel().getSelectedItem(); // get the selected file name
            Path path = Paths.get("Games/Saved", selectedGame); // use Paths for better platform independence
            File saveGameFile = path.toFile();

            boolean deleted = saveGameFile.delete();
            if (deleted) {
                deleteGameLabel.setText("Successfully deleted game.");
                // refresh the list
                GameList.getItems().clear();
                getFiles(GameList);
            } else {
                deleteGameLabel.setText("Reload the game before deleting this file.");
            }



        } catch (Exception e) {
            deleteGameLabel.setText("File does not exist");

        }

    }


}
