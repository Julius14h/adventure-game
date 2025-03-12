package AutoSave;

import SettingsPage.GuiActionPair;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Font;
import views.AdventureGameView;
import SettingsPage.Command;

import java.util.HashMap;
import java.util.Map;


public class AutoSaveCommand implements Command {
    private CheckBox autosaveToggle = new CheckBox("Enable AutoSave");
    private AdventureGameView adventureGameView;
    private Originator originator;
    private Caretaker caretaker;


    public AutoSaveCommand(AdventureGameView adventureGameView) {
        this.caretaker = new Caretaker();
        this.originator = new Originator(adventureGameView,this.caretaker);
        autosaveToggle.setOnAction(e -> toggleAutosave(autosaveToggle.isSelected()));
        customizeCheckBox(autosaveToggle, 200, 30);
    }

    private void toggleAutosave(boolean isEnabled) {
        if (isEnabled) {
            originator.startAutoSave();
        } else {
            originator.stopAutoSave();
        }
    }

    private void customizeCheckBox(CheckBox checkBox, int w, int h) {
        checkBox.setPrefSize(w, h);
        checkBox.setFont(new Font("Arial", 16));
        checkBox.setStyle("-fx-background-color: #17871b; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 5;");
        checkBox.setPadding(new Insets(5, 10, 5, 10));
    }

    @Override
    public Map<String, GuiActionPair> getGuiElements() {
        Map<String, GuiActionPair> elements = new HashMap<>();
        elements.put("AUTOSAVE_TOGGLE", new GuiActionPair(autosaveToggle, null));
        return elements;
    }
}

