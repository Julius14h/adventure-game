package Voice.player;


import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.*;
import javax.sound.sampled.*;

import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.*;

public class Mediaplayer{
    private String VOICENAME = "kevin16";
    private String TEXT = "Hello, this is a text to speech test.";
    private String OUTPUT_FILE = "output";
    private MediaPlayer mediaPlayer = null;
    private double volume = 0.5;
    private boolean voiceover_on = false;

    public Mediaplayer(){
        readtxt("Welcome to the game");
        String path = new File(OUTPUT_FILE + ".wav").toURI().toString();
        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
    }
    public void readtxt(String txt) {

        TEXT = txt;
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        try {
            Voice voice;
            VoiceManager voiceManager = VoiceManager.getInstance();
            voice = voiceManager.getVoice(VOICENAME);
            if (voice == null) {
                System.err.println("Cannot find a voice named " + VOICENAME + ". Please specify a different voice.");
                System.exit(1);
            }

            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
            AudioPlayer audioPlayer = new SingleFileAudioPlayer(OUTPUT_FILE, fileType);
            voice.setAudioPlayer(audioPlayer);
            voice.setRate(130);
            voice.allocate();
            voice.speak(TEXT);

            voice.deallocate();
            audioPlayer.close();

            String path = new File(OUTPUT_FILE + ".wav").toURI().toString();
            Media media = new Media(path);
            System.out.println(voiceover_on);
            if(voiceover_on)
            playsound(media);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playsound(Media media){

        try {
            if(mediaPlayer != null)
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public void play(){
        if(voiceover_on)mediaPlayer.play();
    }

    public void change_volume(double v){
        volume = v;
        mediaPlayer.setVolume(v);
    }

    public void change_voiceover(){
        voiceover_on = !voiceover_on;
        System.out.println(voiceover_on);
    }
}
