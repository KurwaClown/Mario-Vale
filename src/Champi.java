import java.util.Random;

public class Champi extends Enemy {
    private boolean isRugbyman = false;
    private int regenCharge=60;
    private int timingCharge=30;

    public Champi(int x, int y){
        super(x, y, "champi");
        if (getRandom()<3){
            isRugbyman = true;
            sprite=Ressource.getImage("champiRugby");
        }
    }
    // Implementing the automatic moves and the ability to charge
    public void update() {
        if (isRugbyman && regenCharge == 60) {
            velX = 10;
            timingCharge--;
            regenCharge = 0;
            if (timingCharge == 0) {
                velX = 0;
                timingCharge = 30;
            }
        }
    }

    // Random use to create a champi or a RugbyChampi
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(10);
    }
}



