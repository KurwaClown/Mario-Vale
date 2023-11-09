import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject{
    public PowerUp(double xLocation, double yLocation, BufferedImage sprite) {
        super(xLocation, yLocation, sprite);
    }
}
