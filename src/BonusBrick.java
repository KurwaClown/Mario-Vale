import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BonusBrick extends Block {

    String spritePath = "";
    public BonusBrick(double xLocation, double yLocation) {
        super(xLocation, yLocation, "brick");
    }
}
