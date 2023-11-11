import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Brick extends Block{

    public Brick(double xLocation, double yLocation) {
        super(xLocation, yLocation, "brick");
    }
    public void disappear(){
        y = 3000;
    }
}
