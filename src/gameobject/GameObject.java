package gameobject;

import java.awt.*;
import java.awt.image.BufferedImage;

import gameobject.character.Mario;
import gameobject.enemy.Turtle;
import view.Ressource;

public class GameObject {
    private double x, y;
    private double velX, velY;
    private final double gravity = 0.30f;
    private boolean lookingRight = true;
    private Dimension spriteDimension; // Dimension encapsulate width and height
    protected BufferedImage sprite;

    protected boolean jumping, falling;

    public GameObject(double xLocation, double yLocation, String name){
        setX(xLocation);
        setY(yLocation);
        this.spriteDimension = new Dimension(64, 64);
        setSprite(Ressource.getImage(name)); //picking the Sprite in Ressource.java
        setVelX(0);
        setVelY(0);
    }


    /**
     * @param g Graphics object
     * Draws the sprite on the screen
     */
    public void draw(Graphics g) {
        BufferedImage style = this.sprite;
        if(!this.isLookingRight()) style = Ressource.getFlippedImage(this.sprite);
        if(style != null){
            g.drawImage(style, (int)getX(), (int)getY(), null);
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
        if(jumping && getVelY() <= 0){ //After the apex of the jump
            jumping = false;
            falling = true;
        }
        else if(jumping){ // At jumping time
            setVelY(getVelY() - gravity);
            setY(getY()- getVelY());
        }

        if(falling){ // When falling after apex
            setY(getY() + getVelY());
            setVelY(getVelY() + gravity);

        }
        if(lookingRight) setX(getX() + getVelX());
        else setX(getX() - getVelX());
    }
    // Creating Rectangle to check collisions (cf Game.java => Collisions management)
    public Rectangle getBottomCollision(){
        int hitboxHeight = isFalling() ? Math.max((int)getVelY(),1) : 1;
        return new Rectangle((int)getX(), (int)getY() + spriteDimension.height - hitboxHeight, spriteDimension.width, hitboxHeight);
    }

    public Rectangle getTopCollision(){
        int hitboxHeight = isJumping() ? Math.max((int)getVelY(),1) : 1;
        return new Rectangle((int)getX(), (int)getY(), spriteDimension.width, hitboxHeight);
    }
    
    public Rectangle getLeftCollision(){ //Yellow collision
        int hitboxWidth = isLookingRight() ? 1 : Math.max((int)getVelX(), 1);
        return new Rectangle((int)getX(), (int)getY(), hitboxWidth, spriteDimension.height);
    }

    public Rectangle getRightCollision(){ //Pink collision
        int hitboxWidth = isLookingRight() ? Math.max((int)getVelX(), 1) : 1;
        return new Rectangle((int)getX() + spriteDimension.width - hitboxWidth, (int)getY(), hitboxWidth, spriteDimension.height);
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
        return new Rectangle((int)getX(), (int)getY(), sprite.getWidth(), sprite.getHeight());
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
        setX(x);
        setY(y);
    }

    public void disappear(){
        y = 3000;
        setFalling(false);
    }

    public void drawData(Graphics g) {
        g.setColor(Color.BLACK);

        g.setFont(Font.getFont("defaultFont").deriveFont(8f));
        g.drawString("X: " + (int)getX() + " Y: " + (int)getY(), (int)getX(), (int)getY()-20);
        g.drawString("VelX: " + (int)getVelX() + " VelY: " + (int)getVelY(), (int)getX(), (int)getY()-10);
    }
}
