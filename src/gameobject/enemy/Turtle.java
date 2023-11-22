package gameobject.enemy;

import gameobject.block.Block;
import view.Resource;
import gameobject.character.Mario;

public class Turtle extends Enemy {
    private boolean isShell = false;

    public Turtle(double xLocation, double yLocation) {
        super(xLocation, yLocation, "turtle");

    }

    @Override
    public void attacked(Class<?> attacker) {
        if (attacker == Mario.class) {
            if (!isShell) {
                transformInShell();
            } else if (Math.abs(this.getVelX()) < 8) {
                this.setVelX(8);
            } else {
                this.setVelX(0);
            }
        } else {
            System.out.println("attacked by something else");
            inverseVelX();
        }
    }

    public void transformInShell() {
        setSprite(Resource.getImage("shell"));
        isShell = true;
        setVelX(0);
    }
}

