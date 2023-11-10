import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Block extends GameObject{

    protected String spritePath;
    public Block(double xLocation, double yLocation, String name) {
        super(xLocation, yLocation, name);
    }
}
