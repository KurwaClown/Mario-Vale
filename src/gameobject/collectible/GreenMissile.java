package gameobject.collectible;

public class GreenMissile extends PowerUp{
    public GreenMissile(double x, double y){
        super("greenmissile");
        setX(x);
        setY(y);
        setVelX(7);
        setVelY(0);
        setFalling(false);
        setLookingRight(false);
    }
}
