package gameobject;

import view.Ressource;

import java.awt.*;


public class Flag extends GameObject {
    private boolean flagBroken = false;

    private int count = 0;

    public Flag(double xLocation) {
        super(xLocation, 576, "flag");
        setSprite(Ressource.getImage("flag"));
        setY(getY()-getSpriteDimension().height);
    }
    public void flagBreak(){
        flagBroken = true;
        setSprite(Ressource.getImage("flagBroken"));
        setY(650 - getSpriteDimension().height);
    }
    public void increaseCount(){
        count++;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
