package FontVisitor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import SettingsPage.Command;
import SettingsPage.GuiActionPair;
import javafx.scene.control.Label;
import views.EnhancedAdventureGameViewDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiFont implements Command {

    boolean Fonton = false;
    EnhancedAdventureGameViewDecorator gameview;
    ArrayList<? extends Node> guiList;
    @Override
    public Map<String, GuiActionPair> getGuiElements() {
        Map<String, GuiActionPair> elements = new HashMap<>();
        elements.put("Font_select", new GuiActionPair(FontButton(), null));
        return elements;
    }

    public GuiFont(EnhancedAdventureGameViewDecorator gameview){
        this.gameview = gameview;
        guiList = gameview.guillst();
        System.out.println(guiList.size());




    }
    public Button FontButton(){
        Button toggleButton = new Button("Off");

        toggleButton.setOnAction(e -> {

             Fonton = ! Fonton; // Toggle the boolean value
            toggleButton.setText(Fonton ? "On" : "Off"); // Change the button text based on the boolean value
            if (Fonton == true){
                for (int i = 0; i < guiList.size(); i++) {
                    Node node = guiList.get(i);
                    if (node.getClass().getSimpleName().equals("Label")){
                        ConcreteFontVisitor fontVisitor = new ConcreteFontVisitor("Verdana", 16);
                        LabelElement labelButtonElement = new LabelElement((Label) node);
                        labelButtonElement.accept(fontVisitor);


                    }
                    if (node.getClass().getSimpleName().equals("Button")){
                        ConcreteFontVisitor fontVisitor = new ConcreteFontVisitor("Verdana");
                        ButtonElement ButtonElement = new ButtonElement((Button) node);
                        ButtonElement.accept(fontVisitor);


                    }

                }
                gameview.updateScene("");
            }
            else{
                for (int i = 0; i < guiList.size(); i++) {
                    Node node = guiList.get(i);
                    if (node.getClass().getSimpleName().equals("Label")){
                        ConcreteFontVisitor fontVisitor = new ConcreteFontVisitor("Arial", 16);
                        LabelElement labelButtonElement = new LabelElement((Label) node);
                        labelButtonElement.accept(fontVisitor);


                    }
                    if (node.getClass().getSimpleName().equals("Button")){
                        ConcreteFontVisitor fontVisitor = new ConcreteFontVisitor("Arial");
                        ButtonElement ButtonElement = new ButtonElement((Button) node);
                        ButtonElement.accept(fontVisitor);


                    }

                }
            }
            gameview.updateScene("");
        });
        return toggleButton;
    }
}
