import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bonus extends Block{


    public Bonus(double xLocation, double yLocation) {
        super(xLocation, yLocation, "bonus");

    }
    public void displayBonus(Jersey jersey){
        jersey.setLocation(this);
    }
}
