package gameobject.character;

import core.Game;
import gameobject.GameObject;
import gameobject.block.Block;
import gameobject.block.Bonus;
import gameobject.enemy.Enemy;
import view.Map;

import java.awt.*;

public class Projectile extends GameObject {

    public Projectile(int x, int y, boolean toRight) {
        super(x, y, "ballSmall");
        setVelX(7);
        setVelY(3);
        setLookingRight(toRight);
        setFalling(true);
    }

    @Override
    protected void checkBlockCollisions(Map map, Rectangle projectileHorizontalHitbox, Rectangle projectileVerticalHitbox) {

        for (Block block : map.getBlocks()) {
            Rectangle blockVerticalHitbox = isJumping() ? block.getBottomCollision() : block.getTopCollision();

            if (blockVerticalHitbox.intersects(projectileVerticalHitbox)) {
                if(this.isFalling()){

                Rectangle intersection = blockVerticalHitbox.intersection(projectileVerticalHitbox);
                this.setY(this.getY() - intersection.height);
                this.setVelY(7.5);
                this.setJumping(true);
                this.setFalling(false);
                }
                else this.disappear();
            }
        }
    }

    @Override
    protected void checkEnemyCollisions(Map map, Rectangle horizontalHitbox, Rectangle verticalHitbox) {
        for (Enemy enemy : map.getEnemies()) {
            if (enemy.getHitbox().intersects(this.getHitbox())) {
                enemy.disappear();
                this.disappear();
            }

        }
    }
}
