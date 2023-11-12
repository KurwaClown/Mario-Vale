import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bonus extends Block {

    PowerUp containedPowerUp;

    public Bonus(double xLocation, double yLocation, PowerUp containedPowerUp) {
        super(xLocation, yLocation, "bonus");
        this.containedPowerUp = containedPowerUp;
    }

    public void displayBonus(PowerUp powerUp) {
        powerUp.setLocation(this);
    }

    @Override
    public void hit() {
        displayBonus(containedPowerUp);
        //TODO: transform into a deadblock
    }

    public PowerUp getContainedPowerUp() {
        return containedPowerUp;
    }
}
