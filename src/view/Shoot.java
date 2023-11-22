package view;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Shoot {
    private Timer timer;
    private double angle = 0;
    private double power = 0;

    private boolean transformed = false;
    private boolean increasingPower = true;
    private boolean angleLocked = false;
    private boolean powerLocked = false;


    public Shoot() {
    }

    public void start() {
        timer = new Timer(10, e -> {
            if(!angleLocked) {
                angle += 2.5;
                if (angle > 90) {
                    angle = 0;
                }
            }
             else {
                if (increasingPower) {
                    power += 1;
                    if (power > 25) {
                        increasingPower = false;
                    }
                } else {
                    power -= 1;
                    if (power < 0) {
                        increasingPower = true;
                    }
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
    }



    public double getAngle() {
        return angle;
    }

    public double getPower() {
        return power;
    }
    public void lockAngle() {
        angleLocked = true;
    }
    public boolean isAngleLocked() {
        return angleLocked;
    }
    public void lockPower() {
        powerLocked = true;
    }
    public boolean isPowerLocked() {
        return powerLocked;
    }

    public boolean getTransformed(){
        return this.transformed;
    }
    public void setTransformed(boolean value){
        this.transformed= value;
    }

    public void setAngleLocked (boolean value){
        this.angleLocked=value;
    }
    public void setPowerLocked (boolean value){
        this.powerLocked=value;
    }
}


