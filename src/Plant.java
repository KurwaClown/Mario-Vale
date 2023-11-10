import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Plant extends GameObject{

    private String spritePath = "";
    public Plant(double xLocation, double yLocation) {
        super(xLocation, yLocation, "plant");
    }
}
