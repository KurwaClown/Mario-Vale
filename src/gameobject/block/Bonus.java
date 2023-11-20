package gameobject.block;

import gameobject.collectible.Collectible;
import view.Ressource;

public class Bonus extends Block {

    private final Collectible containedCollectible;

    private boolean deadBlock = false;

    public Bonus(double xLocation, double yLocation, Collectible containedCollectible) {
        super(xLocation, yLocation, "bonus");
        this.containedCollectible = containedCollectible;
    }

    public void displayBonus(Collectible collectible) {
        collectible.setLocation(this);
    }

    @Override
    public void hit() {
        if(isDeadBlock()) return;
        displayBonus(getContainedCollectible());
        setDeadBlock(true);
        setSprite(Ressource.getImage("deadBrick"));
    }

    public Collectible getContainedCollectible() {
        return containedCollectible;
    }

    public boolean isDeadBlock() {
        return deadBlock;
    }

    public void setDeadBlock(boolean deadBlock) {
        this.deadBlock = deadBlock;
    }
}
