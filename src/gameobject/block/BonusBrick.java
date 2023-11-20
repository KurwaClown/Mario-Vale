package gameobject.block;

import gameobject.collectible.Collectible;
import view.Ressource;

public class BonusBrick extends Bonus {

    public BonusBrick(double xLocation, double yLocation, Collectible containedPowerUp) {
        super(xLocation, yLocation, containedPowerUp);
        this.setSprite(Ressource.getImage("bonusBrick"));
    }

}
