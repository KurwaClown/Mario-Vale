package gameobject.enemy;

import gameobject.GameObject;

// creating an abstract class that will be extended by all the enemies of the game
public abstract class Enemy extends GameObject {

    public Enemy(double xLocation, double yLocation, String spriteName) {
        super(xLocation, yLocation, spriteName);
        setLookingRight(false);
        setVelX(3.5);
        setFalling(true);
    }

    public void attacked(){
        this.disappear();
    }

    public void inverseVelX(){
        setLookingRight(!isLookingRight());
    }
}
