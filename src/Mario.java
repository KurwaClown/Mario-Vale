import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Mario extends GameObject {
    private boolean isRugbyman = false;


    public Mario(int x, int y) {
        super(x, y, "mario");
    }

    public void jump() {
        if (!isJumping() && !isFalling()) {
            setJumping(true);
            setVelY(10);
        }
    }

    public void move(boolean toRight) {
        if(toRight) {
            System.out.println("Moving to the right");
            setVelX(5);
        } else {
            System.out.println("Moving to the left");
            setVelX(-5);
        }
    }
//    public void fall(){
//        if (isJumping()) {
//            marioY -= velY;
//            velY -= gravity;
//            if (marioY >= 150) {
//                marioY = 150;
//                isJumping = false;
//                velY = 0;
//            }
//        }
//    }

    //TODO: Handle power up in Game
//    public void powerup(PowerUp powerUp) {
//        if (this.collide(powerUp)) {
//            isRugbyman = true;
//            updateImage();
//            Timer timer = new Timer(12000, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    isRugbyman = false;
//                    updateImage();
//                    ((Timer) e.getSource()).stop();
//                }
//            });
//            timer.setRepeats(false);
//            timer.start();
//        }
//    }
//
//    public void attack(Enemy enemy) {
//        int timingCharge = 30;
//        int regenCharge = 300;
//        if (isRugbyman == true) {
//            if (interact && regenCharge == 300) {
//                while (timingCharge > 0) {
//                    velX = 10;
//                    timingCharge--;
//
//                }
//                regenCharge++;
//
//                if (this.collide(enemy)) {
//                    enemy.die();
//                }
//            }
//        }
//    }
//public void Flag(Flag flag) {
//    int numClicks=0;
//    if (interact && this.collide(flag)) {
//        flag.breakFlag();
//
//        JOptionPane optionPane = new JOptionPane("Spam B", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
//        final JDialog dialog = new JDialog();
//        dialog.setTitle("Message");
//        dialog.setModal(true);
//
//        dialog.setContentPane(optionPane);
//        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//        dialog.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_B) {
//                    numClicks++;
//                }
//            }
//        });
//
//        Timer timer = new Timer(5000, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                dialog.dispose();
//            }
//        });
//        timer.setRepeats(false);
//        timer.start();
//
//        dialog.pack();
//        dialog.setVisible(true);
//
//        marioX += numClicks * 10;
//        numClicks = 0;
//    }
//}

    //public void coins(Coin coin){
//    if (this.collide(coin)){
//        coin.disappear;
//    }
//}
//public void bricks(Block block){
//    if (this.collide(block) && block instanceof BonusBrick){
//        block.transform();
//        block.powerup();
//    }
//    if (this.collide(block)&& block instanceof Brick){
//        block.transform();
//    }
//    if (this.collide(block)&& block instanceof Bonus){
//        block.powerup();
//        block.transfrom();
//    }
//}
//public void die(Object enemy){
//    if (enemy instanceof Champi || enemy instanceof Turtle || enemy instanceof Plant){
//        if (this.collide(enemy)&& (horizontalHit)){
//            gameover
//        }
//    }
//}
    public void updateImage() {
        if (isRugbyman) {
            sprite = Ressource.getImage("marioStade");
        } else {
            sprite = Ressource.getImage("mario");
        }
    }
}
