package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Passage;
import AdventureModel.Room;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
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

public abstract class AdventureGameViewAbstract {

    protected AdventureGame model; //model of the game
    protected Stage stage; //stage on which all is rendered
    protected Button saveButton, loadButton, helpButton; //buttons
    protected Boolean helpToggle = false; //is help on display?

    protected GridPane gridPane = new GridPane(); //to hold images and buttons
    protected Label roomDescLabel = new Label(); //to hold room description and/or instructions
    protected VBox objectsInRoom = new VBox(); //to hold room items
    protected VBox objectsInInventory = new VBox(); //to hold inventory items
    protected ImageView roomImageView; //to hold room image
    protected TextField inputTextField; //for user input


    public abstract void intiUI();

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

    public abstract void updateScene(String textToDisplay);

    public abstract boolean processRooms();

    public abstract void updateItems();

    public abstract void showInstructions();

    public abstract void addInstructionEvent();

    public abstract void addSaveEvent();

    public abstract void addLoadEvent();

    public abstract void articulateRoomDescription();

    public abstract void stopArticulation();


}