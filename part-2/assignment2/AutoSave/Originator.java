package AutoSave;
        import views.AdventureGameView;

        import java.util.Timer;
        import java.util.TimerTask;


/**
 * Originator class in the Memento pattern. This class is responsible for
 * creating a memento containing the state of the AdventureGame and
 * using it to restore its state.
 */
class Originator {
    private AdventureGameView adventureGameView; // Holds the current state of the game
    private Caretaker caretaker; // The caretaker responsible for managing mementos
    private Timer autoSaveTimer; // Timer for scheduling the auto-save tasks

    /**
     * Constructor for Originator.
     * @param adventureGameView Reference to the AdventureGameView which holds the game state.
     * @param caretaker Reference to the Caretaker responsible for memento management.
     */
    public Originator(AdventureGameView adventureGameView, Caretaker caretaker) {
        this.adventureGameView = adventureGameView;
        this.caretaker = caretaker;
    }

    /**
     * Starts the auto-save process with a fixed time interval.
     */
    public void startAutoSave() {
        autoSaveTimer = new Timer();
        autoSaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                saveCurrentState();
            }
        }, 0, 300000); // For example, auto-save every 5 minutes
    }

    /**
     * Stops the ongoing auto-save process.
     */
    public void stopAutoSave() {
        if (autoSaveTimer != null) {
            autoSaveTimer.cancel();
            autoSaveTimer = new Timer();
        }
    }

    /**
     * Saves the current state of the game by creating a memento and passing it to the caretaker.
     */
    private void saveCurrentState() {
        AdventureGameView currentState = this.adventureGameView;
        Memento memento = new Memento(currentState); // Create a memento with the current state
        caretaker.addSettingsMemento(memento); // Pass the memento to the caretaker for storage
        caretaker.saveLatestGameState();
    }
}


