package gameobject.character;

import gameobject.GameObject;

import javax.swing.*;

public class Projectile extends GameObject {
    private final int LIFETIME = 2000;

    public Projectile(int x, int y){
        super(x, y, "ball");
        setVelX(7);
        setVelY(3);
        setFalling(true);
        Timer timer = new Timer(LIFETIME, e -> disappear());
        timer.setRepeats(false);
        timer.start();

    }

    public void disappear() {
        this.setY(-3000);
        this.setFalling(false);
    }

}
