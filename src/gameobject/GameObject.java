package gameobject;

import core.Game;
import core.GameState;
import gameobject.block.Block;
import gameobject.block.Bonus;
import gameobject.character.Mario;
import gameobject.character.Projectile;
import gameobject.collectible.Coin;
import gameobject.collectible.PowerUp;
import gameobject.enemy.Enemy;
import view.Map;
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

    protected boolean canJump = false;


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

    public void checkCollisions(Map map) {
        Rectangle verticalHitbox = isJumping() ? getTopCollision() : getBottomCollision();
        Rectangle horizontalHitbox = isLookingRight() ? getRightCollision() : getLeftCollision();
        if (!this.isJumping()) {
            this.setFalling(true);
        }
        checkBlockCollisions(map, horizontalHitbox, verticalHitbox);
        checkEnemyCollisions(map, horizontalHitbox, verticalHitbox);
        checkFlagCollisions(map);
    }


    protected void checkFlagCollisions(Map map) {
        Flag flag = map.getFlag();
        if (flag == null) return;

        if (this.getHitbox().intersects(flag.getHitbox())) {
            this.setX(flag.getX() - this.getSpriteDimension().getWidth());
            if (this instanceof Mario) flag.flagBreak();
            if (this instanceof Projectile) this.disappear();
        }
    }

    protected void checkEnemyCollisions(Map map, Rectangle horizontalHitbox, Rectangle verticalHitbox) {

        for (Enemy enemy : map.getEnemies()) {
            if (this == enemy) continue;
            Rectangle blockVerticalHitbox = isJumping() ? enemy.getBottomCollision() : enemy.getTopCollision();
            Rectangle enemyHorizontalHitbox = isLookingRight() ? enemy.getLeftCollision() : enemy.getRightCollision();
            if (horizontalHitbox.intersects(enemyHorizontalHitbox)) {
                if (this.getVelX() > enemy.getVelX() && this.getVelX() > 5) {
                    enemy.disappear();
                } else {
                    if (this instanceof Mario mario && mario.getHp() > 1) enemy.attacked(this.getClass());
                    else if (this instanceof Enemy) {
                        ((Enemy) this).inverseVelX();
                        enemy.inverseVelX();
                        continue;
                    }
                    this.attacked(enemy.getClass());

                }
            } else if (verticalHitbox.intersects(blockVerticalHitbox)) {
                this.setY(enemy.getY() - this.getSpriteDimension().height);
                enemy.attacked(this.getClass());
                this.setFalling(false);
                this.setCanJump(true);
                if (this instanceof Mario mario) {
                    mario.addScore(300);
                    mario.jump();
                }
            }
        }
    }

    protected void checkBlockCollisions(Map map, Rectangle marioHorizontalHitbox, Rectangle marioVerticalHitbox) {
        for (Block block : map.getBlocks()) {

            Rectangle blockVerticalHitbox = isJumping() ? block.getBottomCollision() : block.getTopCollision();
            Rectangle blockHorizontalHitbox = isLookingRight() ? block.getLeftCollision() : block.getRightCollision();


            if (marioHorizontalHitbox.intersects(blockHorizontalHitbox)) {
                if (isLookingRight()) this.setX(block.getX() - this.getSpriteDimension().getWidth());
                else this.setX(block.getX() + block.getSpriteDimension().width + 1);
            }
            if (marioVerticalHitbox.intersects(blockVerticalHitbox)) {
                if (isJumping()) {
                    this.setY(block.getY() + block.getSpriteDimension().height + 1);
                    this.setVelY(0);
                    if (block instanceof Bonus bonus) map.addCollectible(bonus.getContainedCollectible());
                    block.hit();
                } else {
                    this.setY(block.getY() - this.getSpriteDimension().height + 1);
                    this.setVelY(0);
                    this.setFalling(false);
                    if (this instanceof Mario mario) mario.canJump = true;
                }
            }
        }
    }


    // Creating Rectangle to check collisions (cf Game.java => Collisions management)
    public Rectangle getBottomCollision() {
        return new Rectangle((int) x + getSpriteDimension().width / 8, (int) getY() + getSpriteDimension().height / 2, 3 * getSpriteDimension().width / 4, getSpriteDimension().height / 2);
    }

    public Rectangle getTopCollision() {
        return new Rectangle((int) getX() + getSpriteDimension().width / 8, (int) getY(), (3 * getSpriteDimension().width / 4), getSpriteDimension().height / 2);
    }

    public Rectangle getLeftCollision() { //Yellow collision
        return new Rectangle((int) x, (int) y + getSpriteDimension().height / 6, getSpriteDimension().width / 4, getSpriteDimension().height / 3 * 2);
    }

    public Rectangle getRightCollision() { //Pink collision
        return new Rectangle((int) x + 3 * getSpriteDimension().width / 4, (int) y + getSpriteDimension().height / 6, getSpriteDimension().width / 4, getSpriteDimension().height / 3 * 2);
    }

    public void attacked(Class<?> attacker) {
        this.disappear();
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
        if (sprite == null) return;
        else this.sprite = sprite;
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

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void update() {
    }
}
