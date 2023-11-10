
public class Flag extends GameObject{

    public Flag(double xLocation, double yLocation) {
        super(xLocation, yLocation, "flag");
        sprite = Ressource.getImage("flag");
    }
    public void flagBreak(){
        sprite=Ressource.getImage("flagBreak");
    }
}
