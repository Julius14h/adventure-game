package Voice;

import Voice.player.Adapter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import SettingsPage.GuiActionPair;
import SettingsPage.Command;
import Voice.player.Mediaplayer;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;


import static views.AdventureGameView.makeButtonAccessible;


public class voiceplayer implements Command{
    public Mediaplayer mediaplayer;
    private int is_stereo = 0;
    private int is_playing = 1;
    private String last_played;

    Button PausePlayButton, ConvertAudio, OpenVoice;
    Slider volumeSlider;

    @Override
    public Map<String, GuiActionPair> getGuiElements() {
        Map<String, GuiActionPair> elements = new HashMap<>();
        elements.put("pause_and_play", new GuiActionPair(PausePlayButton, null));
        elements.put("stereo_convert", new GuiActionPair(ConvertAudio, null));
        elements.put("volumeSlider", new GuiActionPair(volumeSlider, null));
        elements.put("OpenVoice", new GuiActionPair(OpenVoice, null));
        return elements;
    }

    public voiceplayer(){

        mediaplayer = new Mediaplayer();
        PausePlayButton = new Button("Pause/Play");
        PausePlayButton.setId("Pause/Play");
        customizeButton(PausePlayButton, 100, 50);
        makeButtonAccessible(PausePlayButton, "PausePlayButton", "Press to pause the audio and press again to replay.", "Press to pause the audio and press again to replay.");
        PausePlayButton.setOnAction(e -> {
            if(is_playing == 1)mediaplayer.play();
            else mediaplayer.stop();
            is_playing = is_playing ^ 1;
        });

        ConvertAudio = new Button("ConvertAudio");
        ConvertAudio.setId("ConvertAudio");
        customizeButton(ConvertAudio, 100, 50);
        makeButtonAccessible( ConvertAudio, "ConvertButton", "Press to play stereo audio in wav format.", "Press to play stereo audio in wav format.");
        ConvertAudio.setOnAction(e -> {
            is_stereo = is_stereo ^ 1;
        });

        OpenVoice = new Button("OpenVoice");
        OpenVoice.setId("OpenVoice");
        customizeButton(OpenVoice, 100, 50);
        makeButtonAccessible( OpenVoice, "OpenVoice", "Press to open the voiceover.", "Press to open the voiceover.");
        OpenVoice.setOnAction(e -> {
            mediaplayer.change_voiceover();
        });


        volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);


        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                mediaplayer.stop();
                mediaplayer.change_volume(new_val.doubleValue());
            }
        });

        volumeSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean wasChanging, Boolean isChanging) {
                if (!isChanging) {
                    mediaplayer.stop();
                    mediaplayer.change_volume(volumeSlider.getValue());
                    mediaplayer.play();
                }
            }
        });
    }

    private void customizeButton(Button b, int w, int h) {
        b.setPrefSize(w, h);
        b.setFont(new Font("Arial", 16));
        b.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    public void play(String text){
        mediaplayer.readtxt(text);
    }

    public boolean is_stereo(){
        return is_stereo == 1;
    }
}
