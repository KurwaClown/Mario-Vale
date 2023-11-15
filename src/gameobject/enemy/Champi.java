package gameobject.enemy;

import view.Ressource;

import java.util.Random;

public class Champi extends Enemy {
    private boolean isRugbyman = false;
    private int regenCharge=300;
    private int timingCharge=30;

    public Champi(int x, int y){
        super(x, y, "champi");
        if (getRandom()<3){
            isRugbyman = true;
            setSprite(Ressource.getImage("champiRugby"));
        }
    }
    // Implementing the automatic moves and the ability to charge
    @Override
    public void update() {
        //TODO: Fix rugbyman
//        if (isRugbyman && regenCharge == 300) {
//            setVelX(10);
//            timingCharge--;
//            regenCharge = 0;
//            if (timingCharge == 0) {
//                velX = 0;
//                timingCharge = 30;
//            }
//
//        }
//        if(regenCharge<300){
//            regenCharge++;
//        }
    }

    // Random use to create a champi or a RugbyChampi
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(10);
    }
}



