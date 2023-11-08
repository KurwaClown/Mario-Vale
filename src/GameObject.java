import java.awt.*;

public class GameObject {
    private double x, y;
    private double velX, velY;
    private final double gravity = 0.38f;


    private Dimension spriteDimension; // Dimension encapsulate width and height
    private Image sprite;

    private boolean jumping;

    public GameObject(double xLocation, double yLocation, Image sprite){
        this.x = xLocation;
        this.y = yLocation;
        this.sprite = sprite;
        this.spriteDimension = new Dimension(sprite.getWidth(null), sprite.getHeight(null));

        this.velX = 0;
        this.velY = 0;
    }

    public void moveObject(){
        if (velY >=  0 && jumping) {
            velY += gravity;
        }

        x += velX;
        y += velY;
    }

}
