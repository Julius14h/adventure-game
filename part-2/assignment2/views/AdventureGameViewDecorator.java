package views;

import views.AdventureGameView;

public class AdventureGameViewDecorator extends AdventureGameViewAbstract{

    private AdventureGameView adventureGameView;

    public AdventureGameViewDecorator(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
    }


    public void intiUI() {
        this.adventureGameView.intiUI();
    }

    public void updateScene(String textToDisplay) {
        this.adventureGameView.updateScene(textToDisplay);
    }

    public void updateItems() {
        this.adventureGameView.updateItems();
    }

    public void showInstructions() {
        this.adventureGameView.showInstructions();
    }


    public boolean processRooms(){return this.adventureGameView.processRooms(); }

    public void addInstructionEvent() {
        this.adventureGameView.addInstructionEvent();
    }

    public void addSaveEvent() {
        this.adventureGameView.addSaveEvent();
    }

    public void addLoadEvent() {
        this.adventureGameView.addLoadEvent();
    }

    public void articulateRoomDescription() {
        this.adventureGameView.articulateRoomDescription();
    }

    public void stopArticulation() {
        this.adventureGameView.stopArticulation();
    }

}
