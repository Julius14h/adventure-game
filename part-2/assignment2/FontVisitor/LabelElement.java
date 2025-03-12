package FontVisitor;

// LabelElement.java
import javafx.scene.control.Label;

public class LabelElement implements Visitable {
    private Label label;

    public LabelElement(Label label) {
        this.label = label;
    }

    @Override
    public void accept(FontVisitor visitor) {
        visitor.visit(this);
    }

    public Label getLabel() {
        return label;
    }
    // Add other label-related methods if needed
}
