
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Mario  implements GameObject{
    private int marioX, marioY;
    private final int velX = 1;
    private final int jumpSpeed = 15; 
    private final int gravity = 1; 
    private BufferedImage image; 
    private boolean isRugbyman =true;
    private boolean interact = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean isJumping = false;
    private double velY = 0;
    private double rotationAngle = 0;

    public Mario() {

    }
    public void jump(GameEngine engine) {
        if(!isJumping() && !isFalling()){
            setJumping(true);
            setVelY(10);
        }
    }
    public void move(GameEngine engine){
        if (moveLeft){
            marioX -= velX;
        }
        if (moveRight)
            marioX += velX;
    }
    public void fall(GameEngine engine){
        if (isJumping) {
            marioY -= velY;
            velY -= gravity;
            if (marioY >= 150) { 
                marioY = 150;
                isJumping = false;
                velY = 0;
            }
        }
    }
    public void attack(GameEngine engine, Enemy enemy){
         int timingCharge =30;
         int regenCharge=300;
        if (isRugbyman == true){
            if (interact && regenCharge==300){
                while(timingCharge >0)
                {
                    velX = 10;
                    timingCharge--;

                }
                regenCharge++;

            if(this.collide(enemy)){
                enemy.die();
            }
        }
    }
}
public void Flag(GameEngine engine, Flag flag) {
    int numClicks=0;
    if (interact && this.collide(flag)) {
        flag.breakFlag(); 

        JOptionPane optionPane = new JOptionPane("Spam B", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog = new JDialog();
        dialog.setTitle("Message");
        dialog.setModal(true);

        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B) { 
                    numClicks++;
                }
            }
        });

        Timer timer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            } 
        }); 
        timer.setRepeats(false);
        timer.start();

        dialog.pack();
        dialog.setVisible(true);

        marioX += numClicks * 10;
        numClicks = 0;
    } 
} 
public void coins(Coin coin){
    if (this.collide(coin)){
        coin.disappear;
    }
}
public void bricks(Block block){
    if (this.collide(block) && block instanceof BonusBrick){
        block.transform();
        block.powerup();
    }
    if (this.collide(block)&& block instanceof Brick){
        block.transform();
    }
    if (this.collide(block)&& block instanceof Bonus){
        block.powerup();
        block.transfrom();
    }
}
public void die(Object enemy){
    if (enemy instanceof Champi || enemy instanceof Turtle || enemy instanceof Plant){
        if (this.collide(enemy)&& (horizontalHit)){
            gameover
        }
    }
}
}
