package ZoomCommands;


import SettingsPage.Command;
import SettingsPage.GuiActionPair;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import views.EnhancedAdventureGameViewDecorator;
import java.util.HashMap;
import java.util.Map;



/**
 * class EnableZoomCommand
 *  __________________________
 * Used to enable the keyboard zoom commands
 */
class EnableZoomCommand implements Command {
    EnhancedAdventureGameViewDecorator view;

    /**
     * EnableZoomCommand constructor
     * __________________________
     * initializes attributes
     */
    public EnableZoomCommand(EnhancedAdventureGameViewDecorator view) {
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

        // Adding a checkbox
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(e -> checkboxAction(checkBox.isSelected()));
        elements.put("CHECKBOX", new GuiActionPair(checkBox, () -> checkboxAction(checkBox.isSelected())));

        return elements;
    }
    /**
     * checkboxAction
     *  __________________________
     * If the checkbox is selected, enable zooming, else disable it
     */
    private void checkboxAction(boolean selected) {
        if (selected) {
            this.view.enableZoom();
        } else {
            this.view.disableZoom();
        }
    }

}


/**
 * class ZoomInCommand
 *  __________________________
 * Used to zoom in into the current room image
 */
class ZoomInCommand implements Command {
    EnhancedAdventureGameViewDecorator view;

    /**
     * ZoomInCommand constructor
     * __________________________
     * initializes attributes
     */
    public ZoomInCommand(EnhancedAdventureGameViewDecorator view) {
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

        // Adding a zoom-in button
        Button button = this.view.getZoomInButton();
        button.setOnAction(e -> buttonAction());
        elements.put("BUTTON", new GuiActionPair(button, this::buttonAction));
        return elements;
    }

    /**
     * buttonAction
     *  __________________________
     * Increase the size of the image
     */
    private void buttonAction() {
        this.view.alterScene(1);
    }
}


/**
 * class ZoomOutCommand
 *  __________________________
 * Used to zoom out of the current room image
 */
class ZoomOutCommand implements Command {
    EnhancedAdventureGameViewDecorator view;

    /**
     * ZoomOutCommand constructor
     * __________________________
     * initializes attributes
     */
    public ZoomOutCommand(EnhancedAdventureGameViewDecorator view) {
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
        Button button = this.view.getZoomOutButton();
        button.setOnAction(e -> buttonAction());
        elements.put("BUTTON", new GuiActionPair(button, this::buttonAction));
        return elements;
    }

    /**
     * buttonAction
     *  __________________________
     * Decrease the size of the image
     */
    private void buttonAction() {
        this.view.alterScene(-1);
    }
}


