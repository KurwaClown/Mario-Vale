import java.awt.image.BufferedImage;

public abstract class Block extends GameObject{
    public Block(double xLocation, double yLocation, BufferedImage sprite) {
        super(xLocation, yLocation, sprite);
    }
        public void transform(Object mario){
        if (mario instanceof Mario && hitUnder((Mario) mario)){
            this.setImageIo("./img/deadbrick.png");
        }
        }
    
    private boolean hitUnder(Mario mario) {
        boolean vertAlign = (mario.getY() + mario.getHeight()) == this.getY();
    
        boolean horiAlign = (mario.getX() < (this.getX() + this.getWidth()))
                                        && ((mario.getX() + mario.getWidth()) > this.getX());
    
        return vertAlign && horiAlign;
    }

    
}
