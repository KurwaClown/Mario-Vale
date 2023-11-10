import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Turtle extends GameObject{
    private String spritePath = "";
    public Turtle(double xLocation, double yLocation) {
        super(xLocation, yLocation);
        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
