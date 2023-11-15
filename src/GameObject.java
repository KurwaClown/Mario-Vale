import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
    protected double x, y;
    protected double velX, velY;
    private final double gravity = 0.30f;

    private final int GROUND_LEVEL = 650;
    private Dimension spriteDimension; // Dimension encapsulate width and height
    protected BufferedImage sprite;

    protected boolean jumping, falling;

    public GameObject(double xLocation, double yLocation, String name){
        this.x = xLocation;
        this.y = yLocation;
        this.spriteDimension = new Dimension(64, 64);
        setSprite(Ressource.getImage(name)); //picking the Sprite in Ressource.java
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
    // Creating Rectangle to check collisions (cf Game.java => Collisions management)
    public Rectangle getBottomCollision(){
        return new Rectangle((int)x, (int)y + spriteDimension.height - 10, spriteDimension.width, 10);
    }

    public Rectangle getTopCollision(){
        return new Rectangle((int)x, (int)(y), spriteDimension.width, 10);
    }
    
    public Rectangle getLeftCollision(){
        return new Rectangle((int)(x), (int)y+3, 10, spriteDimension.height-3);
    }

    public Rectangle getRightCollision(){
        return new Rectangle((int)x + spriteDimension.width - 10, (int)y+3, 10, spriteDimension.height-3);
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

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
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

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
        updateSpriteDimension();
    }

    public void updateSpriteDimension() {
        this.spriteDimension.setSize(sprite.getWidth(), sprite.getHeight());
    }

    public Dimension getSpriteDimension() {
        spriteDimension.setSize(sprite.getWidth(), sprite.getHeight());
        return spriteDimension;
    }

    public Rectangle getHitbox() {
        return new Rectangle((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
    }

    public void UpdateSpriteDimension() {
        this.spriteDimension = new Dimension(sprite.getWidth(), sprite.getHeight());
    }
}
