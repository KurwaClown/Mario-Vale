package view;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AudioManager {

    private boolean isPlaying = false;

    private final String soundFolder = "/resource/sound/";

    private Clip clip;
    public AudioManager() {
    }
    public void playLoopSound(String soundFileName) {

        if (!isPlaying) {
            try {
                if (clip != null) {
                    clip.stop();
                    clip.close();
                }

                clip = AudioSystem.getClip();
                InputStream inputStream = AudioManager.class.getResourceAsStream(soundFolder + soundFileName);
                byte[] audioData = inputStream.readAllBytes();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);

                // Use Clip to play the audio
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                clip.loop(Clip.LOOP_CONTINUOUSLY);

                clip.start();

                isPlaying = true;

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        isPlaying = false;
                    }
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
                System.out.println("Error while loading sound");
                System.out.println("Skipping sound");
            }
        }
    }

    public void playSound(String soundFileName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(AudioManager.class.getResourceAsStream(soundFolder + soundFileName));
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error while playing sound");
            System.out.println("Skipping sound");
        }
    }

}
