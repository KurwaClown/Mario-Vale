package gameobject;

import view.Resource;

import java.awt.*;


public class Flag extends GameObject {
    public Flag(double xLocation) {
        super(xLocation, 576, "flag");
        setSprite(Resource.getImage("flag"));
        setY(getY()-getSpriteDimension().height);
    }
    public void flagBreak(){
        setSprite(Resource.getImage("flagBroken"));
        setY(650 - getSpriteDimension().height);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
