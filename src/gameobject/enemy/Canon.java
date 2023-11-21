package gameobject.enemy;

import view.Map;

import javax.swing.*;
import java.util.Random;

public class Canon extends Enemy {


    public Canon(int x, int y, Map map){
        super(x,y,"canon");
        setVelX(0);
        setFalling(false);
        Timer shootingTimer = new Timer(randomNumber(), e -> missile(map));
        shootingTimer.start();
    }
    private void missile(view.Map map) {
        map.addEnemy(new Missile((int) getX(), (int) getY(), isLookingRight()));
    }
    private int randomNumber(){
        Random random = new Random();
        return random.nextInt(1000,3000);
    }

}
