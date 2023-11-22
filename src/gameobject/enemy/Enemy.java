package gameobject.enemy;

import gameobject.GameObject;
import gameobject.block.Block;
import gameobject.block.Bonus;
import gameobject.character.Mario;
import view.Map;

import java.awt.*;

// creating an abstract class that will be extended by all the enemies of the game
public abstract class Enemy extends GameObject {

    public Enemy(double xLocation, double yLocation, String spriteName) {
        super(xLocation, yLocation, spriteName);
        setLookingRight(false);
        setVelX(3.5);
        setFalling(true);
    }

    public void inverseVelX(){
        setLookingRight(!isLookingRight());
    }

    @Override
    protected void checkBlockCollisions(Map map, Rectangle marioHorizontalHitbox, Rectangle marioVerticalHitbox) {
        for (Block block : map.getBlocks()) {
            Rectangle blockVerticalHitbox = isJumping() ? block.getBottomCollision() : block.getTopCollision();
            Rectangle blockHorizontalHitbox = isLookingRight() ? block.getLeftCollision() : block.getRightCollision();


            if (marioHorizontalHitbox.intersects(blockHorizontalHitbox)) {
                if (isLookingRight()) {
                    this.setX(block.getX() - this.getSpriteDimension().getWidth());
                    inverseVelX();
                }
                else {
                    this.setX(block.getX() + block.getSpriteDimension().width + 1);
                    inverseVelX();
                }
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
                }
            }
        }
    }

    @Override
    public void attacked(Class<?> attacker) {
        if(attacker == Mario.class){
            this.disappear();
        } else if (attacker == Missile.class){
            this.disappear();
        }

    }
}
