package gameobject.block;

import gameobject.collectible.PowerUp;
import view.Ressource;

public class Bonus extends Block {

    private final PowerUp containedPowerUp;

    private boolean isDeadBlock = false;

    public Bonus(double xLocation, double yLocation, PowerUp containedPowerUp) {
        super(xLocation, yLocation, "bonus");
        this.containedPowerUp = containedPowerUp;
    }

    public void displayBonus(PowerUp powerUp) {
        powerUp.setLocation(this);
    }

    @Override
    public void hit() {
        if(isDeadBlock) return;
        displayBonus(containedPowerUp);
        isDeadBlock = true;
        setSprite(Ressource.getImage("deadBrick"));
    }

    public PowerUp getContainedPowerUp() {
        return containedPowerUp;
    }
}
