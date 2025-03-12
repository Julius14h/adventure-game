package Dictionary;
import AdventureModel.AdventureGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DictionaryTest {
    @Test
    void definitionTest() throws IOException{
    GUIDefinitionDisplay dict = new GUIDefinitionDisplay();
        assertEquals(" n. Mammal with a tubular snout and a long tongue, feeding on termites. [afrikaans]", dict.fetchDefinitionFromDictionary("Aardvark"));
        assertEquals(" . Stick carried for support when walking.", dict.fetchDefinitionFromDictionary("Walking-Stick"));

    }
}
