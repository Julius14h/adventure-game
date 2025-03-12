package SettingsPage;


import javafx.scene.Node;

/**
 * GuiActionPair class encapsulates a GUI element and its associated action.
 */
public class GuiActionPair {
    private Node guiElement;
    // In here Node is a base class for all JavaFX Scene Graph nodes(botton,checkbox...)
    private Runnable action;
    // Runnable is a functional interface for a block of code that can be executed


    /**
     * Constructor for GuiActionPair.
     * @param guiElement The GUI element, which is a subclass of Node.
     * @param action The action associated with the guiElement, implementing Runnable.
     */
    public GuiActionPair(Node guiElement, Runnable action) {
        this.guiElement = guiElement;
        this.action = action;
    }

    public Node getGuiElement() {
        return guiElement;
    }

    /**
     * Executes the action associated with the GUI element.
     * If an action is defined (not null), its run method is called.
     */

    public void executeAction() {
        if (action != null) {
            action.run();
            // run executes the block of code defined in the Runnable implementation
        }
    }
}