package gameobject.character;

import gameobject.GameObject;

public class Projectile extends GameObject {

    public Projectile(int x, int y, boolean toRight) {
        super(x, y, "ball");
        setVelX(7);
        setVelY(3);
        setLookingRight(toRight);
        setFalling(true);
    }
}
