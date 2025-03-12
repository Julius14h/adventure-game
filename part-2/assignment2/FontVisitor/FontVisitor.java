package FontVisitor;

// FontVisitor.java
public interface FontVisitor {
    void visit(LabelElement label);
    void visit(ButtonElement button);

    void visit(TextInputFieldElement inputText);
}
