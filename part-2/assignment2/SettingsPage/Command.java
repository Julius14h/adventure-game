package SettingsPage;

import java.util.Map;



/**
 * Command interface for managing GUI elements and their associated actions.
 */
public interface Command {
    Map<String, GuiActionPair> getGuiElements();
}

