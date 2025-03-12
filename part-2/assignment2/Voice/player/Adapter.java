package Voice.player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Adapter{
    public static void convertMp3ToWav(String mp3FilePath, String wavFilePath) {
        try {

            AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(new File(mp3FilePath));
            AudioFormat sourceFormat = mp3Stream.getFormat();


            AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    sourceFormat.getSampleRate(),
                    16,
                    sourceFormat.getChannels(),
                    sourceFormat.getChannels() * 2,
                    sourceFormat.getSampleRate(),
                    false);


            AudioInputStream converted = AudioSystem.getAudioInputStream(convertFormat, mp3Stream);


            AudioSystem.write(converted, AudioFileFormat.Type.WAVE, new File(wavFilePath));
            mp3Stream.close();
            converted.close();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
}
