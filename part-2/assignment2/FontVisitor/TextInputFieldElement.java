package FontVisitor;



import javafx.scene.control.*;

import javafx.scene.control.Label;

public class TextInputFieldElement implements Visitable {
    private TextField inputField;

    public TextInputFieldElement(TextField inputField) {
        this.inputField = inputField;
    }

    @Override
    public void accept(FontVisitor visitor) {
        visitor.visit(this);
    }

    public TextField getInputField() {
        return inputField;
    }
}
