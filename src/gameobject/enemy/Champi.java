package gameobject.enemy;

import view.Ressource;

import java.awt.*;
import java.util.Random;

public class Champi extends Enemy {
    private boolean isRugbyman = false;
    private int regenCharge=300;
    private int timingCharge=30;

    public Champi(int x, int y){
        super(x, y, "champi");
        if (getRandom()<3){
            isRugbyman = true;
            setSprite(Ressource.getImage("champiRugby"));
        }
    }
    // Implementing the automatic moves and the ability to charge

    public void update(Rectangle marioHitbox) {
        if (isRugbyman && regenCharge == 300 && marioHitbox.intersects(getForwardHitbox())) {
            setVelX(9.9f);
            timingCharge--;
            regenCharge = 0;
        }
        if(regenCharge<300){
            regenCharge++;
        }
        if(timingCharge==0) setVelX(3.5);
    }

    // Random use to create a champi or a RugbyChampi
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(10);
    }

    private Rectangle getForwardHitbox() {
        return new Rectangle((int) x + (isLookingRight() ? getSpriteDimension().width : -getSpriteDimension().width*3), (int) y, getSpriteDimension().width * 3 , getSpriteDimension().height);
    }
}



