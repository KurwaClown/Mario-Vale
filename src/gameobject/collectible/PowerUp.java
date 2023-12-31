package gameobject.collectible;

import gameobject.GameObject;
import gameobject.block.Block;
import gameobject.character.Mario;

public abstract class PowerUp extends GameObject implements Collectible{

    public PowerUp(String spriteName) {
        super(-100, -3000, spriteName);
    }

    public void setLocation(Block block){
        setX(block.getX());
        setY(block.getY() - 96);
    }



    @Override
    public void onTouch(Mario mario){
        mario.powerup(this);
        disappear();
    }
}
