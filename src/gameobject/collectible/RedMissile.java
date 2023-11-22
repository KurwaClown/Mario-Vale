package gameobject.collectible;

public class RedMissile extends PowerUp{
    public RedMissile(double x, double y){
        super("redmissile");
        setX(x);
        setY(y);
        setVelX(7);
        setVelY(0);
        setFalling(false);
        setLookingRight(false);
    }
}
