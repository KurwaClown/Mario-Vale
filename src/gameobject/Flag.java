package gameobject;

import view.Ressource;

import java.awt.*;


public class Flag extends GameObject {
    public Flag(double xLocation) {
        super(xLocation, 576, "flag");
        setSprite(Ressource.getImage("flag"));
        setY(getY()-getSpriteDimension().height);
    }
    public void flagBreak(){
        setSprite(Ressource.getImage("flagBroken"));
        setY(650 - getSpriteDimension().height);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
