public class Coin extends GameObject{

    public Coin(double xLocation, double yLocation) {
        super(xLocation, yLocation, "coin");
        sprite = Ressource.getImage("coin");
    }
    public void disappear(){
        y = 3000;
    }
}
