package AutoSave;

import views.AdventureGameView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

class Caretaker {
    private List<Memento> mementos = new ArrayList<>();

    public void addSettingsMemento(Memento memento) {
        mementos.add(memento);
    }

    /**
     * Saves the latest game state to a file.
     */
    public void saveLatestGameState() {
        if (mementos.isEmpty()) {
            return; // If there are no mementos, do nothing
        }

        Memento latestMemento = mementos.get(mementos.size() - 1);
        AdventureGameView gameState = latestMemento.getState();

        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String filename = "autosave" + timestamp + ".ser";
        String filepath = "Games/Saved/" + filename;


        File saveFile = new File(filepath);
        try (FileOutputStream file = new FileOutputStream(saveFile);
             ObjectOutputStream out = new ObjectOutputStream(file)) {
            out.writeObject(gameState.model);
        } catch (IOException e) {
            e.printStackTrace(); // Consider better exception handling or user feedback
        }
    }

}




