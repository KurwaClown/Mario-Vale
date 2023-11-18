package gameobject;

import java.awt.*;
import java.awt.image.BufferedImage;

import gameobject.character.Mario;
import view.Ressource;

public class GameObject {
    protected double x, y;
    protected double velX, velY;
    private final double gravity = 0.30f;
    private boolean lookingRight = true;
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
        if(!this.isLookingRight()) style = Ressource.getFlippedImage(this.sprite);
        if(style != null){
            g.drawImage(style, (int)this.x, (int)this.y, null);
        }
    }

    public void drawHitboxes(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(getTopCollision().x, getTopCollision().y, getTopCollision().width, getTopCollision().height);
        g.setColor(Color.GREEN);
        g.fillRect(getBottomCollision().x, getBottomCollision().y, getBottomCollision().width, getBottomCollision().height);
        g.setColor(Color.YELLOW);
        g.fillRect(getLeftCollision().x, getLeftCollision().y, getLeftCollision().width, getLeftCollision().height);
        g.setColor(Color.PINK);
        g.fillRect(getRightCollision().x, getRightCollision().y, getRightCollision().width, getRightCollision().height);
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
        if(lookingRight) x = x + velX;
        else x = x - velX;
    }
    // Creating Rectangle to check collisions (cf Game.java => Collisions management)
    public Rectangle getBottomCollision(){
        int hitboxHeight = isFalling() ? (int)this.velY : 1;
        return new Rectangle((int)x, (int)y + spriteDimension.height - hitboxHeight, spriteDimension.width, 1 + hitboxHeight);
    }

    public Rectangle getTopCollision(){
        int hitboxHeight = isJumping() ? (int)this.velY : 1;
        return new Rectangle((int)x, (int)(y), spriteDimension.width, 1 + hitboxHeight);
    }
    
    public Rectangle getLeftCollision(){ //Yellow collision
        int hitboxWidth = isLookingRight() ? 1 : Math.max((int)this.velX, 1);
        return new Rectangle((int)x-1, (int)y, hitboxWidth, spriteDimension.height);
    }

    public Rectangle getRightCollision(){ //Pink collision
        int hitboxWidth = isLookingRight() ? Math.max((int)this.velX, 1) : 1;
        return new Rectangle((int)x + spriteDimension.width - hitboxWidth - 1, (int)y, hitboxWidth + 1, spriteDimension.height);
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

    public void setLookingRight(boolean lookingRight) {
        this.lookingRight = lookingRight;
    }

    public boolean isLookingRight() {
        return lookingRight;
    }
    public void UpdateSpriteDimension() {
        this.spriteDimension = new Dimension(sprite.getWidth(), sprite.getHeight());
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
