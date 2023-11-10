import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bonus extends Block{


    public Bonus(double xLocation, double yLocation) {
        super(xLocation, yLocation);
        spritePath = "src/resources/bonus.png";
        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
