package gameobject.character;

import gameobject.GameObject;

import javax.swing.*;

public class Projectile extends GameObject {
    private final int LIFETIME = 2000;

    public Projectile(int x, int y, boolean toRight) {
        super(x, y, "ball");
        setVelX(7);
        setVelY(3);
        setLookingRight(toRight);
        setFalling(true);
        Timer timer = new Timer(LIFETIME, e -> disappear());
        timer.setRepeats(false);
        timer.start();

    }

}
