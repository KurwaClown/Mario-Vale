import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Block extends GameObject{

    protected String spritePath;
    public Block(double xLocation, double yLocation) {
        super(xLocation, yLocation);
        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
