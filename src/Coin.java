import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Coin extends GameObject{

    private String spritePath = "";
    public Coin(double xLocation, double yLocation) {
        super(xLocation, yLocation);
        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
