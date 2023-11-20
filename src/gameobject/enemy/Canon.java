package gameobject.enemy;

import core.Game;
import gameobject.GameObject;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import gameobject.character.Projectile;
import view.Map;

public class Canon extends Enemy {
    private final Timer shootingTimer;


    public Canon(int x, int y, Map map){
        super(x,y,"canon");
        setVelX(0);
        setFalling(false);
        shootingTimer = new Timer(randomNumber(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                missile(map);
            }
        });
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
