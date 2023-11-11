
public class Jersey extends PowerUp{
    public Jersey(double xLocation, double yLocation) {
        super(xLocation, yLocation, "jersey");
    }
    public void setLocation(Block block){
        x= block.x;
        y= block.y -96;

    }
    public void disappear(){
        y = 3000;
    }
}
