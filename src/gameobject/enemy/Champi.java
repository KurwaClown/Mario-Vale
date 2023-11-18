package gameobject.enemy;

import view.AudioManager;
import view.Ressource;

import java.awt.*;
import java.util.Random;

public class Champi extends Enemy {
    private boolean isRugbyman = false;
    private int regenCharge=300;

    private AudioManager audioManager = new AudioManager();
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
            audioManager.playSound("champicharge.wav");
            setVelX(9.9f);

            regenCharge = 0;
        }
        if(regenCharge<300){
            regenCharge++;
        }

        if(regenCharge==timingCharge) setVelX(3.5);
    }

    // Random use to create a champi or a RugbyChampi
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(10);
    }

    private Rectangle getForwardHitbox() {
        return new Rectangle((int) getX() + (isLookingRight() ? getSpriteDimension().width : -getSpriteDimension().width*3), (int) getY(), getSpriteDimension().width * 3 , getSpriteDimension().height);
    }
}



