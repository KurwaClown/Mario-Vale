package view;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {

    private boolean isPlaying = false;

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
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(soundFileName));
                clip.open(inputStream);

                clip.loop(Clip.LOOP_CONTINUOUSLY);

                clip.start();

                isPlaying = true;

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        isPlaying = false;
                    }
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println("Error while loading sound");
                System.out.println("Skipping sound");
            }
        }
    }

    public void playSound(String soundFileName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("./src/resource/sound/" + soundFileName));
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error while playing sound");
            System.out.println("Skipping sound");
        }
    }

}
