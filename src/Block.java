import java.awt.image.BufferedImage;

public abstract class Block extends GameObject{
    public Block(double xLocation, double yLocation, BufferedImage sprite) {
        super(xLocation, yLocation, sprite);
    }
}
