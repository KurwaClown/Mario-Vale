package gameobject.enemy;

import gameobject.GameObject;
import gameobject.block.Block;
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

    public void attacked(){
        this.disappear();
    }

    @Override
    protected void checkEnemyCollisions(Map map, Rectangle horizontalHitbox, Rectangle verticalHitbox) {
        super.checkEnemyCollisions(map, horizontalHitbox, verticalHitbox);
        for (Enemy enemy : map.getEnemies()) {
            Rectangle enemyHorizontalHitbox = isLookingRight() ? enemy.getLeftCollision() : enemy.getRightCollision();
            if (horizontalHitbox.intersects(enemyHorizontalHitbox)) {
                inverseVelX();
            }
        }
        for(Block block : map.getBlocks()){
            Rectangle blockHorizontalHitbox = isLookingRight() ? block.getLeftCollision() : block.getRightCollision();
            if (horizontalHitbox.intersects(blockHorizontalHitbox)) {
                inverseVelX();
            }
        }
    }

    public void inverseVelX(){
        setLookingRight(!isLookingRight());
    }
}
