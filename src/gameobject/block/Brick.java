package gameobject.block;

public class Brick extends Block{

    public Brick(double xLocation, double yLocation) {
        super(xLocation, yLocation, "brick");
    }
    public void disappear(){
        y = 3000;
    }

    @Override
    public void hit() {
        disappear();
    }
}
