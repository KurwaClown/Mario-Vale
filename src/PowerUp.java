import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class PowerUp extends GameObject{

    public PowerUp(double xLocation, double yLocation, String spriteName) {
        super(xLocation, yLocation, spriteName);
        setY(3000);
    }
}
