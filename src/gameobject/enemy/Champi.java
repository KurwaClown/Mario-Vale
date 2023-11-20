package gameobject.enemy;

import view.AudioManager;
import view.Ressource;

import java.awt.*;
import java.util.Random;

public class Champi extends Enemy {
    private boolean rugbyman = false;
    private int regenCharge = 300;

    private final AudioManager audioManager = new AudioManager();

    public Champi(int x, int y){
        super(x, y, "champi");
        if (getRandom()<3){
            setRugbyman(true);
            setSprite(Ressource.getImage("champiRugby"));
        }
    }
    // Implementing the automatic moves and the ability to charge

    public void update(Rectangle marioHitbox) {
        if (isRugbyman() && getRegenCharge() == 300 && marioHitbox.intersects(getForwardHitbox())) {
            audioManager.playSound("champicharge.wav");
            setVelX(9.9f);

            setRegenCharge(0);
        }
        if(getRegenCharge()<300){
            incrementRegenCharge();
        }

        //TODO : Use time instead of frame to prevent issue when changing framerate
        int timingCharge = 30;
        if(getRegenCharge() == timingCharge) setVelX(3.5);
    }

    // Random use to create a champi or a RugbyChampi
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(10);
    }

    private Rectangle getForwardHitbox() {
        return new Rectangle((int) getX() + (isLookingRight() ? getSpriteDimension().width : -getSpriteDimension().width*3), (int) getY(), getSpriteDimension().width * 3 , getSpriteDimension().height);
    }

    public int getRegenCharge() {
        return regenCharge;
    }

    public void setRegenCharge(int regenCharge) {
        this.regenCharge = regenCharge;
    }

    public void incrementRegenCharge() {
        this.regenCharge++;
    }

    public boolean isRugbyman() {
        return rugbyman;
    }

    public void setRugbyman(boolean rugbyman) {
        this.rugbyman = rugbyman;
    }
}



