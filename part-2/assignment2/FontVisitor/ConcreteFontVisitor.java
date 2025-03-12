package FontVisitor;

// ConcreteFontVisitor.java
import javafx.scene.text.Font;

public class ConcreteFontVisitor implements FontVisitor {
    private String fontType;
    private int fontSize;

    public ConcreteFontVisitor(String fontType) {
        this.fontType = fontType;
        this.fontSize = fontSize;


    }

    public ConcreteFontVisitor(String fontType, int fontSize) {
        this.fontType = fontType;
        this.fontSize = fontSize;


    }



    @Override
    public void visit(LabelElement label) {
        label.getLabel().setFont(Font.font(fontType, fontSize));
    }

    @Override
    public void visit(ButtonElement button) {
        button.getButton().setFont(Font.font(fontType));
    }

    @Override
    public void visit(TextInputFieldElement inputText) {
        inputText.getInputField().setFont(Font.font(fontType, fontSize));
    }

}
