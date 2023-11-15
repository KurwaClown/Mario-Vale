package gameobject.collectible;

import gameobject.GameObject;
import gameobject.character.Mario;
import gameobject.block.Block;

import java.awt.*;

public abstract class PowerUp extends GameObject implements Collectible{

    public PowerUp(String spriteName) {
        super(-100, -3000, spriteName);
    }

    public void setLocation(Block block){
        x= block.getX();
        y= block.getY() - 96;
    }

    @Override
    public void disappear(){
        y = 3000;
    }

    @Override
    public void onTouch(Mario mario){
        mario.powerup(this);
        disappear();
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
    }
}
