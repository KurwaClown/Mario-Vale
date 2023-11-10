
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Champi extends GameObject {
    private final int champiSpeed = 1;
    private int direction =1;
    private int coordX;
    private boolean isRugbyman;
    private int counterCharge=0;
    private int chargeTime=60;
    private int charge;
    private int chargeSpeed=10;

    private String spritePath = "";
    public Champi(int x, int y){
        super(x, y);

        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        getRandom();
        if (getRandom()<3){
            isRugbyman =true;
        }
        else{
            isRugbyman=false;
        }
    }

    public void moove() {
        if (isRugbyman == false){
        coordX += champiSpeed * direction; 
        if (coordX <= 0 || coordX >= 100) {
            direction *= -1; 
        }
    }
        else{
        coordX += champiSpeed * direction; 
        if (coordX <= 0 || coordX >= 100) {
            direction *= -1; 
            counterCharge++;
            if (counterCharge==2){
                charge = chargeTime;
                while(charge > 0)
                    coordX += chargeSpeed;
                    charge--;
            }
            counterCharge=0;
                
                
            }
        }
    }
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(10);
    }
}



