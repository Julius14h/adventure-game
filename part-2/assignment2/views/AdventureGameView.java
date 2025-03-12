package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Passage;
import AdventureModel.Room;
import FontVisitor.ButtonElement;
import FontVisitor.ConcreteFontVisitor;
import FontVisitor.LabelElement;
import FontVisitor.TextInputFieldElement;
import SettingsPage.SettingsPage;
import SettingsPage.GuiActionPair;
import SettingsPage.Command;
import SettingsPage.SettingsDetailPage;
import AutoSave.AutoSaveCommand;
import Voice.player.Adapter;
import Voice.voiceplayer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 * LINK: https://utoronto-my.sharepoint.com/:v:/g/personal/shijun_chen_mail_utoronto_ca/EfwxOscexrNOujXWKaEAo6ABGZ0_nINNlVWLLY8fqwswig
 */
public class AdventureGameView {

    public AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, settingsButton; //buttons
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input

    Label objLabel =  new Label("Objects in Room");

    Label invLabel =  new Label("Your Inventory");


    voiceplayer voice;
    private boolean mediaPlaying; //to know if the audio is playing

    public List<Command> commands;
    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        this.voice = new voiceplayer();
        intiUI();
        initializeCommands();
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("chens531's Adventure Game"); //Replace <YOUR UTORID> with your UtorID

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, helpButton, loadButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Arial", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        //addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items

        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font("Arial", 16));

        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(new Font("Arial", 16));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font("Arial", 16));

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        scene.focusOwnerProperty().addListener((observable, oldFocus, newFocus) -> {
            if (newFocus != null) {
                System.out.println("Focus on: " + newFocus.getClass().getSimpleName());
                if(newFocus.getClass().getSimpleName().equals("Button") || newFocus.getClass().getSimpleName().equals("Textfield"))
                voice.mediaplayer.readtxt(newFocus.getAccessibleRoleDescription());
            }
        });
        this.stage.setScene(scene);
        this.stage.setResizable(false);

        //dyslexia font addition
        //FontOn = true;


        this.stage.show();

    }

    public void showSettings() {

        SettingsPage settingsPage = new SettingsPage(this.commands, new Stage());
        Scene settingsScene = new Scene(settingsPage.getLayout(), 400, 400);
        Stage settingsStage = new Stage();
        settingsStage.setScene(settingsScene);
        settingsStage.setTitle("Settings");
        settingsStage.show();
    }

    /**
     * Initializes the list of commands.
     */
    private void initializeCommands() {
        commands = new ArrayList<>();
        commands.add(new AutoSaveCommand(this));
        // Add more command in here after merge !!!!!
        // Add more command in here after merge !!!!!
        // Add more command in here after merge !!!!!
        // Add more command in here after merge !!!!!
        // Add more command in here after merge !!!!!
    }

    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
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
     */
    private void addTextHandlingEvent() {
        //add your code here!
        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String strippedText = inputTextField.getText().trim();
                submitEvent(strippedText);
                inputTextField.clear();
            } else if (event.getCode() == KeyCode.TAB) {
                event.consume();
                gridPane.requestFocus();
            }
        });
    }


    public boolean processRooms() {
        if(model.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDirection().equals("FORCED")) {

            inputTextField.setDisable(true);
            updateScene(null);
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                ArrayList<Passage> possibilities = new ArrayList<>();
                for (Passage entry : model.player.getCurrentRoom().getMotionTable().getDirection()) {
                    if (entry.getDirection().equals("FORCED")) { //this is the right direction
                        possibilities.add(entry); // are there possibilities?
                    }
                }

                //try the blocked passages first
                Passage chosen = null;
                for (Passage entry : possibilities) {
//            System.out.println(entry.getIsBlocked());
//            System.out.println(entry.getKeyName());

                    if (chosen == null && entry.getIsBlocked()) {
                        if (model.player.getInventory().contains(entry.getKeyName())) {
                            chosen = entry; //we can make it through, given our stuff
                            break;
                        }
                    } else { chosen = entry; } //the passage is unlocked
                }
                if(chosen == null)
                {
                    inputTextField.setDisable(false);
                    return;
                }
                int roomNumber = chosen.getDestinationRoom();
//                System.out.println(roomNumbeer);
                if(roomNumber == 0)return;
                Room room = model.getRooms().get(roomNumber);
                model.player.setCurrentRoom(room);
//                inputTextField.setDisable(true);
                if(!processRooms()){inputTextField.setDisable(false);updateScene(null);updateItems();}
            });
            pause.play();
            return true;
        }
        return false;
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
            inputTextField.setDisable(true);
            processRooms();

//            inputTextField.setDisable(false);
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
        String s = "";
        for(Passage T: model.player.getCurrentRoom().getMotionTable().passageTable){
            s += T.getDirection()+'\n';
        }
        roomDescLabel.setText(s);
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
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

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
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


    private void formatText2(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 10));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }



    private Button createObjectButton(String objectName) {
        String imagePath = this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        Button button = new Button("", imageView);
        makeButtonAccessible(button, objectName, objectName, objectName); // Assuming makeButtonAccessible exists and is correct

        return button;
    }

    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     *
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {
        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

        objectsInRoom.getChildren().clear();
        objectsInInventory.getChildren().clear();

        // Assuming you have methods to retrieve lists of object names
        // Populate objectsInRoom VBox
        for (AdventureObject roomObjects : model.player.getCurrentRoom().objectsInRoom) {
            Button button = createObjectButton(roomObjects.getName());
            button.setOnAction(event -> {
                if(objectsInRoom.getChildren().contains(button)){
                    objectsInRoom.getChildren().remove(button);
                    objectsInInventory.getChildren().add(button);
                    model.player.takeObject(roomObjects.getName());
                    updateScene("You have taken " + roomObjects.getName());
                }
                else{
                    objectsInInventory.getChildren().remove(button);
                    objectsInRoom.getChildren().add(button);
                    model.player.dropObject(roomObjects.getName());
                    updateScene("You have dropped " + roomObjects.getName());
                }
            });
            objectsInRoom.getChildren().add(button);
        }

        // Populate objectsInInventory VBox
        for (AdventureObject invobj : model.player.inventory) {
            Button button = createObjectButton(invobj.getName());
            button.setOnAction(event -> {
                if(objectsInRoom.getChildren().contains(button)){
                    objectsInRoom.getChildren().remove(button);
                    objectsInInventory.getChildren().add(button);
                    model.player.takeObject(invobj.getName());
                }
                else{
                    objectsInInventory.getChildren().remove(button);
                    objectsInRoom.getChildren().add(button);
                    model.player.dropObject(invobj.getName());
                }
            });
            objectsInInventory.getChildren().add(button);
        }


        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);


    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1);
        if (!helpToggle) {
            // Display help text
            formatText2(model.getInstructions());
            gridPane.add(roomDescLabel, 1, 1);
            helpToggle = true;
        } else {
            // Redraw the room image
            gridPane.add(roomImageView, 1, 1);
            updateScene(null);
            helpToggle = false;
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
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

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");
        String wavmusic = musicFile.replace("mp3", "wav");
        if(voice.is_stereo()){
            Adapter.convertMp3ToWav(musicFile, wavmusic);
            musicFile = wavmusic;
        }
        Media sound = new Media(new File(musicFile).toURI().toString());

        voice.mediaplayer.playsound(sound);
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            voice.mediaplayer.stop(); //shush!
            mediaPlaying = false;
        }
    }
}
