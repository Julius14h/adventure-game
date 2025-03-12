package DeleteSaveCommand;

import SettingsPage.Command;
import SettingsPage.GuiActionPair;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import views.EnhancedAdventureGameViewDecorator;
import java.util.HashMap;
import java.util.Map;

public class DeleteSaveCommand implements Command {

    EnhancedAdventureGameViewDecorator view;

    /**
     * DeleteSaveCommand constructor
     * __________________________
     * initializes attributes
     */
    public DeleteSaveCommand(EnhancedAdventureGameViewDecorator view) {
        this.view = view;
    }

    /**
     * getGuiElements
     * __________________________
     * return a map of the gui elements associated with this command
     */
    @Override
    public Map<String, GuiActionPair> getGuiElements() {
        Map<String, GuiActionPair> elements = new HashMap<>();

        // Adding a zoom-out button
        Button button = this.view.getDeleteButton();
        button.setOnAction(e -> buttonAction());
        elements.put("BUTTON", new GuiActionPair(button, this::buttonAction));

        return elements;
    }


    /**
     * buttonAction
     *  __________________________
     * Display a list of save files and delete the one selected by the user
     */
    private void buttonAction() {
        this.view.deleteSave();

    }


}
