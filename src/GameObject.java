import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
    protected double x, y;
    protected double velX, velY;
    private final double gravity = 0.38f;


    private Dimension spriteDimension; // Dimension encapsulate width and height
    protected BufferedImage sprite;

    protected boolean jumping, falling;

    public GameObject(double xLocation, double yLocation){
        this.x = xLocation;
        this.y = yLocation;

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
        if(jumping && velY <= 0){ //After the apex of the jump
            jumping = false;
            falling = true;
        }
        else if(jumping){ // At jumping time
            velY = velY - gravity;
            y = y - velY;
        }

        if(falling){ // When falling after apex
            y = y + velY;
            velY = velY + gravity;
        }

        x = x + velX;
    }

    public Rectangle getBottomCollision(){
        return new Rectangle((int)x, (int)y + spriteDimension.height, spriteDimension.width, 5);
    }

    public Rectangle getTopCollision(){
        return new Rectangle((int)x, (int)y, spriteDimension.width, -5);
    }

    public Rectangle getLeftCollision(){
        return new Rectangle((int)x, (int)y, -5, spriteDimension.height);
    }

    public Rectangle getRightCollision(){
        return new Rectangle((int)x + spriteDimension.width, (int)y, 5, spriteDimension.height);
    }
    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getVelY() {
        return velY;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public Dimension getSpriteDimension() {
        return spriteDimension;
    }
}
