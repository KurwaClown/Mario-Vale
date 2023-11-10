import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public abstract class Enemy extends GameObject{

    public Enemy(double xLocation, double yLocation) {
        super(xLocation, yLocation);
    }
    public void disappear(){
        yLocation = 3000;
    }
}
