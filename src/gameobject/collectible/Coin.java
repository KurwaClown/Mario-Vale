package gameobject.collectible;

import gameobject.GameObject;
import gameobject.character.Mario;
import view.Ressource;

import java.awt.*;

public class Coin extends GameObject implements Collectible{

    public Coin(double xLocation, double yLocation) {
        super(xLocation, yLocation, "coin");
        setSprite(Ressource.getImage("coin"));
    }
    public void disappear(){
        y = 3000;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
    }

    public void onTouch(Mario mario){
        mario.addCoin();
        mario.addScore(200);
        disappear();
    }
}
