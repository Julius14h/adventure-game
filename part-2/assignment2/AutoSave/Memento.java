package AutoSave;

import views.*;


class Memento {
    private AdventureGameView state;

    public Memento(AdventureGameView state) {
        this.state = state;
    }

    public AdventureGameView getState() {
        return state;
    }
}
