
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Champi extends Enemy {
    private final int champiSpeed = 1;
    private int direction =1;
    private int coordX;
    private boolean isRugbyman = false;
    private int counterCharge=0;
    private int chargeTime=60;
    private int charge;
    private int chargeSpeed=10;
    
    // Create th champi
    public Champi(int x, int y){
        super(x, y, "champi");
        if (getRandom()<3){
            isRugbyman = true;
            sprite=Ressource.getImage("champiRugby");
        }
    }
    // Implementing the automatic moves and the ability to charge
    public void move() {
        if (!isRugbyman) {
            coordX += champiSpeed * direction; 
            if (coordX <= 0 || coordX >= 100) {  
                direction *= -1;  
            }
        }
        else {
            if (charge > 0) {
                coordX += chargeSpeed;
                charge--;
            } else {
               
                coordX += champiSpeed * direction;
                if (coordX <= 0 || coordX >= 100) {  
                    direction *= -1;  
                    counterCharge++;
                    if (counterCharge >= 3) {
                        charge = chargeTime;
                        counterCharge = 0;
                    }
                }
            }
        }
    }
    public void disappear(){
        y = 3000;
    }
    // Random use to create a champi or a RugbyChampi
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(10);
    }
}



