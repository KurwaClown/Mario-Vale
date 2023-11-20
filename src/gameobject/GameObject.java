package gameobject;

import view.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    private double x, y;
    private double velX, velY;
    private boolean lookingRight = true;
    private final Dimension spriteDimension = new Dimension(0, 0); // Dimension encapsulate width and height
    protected BufferedImage sprite;
    protected boolean jumping, falling;

    public GameObject(double xLocation, double yLocation, String name) {
        setX(xLocation);
        setY(yLocation);
        setSprite(Resource.getImage(name)); //picking the Sprite in Resource.java
        setVelX(0);
        setVelY(0);
    }


    public void moveObject() {
        double GRAVITY = 0.30f;
        if (isJumping() && getVelY() <= 0) { //After the apex of the jump
            setJumping(false);
            setFalling(true);
        } else if (isJumping()) { // At jumping time
            setVelY(getVelY() - GRAVITY);
            setY(getY() - getVelY());
        }

        if (isFalling()) { // When falling after apex
            setY(getY() + getVelY());
            setVelY(getVelY() + GRAVITY);

        }
        if (isLookingRight()) setX(getX() + getVelX());
        else setX(getX() - getVelX());
    }

    // Creating Rectangle to check collisions (cf Game.java => Collisions management)
    public Rectangle getBottomCollision() {
        int hitboxHeight = isFalling() ? Math.max((int) getVelY(), 1) : 1;
        return new Rectangle((int) getX(), (int) getY() + getSpriteDimension().height - hitboxHeight, getSpriteDimension().width, hitboxHeight);
    }

    public Rectangle getTopCollision() {
        int hitboxHeight = isJumping() ? Math.max((int) getVelY(), 1) : 1;
        return new Rectangle((int) getX(), (int) getY(), getSpriteDimension().width, hitboxHeight);
    }

    public Rectangle getLeftCollision() { //Yellow collision
        int hitboxWidth = isLookingRight() ? 1 : Math.max((int) getVelX(), 1);
        return new Rectangle((int) getX(), (int) getY(), hitboxWidth, getSpriteDimension().height);
    }

    public Rectangle getRightCollision() { //Pink collision
        int hitboxWidth = isLookingRight() ? Math.max((int) getVelX(), 1) : 1;
        return new Rectangle((int) getX() + getSpriteDimension().width - hitboxWidth, (int) getY(), hitboxWidth, getSpriteDimension().height);
    }

    public void draw(Graphics g) {
        BufferedImage style = isLookingRight() ? getSprite() : Resource.getFlippedImage(getSprite());
        if (style != null) g.drawImage(style, (int) getX(), (int) getY(), null);
    }

    public void drawHitboxes(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(getTopCollision().x, getTopCollision().y, getTopCollision().width, getTopCollision().height);
        g.setColor(Color.GREEN);
        g.fillRect(getBottomCollision().x, getBottomCollision().y, getBottomCollision().width, getBottomCollision().height);
        g.setColor(Color.YELLOW);
        g.fillRect(getLeftCollision().x, getLeftCollision().y, getLeftCollision().width, getLeftCollision().height);
        g.setColor(Color.PINK);
        g.fillRect(getRightCollision().x, getRightCollision().y, getRightCollision().width, getRightCollision().height);
    }

    public void drawData(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString(String.format("x: %.02f | y: %.02f", getX(), getY()), (int) getX(), (int) getY() - 20);
        g.drawString(String.format("velX: %.02f | velY: %.02f", getVelX(), getVelY()), (int) getX(), (int) getY() - 10);
    }

    public void disappear() {
        setY(3000);
        setFalling(false);
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

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
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

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isJumping() {
        return jumping;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public boolean isFalling() {
        return falling;
    }

    public void setLookingRight(boolean lookingRight) {
        this.lookingRight = lookingRight;
    }

    public boolean isLookingRight() {
        return lookingRight;
    }
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
        updateSpriteDimension();
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void updateSpriteDimension() {
        this.spriteDimension.setSize(getSprite().getWidth(), getSprite().getHeight());
    }

    public Dimension getSpriteDimension() {
        return spriteDimension;
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) getX(), (int) getY(), getSprite().getWidth(), getSprite().getHeight());
    }
}
