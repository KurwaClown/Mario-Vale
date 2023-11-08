import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
    private double x, y;
    private double velX, velY;
    private final double gravity = 0.38f;


    private Dimension spriteDimension; // Dimension encapsulate width and height
    private BufferedImage sprite;

    private boolean jumping, falling;

    public GameObject(double xLocation, double yLocation, BufferedImage sprite){
        this.x = xLocation;
        this.y = yLocation;
        this.sprite = sprite;
        this.spriteDimension = new Dimension(sprite.getWidth(null), sprite.getHeight(null));

        this.velX = 0;
        this.velY = 0;
    }


    /**
     * @param g Graphics object
     * Draws the sprite on the screen
     */
    public void draw(Graphics g) {
        BufferedImage style = this.sprite;

        if(style != null){
            g.drawImage(style, (int)this.x, (int)this.y, null);
        }
    }


    public void moveObject(){
        if (velY >=  0 && jumping) {
            velY += gravity;
        }

        x += velX;
        y += velY;
    }

}
