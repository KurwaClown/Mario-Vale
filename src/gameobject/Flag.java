package gameobject;

import view.Resource;

import java.awt.*;


public class Flag extends GameObject {
    private boolean flagBroken = false;
    public Flag(double xLocation) {
        super(xLocation, 576, "flag");
        System.out.println("Flag created");
        setSprite(Resource.getImage("flag"));
        setY(getY()-getSpriteDimension().height);
    }
    public void flagBreak(){
        flagBroken = true;
        setSprite(Resource.getImage("flagBroken"));
        setY(650 - getSpriteDimension().height);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }

    public boolean isFlagBroken() {
        return flagBroken;
    }
}
