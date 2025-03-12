package FontVisitor;

// ButtonElement.java
import javafx.scene.control.Button;

public class ButtonElement implements Visitable {
    private Button button;

    public ButtonElement(Button button) {
        this.button = button;
    }

    @Override
    public void accept(FontVisitor visitor) {
        visitor.visit(this);
    }

    public Button getButton() {
        return button;
    }
    // Add other button-related methods if needed
}
