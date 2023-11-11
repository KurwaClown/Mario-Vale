import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public abstract class Enemy extends GameObject{

    public Enemy(double xLocation, double yLocation, String spriteName) {
        super(xLocation, yLocation, spriteName);
        setVelX(3.5);
    }
    public void disappear(){
        y = 3000;
    }
}
