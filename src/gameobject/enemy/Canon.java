package gameobject.enemy;

import core.Game;
import gameobject.GameObject;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gameobject.character.Projectile;

public class Canon extends GameObject {
    private final Timer shootingTimer;

    private view.Map map;

    public Canon(int x, int y){
        super(x,y,"canon");
        shootingTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                missile(map);
            }
        });
        shootingTimer.start();
    }
    private void missile(view.Map map) {
        map.addMissile(new Missile((int) x, (int) y, isLookingRight()));
    }
}
