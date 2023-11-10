
public class Jersey extends GameObject{
    public Jersey(double xLocation, double yLocation) {
        super(xLocation, yLocation, "jersey");
    }
    public void appear(){
        sprite = Ressource.getImage("jersey");
    }
    public void disappear(){
        yLocation = 3000;
    }
}
