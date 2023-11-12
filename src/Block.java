import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Creating an abstract class that will be extended by all the blocks of the game 
public abstract class Block extends GameObject {

    public Block(double xLocation, double yLocation, String name) {
        super(xLocation, yLocation, name);
    }

    public void hit() {
    }
}

    

