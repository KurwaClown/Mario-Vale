package gameobject.enemy;

import java.util.Random;

public class Plant extends Enemy{
private final int counter = 0;
public boolean isOut=false;
public int direction =1;
    public Plant(double xLocation, double yLocation) {
        super(xLocation, yLocation, "plant");
    }
    public void goingUpAndDown(){
        if(counter % 60==0){
            Random rand = new Random();
            int test = rand.nextInt(2);
            if (test ==1 && !isOut){
                setY(getY() + 50);
            }
            else if (test ==1 && isOut){
                setY(getY() - 50);
            }
        }

    }
//    public void move(Block block){
//        xLocation += velX * direction;
//        if (this.collide(block)){
//            direction = direction *(-1);
//        }
//    }
}
