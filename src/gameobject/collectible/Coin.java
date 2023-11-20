package gameobject.collectible;

import gameobject.GameObject;
import gameobject.block.Block;
import gameobject.character.Mario;
import view.Ressource;

public class Coin extends GameObject implements Collectible{

    public Coin(double xLocation, double yLocation) {
        super(xLocation, yLocation, "coin");
        setSprite(Ressource.getImage("coin"));
    }



    public void onTouch(Mario mario){
        mario.addCoin();
        mario.addScore(200);
        disappear();
    }

    @Override
    public void setLocation(Block Block) {
        setX(Block.getX());
        setY(Block.getY() - this.getSpriteDimension().getHeight());
    }
}
