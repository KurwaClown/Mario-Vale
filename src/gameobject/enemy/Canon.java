package gameobject.enemy;

import gameobject.collectible.GreenMissile;
import gameobject.collectible.RedMissile;
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
        randomNumber1();
        if(randomNumber1()>46 && randomNumber1()<=50){
            map.addCollectible(new GreenMissile(getX(),getY()));
        } else if(randomNumber1()<=40&& randomNumber1()>30)
            map.addCollectible(new RedMissile(getX(), getY()));
        else {
            map.addEnemy(new Missile((int) getX() - 50, (int) getY(), isLookingRight()));
        }
    }
    private int randomNumber(){
        Random random = new Random();
        return random.nextInt(1000,3000);
    }
    private int randomNumber1(){
        Random random = new Random();
        return random.nextInt(0,50);
    }

    @Override
    public void attacked() {
        return;
    }
}
