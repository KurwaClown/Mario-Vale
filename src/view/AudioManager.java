package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioManager {

    // Le constructeur n'a pas besoin de déclarer des exceptions puisque rien n'est fait dedans.
    public AudioManager() {
    }

    // Supposons que vous voulez jouer le son 'haka', vous pouvez nommer la méthode plus généralement.
    public void playLoopSound(String soundFileName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\romai\\IdeaProjects\\T-JAV-501-TLS_7\\src\\ressource\\sound\\" + soundFileName));
            clip.open(inputStream);

            // Mettre le son en boucle
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Démarrer le son
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void playSound(String soundFileName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\romai\\IdeaProjects\\T-JAV-501-TLS_7\\src\\ressource\\sound\\" + soundFileName));
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
