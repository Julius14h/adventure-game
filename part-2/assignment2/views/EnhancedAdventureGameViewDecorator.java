package views;

import AdventureModel.AdventureGame;
import AutoSave.AutoSaveCommand;
import DeleteSaveCommand.DeleteSaveCommand;
import Dictionary.GuiSearchbar;
import FontVisitor.GuiFont;
import SettingsPage.SettingsPage;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import Voice.voiceplayer;
import javafx.scene.control.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.AccessibleRole;

import java.util.ArrayList;
import java.util.List;

import SettingsPage.Command;

/**
 * Class EnhancedAdventureGameViewDecorator.
 *
 * This class is a decorator for the AdventureGameView class.
 *
 */
public class EnhancedAdventureGameViewDecorator extends AdventureGameViewDecorator {

    Boolean zoomToggle = true; //is zoom enabled?
    AdventureGame model; //model of the game

    AdventureGameView view; // the original AdventureGameView object
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, deleteButton, zoomInButton, zoomOutButton, settingsButton ;//buttons
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input
    Stage settingsPage; // The state of Settings page

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    private List<Command> commands;

    /**
     * EnhancedAdventureGameViewDecorator constructor
     * __________________________
     * Initializes attributes and overwrites event handlers
     */
    public EnhancedAdventureGameViewDecorator(AdventureGameView adventureGameView) {
        super(adventureGameView);
        view = adventureGameView;
        model = adventureGameView.model;
        stage = adventureGameView.stage;
        saveButton = adventureGameView.saveButton;
        loadButton = adventureGameView.loadButton;
        helpButton = adventureGameView.helpButton;

        settingsButton = new Button("Settings");
        settingsButton.setId("Settings");
        customizeButton(settingsButton, 200, 50);
        makeButtonAccessible(settingsButton, "Settings Page button", "This button is to settings page.", "This button is to settings page. Click it to set your settings.");

        // init the command attributes
        initializeCommands();

        //init settings page
        settingsPage = creatSettings();
        addSettingsEvent();


        helpToggle = adventureGameView.helpToggle;
        gridPane = adventureGameView.gridPane;
        roomDescLabel = adventureGameView.roomDescLabel;
        objectsInRoom = adventureGameView.objectsInRoom;
        objectsInInventory = adventureGameView.objectsInInventory;
        roomImageView = adventureGameView.roomImageView;
        inputTextField = adventureGameView.inputTextField;
        addTextHandlingEvent();

        for (Node node : gridPane.getChildren()) {
            if (node instanceof HBox) {
                ((HBox) node).getChildren().add(settingsButton);
                break;
            }
        }

    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * enableZoom
     * __________________________
     * enable the zoom feature
     */
    public void enableZoom() {
        zoomToggle = true;
    }

    /**
     * disableZoom
     * __________________________
     * disable the zoom feature
     */
    public void disableZoom() {
        zoomToggle = false;
    }

    /**
     * getDeleteButton
     * __________________________
     * Returns a button that will delete a game from a list of saved files when triggered and displays a new window
     * to delete the files
     *
     */
    public Button getDeleteButton() {
        deleteButton = new Button("Delete");
        deleteButton.setId("Delete");
        customizeButton(deleteButton, 100, 50);
        makeButtonAccessible(deleteButton, "Delete Button", "This button deletes a game from a list " +
                "of saved files.", "This button deletes a game from a list of saved files. Click it" +
                " in order " +
                "to delete a game that you saved at a prior date.");
        return deleteButton;
    }

    /**
     * addDeleteEvent
     *  __________________________
     * This method handles the event related to the
     * delete button.
     */
    public void addDeleteEvent() {
        deleteButton.setOnAction(e -> {
            deleteSave();
        });
    }

    /**
     * deleteSave
     *  __________________________
     * Display a list of save files and delete the one selected by the user
     */
    public void deleteSave() {
        gridPane.requestFocus();
        DeleteView deleteView = new DeleteView(view);
    }



    /**
     * getZoomInButton
     * __________________________
     *
     * Returns a button that will decrease the size of the room image when triggered
     */
    public Button getZoomInButton() {
        zoomInButton = new Button("Zoom In");
        zoomInButton.setId("ZoomIn");
        customizeButton(zoomInButton, 100, 50);
        makeButtonAccessible(zoomInButton, "Zoom In Button", "This button zooms in on the room image.",
                "This button zooms in on the room image. Click it in order to zoom in on the room image.");
        return zoomInButton;
    }

    /**
     * addZoomInEvent
     *  __________________________
     * This method handles the event related to the
     * ZoomIn button.
     */
    public void addZoomInEvent() {
        zoomInButton.setOnAction(e -> {
            alterScene(1);
        });
    }


    /**
     * getZoomOutButton
     * __________________________
     *
     * Returns a button that will decrease the size of the room image when triggered
     */
    public Button getZoomOutButton() {
        zoomOutButton = new Button("Zoom Out");
        zoomOutButton.setId("ZoomOut");
        customizeButton(zoomOutButton, 100, 50);
        makeButtonAccessible(zoomOutButton, "Zoom Out Button", "This button zooms out" +
                        " on the room image.",
                "This button zooms out on the room image. Click it in order to zoom " +
                        "out on the room image.");
        return zoomOutButton;
    }

    /**
     * addZoomOutEvent
     *  __________________________
     * This method handles the event related to the
     * ZoomOut button.
     */
    public void addZoomOutEvent() {
        zoomInButton.setOnAction(e -> {
            alterScene(-1);
        });
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     *
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     * if the user hits the UP key, and Zoom is enabled, increase the size of the room image
     * if the user hits the DOWN key and Zoom is enabled, decrease the size of the room image
     *
     */

    private void addTextHandlingEvent() {
        // "implement addTextHandlingEvent" ChatGPT, 28 Oct.
        // version, OpenAI, 25 Sept. 2023, chat.openai.com/chat.
        this.inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String input = this.inputTextField.getText().strip();
                submitEvent(input); // strip whitespace from input and pass to submitEvent
                this.inputTextField.clear();
            } else if (event.getCode() == KeyCode.TAB) {
                gridPane.requestFocus(); // change the focus
            } else if (event.getCode() == KeyCode.UP && zoomToggle) {
                alterScene(1);
            } else if (event.getCode() == KeyCode.DOWN && zoomToggle) {
                alterScene(-1);
            }
        });
    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            updateScene(""); // update the scene as the player traverses the rooms
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(5)); // pause game for 5 seconds
            pause.setOnFinished(event -> {
                submitEvent("FORCED"); // keeping looping through forced events if any exist
            });
            pause.play();

        }
    }


    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        String possibleMoves = this.model.getPlayer().getCurrentRoom().getCommands();
        roomDescLabel.setText(possibleMoves); // get the moves the player can make from the rooms and return them

    }


    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }


    /**
     * changeRoomImage
     * __________________________
     *
     * increase the room image by 50 units if input is 1
     * decrease the room image by 50 units if input is -1
     */
    public void changeRoomImage(int input) {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";
        double height = roomImageView.getFitHeight();
        double width = roomImageView.getFitWidth();

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        if (input == 1 && height < 525) {
            // increase size
            roomImageView.setFitWidth(width + 25);
            roomImageView.setFitHeight(height + 25);
        } else if (input == -1 && height > 200) {
            roomImageView.setFitWidth(width - 25);
            roomImageView.setFitHeight(height - 25);
        }

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);

    }


    /**
     * alterScene
     * __________________________
     *
     * increase the room image by 50 units if input is 1
     * decrease the room image by 50 units if input is -1
     */
    public void alterScene(int x) {
        double height = roomImageView.getFitHeight();

        if (x == 1) {
            if (height <525) {
                changeRoomImage(x); //get the image of the current room
                formatText(""); //display current room description
                roomDescLabel.setPrefWidth(500);
                roomDescLabel.setPrefHeight(500);
                roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
                roomDescLabel.setWrapText(true);
                VBox roomPane = new VBox(roomImageView,roomDescLabel);
                roomPane.setPadding(new Insets(10));
                roomPane.setAlignment(Pos.TOP_CENTER);
                roomPane.setStyle("-fx-background-color: #000000;");

                gridPane.add(roomPane, 1, 1);
                stage.sizeToScene();
            } else {
                roomDescLabel.setText("Maximum zoom reached!");
            }
        } else if (x == -1) {
            if (height > 200) {
                changeRoomImage(x); //get the image of the current room
                formatText(""); //display current room description
                roomDescLabel.setPrefWidth(500);
                roomDescLabel.setPrefHeight(500);
                roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
                roomDescLabel.setWrapText(true);
                VBox roomPane = new VBox(roomImageView,roomDescLabel);
                roomPane.setPadding(new Insets(10));
                roomPane.setAlignment(Pos.TOP_CENTER);
                roomPane.setStyle("-fx-background-color: #000000;");

                gridPane.add(roomPane, 1, 1);
                stage.sizeToScene();
            } else {
                roomDescLabel.setText("Minimum zoom reached!");
            }

        }
    }
    public Stage creatSettings(){

        SettingsPage settingsPage = new SettingsPage(this.commands, new Stage());
        Scene settingsScene = new Scene(settingsPage.getLayout(), 400, 400);
        Stage settingsStage = new Stage();
        settingsStage.setScene(settingsScene);
        settingsStage.setTitle("Settings");

        return settingsStage;
    }

    public void showSettings() {
        settingsPage.show();
    }

    /**
     * Initializes the list of commands.
     */
    private void initializeCommands() {
        commands = new ArrayList<>();
        commands.add(new AutoSaveCommand(this.view));
        commands.add(new DeleteSaveCommand(this));
        commands.add(this.view.voice);
        commands.add(new GuiSearchbar());
        commands.add(new GuiFont(this));

        // Add more command in here after merge !!!!!
        // Add more command in here after merge !!!!!
        // Add more command in here after merge !!!!!
        // Add more command in here after merge !!!!!
        // Add more command in here after merge !!!!!

    }

    /**
     *
     * This method handles the event related to the
     * Setttings button.
     */
    public void addSettingsEvent() {

        settingsButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            gridPane.requestFocus();
            showSettings();
        });
    }

    public ArrayList<? extends Node> guillst(){
        ArrayList<Node> lst = new ArrayList<>();
        lst.add(saveButton);
        lst.add(getDeleteButton());
        lst.add(settingsButton);
        lst.add(helpButton);

        lst.add(roomDescLabel);
        lst.add(loadButton);
        lst.add(view.roomDescLabel);
        lst.add(view.invLabel);
        lst.add(view.objLabel);

//        System.out.println(lst.size());
        return lst;

    }
}
