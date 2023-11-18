package gameobject.block;

public class Brick extends Block{

    public Brick(double xLocation, double yLocation) {
        super(xLocation, yLocation, "brick");
    }


    @Override
    public void hit() {
        disappear();
    }
}
