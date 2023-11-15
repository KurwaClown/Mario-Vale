
public class Flag extends GameObject{

    public Flag(double xLocation) {
        super(xLocation, 650, "flag");
        setSprite(Ressource.getImage("flag"));
        setY(getY()-getSpriteDimension().height);
    }
    public void flagBreak(){
        setSprite(Ressource.getImage("flagBroken"));
        setY(650 - getSpriteDimension().height);
    }
}
