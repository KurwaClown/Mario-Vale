package gameobject.enemy;

import gameobject.GameObject;

public class Missile extends GameObject {
    public Missile(int x, int y,boolean toRight){
        super(x,y,"missile");
        setLookingRight(!toRight);
        setVelX(7);
        setVelY(0);
    }
}
