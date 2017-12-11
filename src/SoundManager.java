import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {

    public static Clip getSound(URL url) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}