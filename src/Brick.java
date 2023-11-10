import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Brick extends GameObject{

    public Brick(double xLocation, double yLocation) {
        super(xLocation, yLocation, "brick");
    }
}
