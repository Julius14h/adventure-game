import AdventureModel.AdventureGame;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.AdventureGameView;
import views.EnhancedAdventureGameViewDecorator;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.application.Application.launch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BasicAdventureTest {

    @Test
    void getObjectString() throws IOException {
        AdventureGame game = new AdventureGame("TinyGame");;
        String objects = game.player.getCurrentRoom().getObjectString();
        assertEquals("a water bird", objects);
    }




}
