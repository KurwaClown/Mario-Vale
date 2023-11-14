import java.awt.image.BufferedImage;

public class Mario extends GameObject {

    private Mode mode = Mode.NORMAL;
    private final BufferedImage[] sprites;
    private int currentSpriteIndex;
    private long lastSpriteChangeTime;
    private static final long ANIMATION_TIME = 500;

    private int regenCharge = 300;
    private int timingCharge = 30;

    public boolean canForceJump = true;

    private int coins = 0;
    private int score;

    public void addScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    private enum Mode{
        NORMAL, JERSEY, THROWER, WINNER // Jersey, Ball and Trophy
    }

    public Mario(int x, int y) {
        super(x, y, "mario");
        sprites = new BufferedImage[2];
        sprites[0] = Ressource.getImage("mario");
        sprites[1] = Ressource.getImage("mario1");
        currentSpriteIndex = 0;
        lastSpriteChangeTime = System.currentTimeMillis();
        setFalling(true);
    }
    public void reset(){
        setX(50);
        setY(700);
    }
    public void jump() {
        if (!isJumping() && !isFalling()) {
            forceJump();
        } else if (canForceJump) {
            forceJump();
        }
    }
    public void forceJump() {
            setJumping(true);
            setFalling(false);
            setVelY(10);
            canForceJump = false;
    }

    public void move(boolean toRight) {
        if (toRight) {
            System.out.println("Moving to the right");
            setVelX(5);
        } else {
            System.out.println("Moving to the left");
            setVelX(-5);
        }


    }

    public BufferedImage getCurrentSprite() {
        return sprites[currentSpriteIndex];
    }

    public void stop(boolean toRight) {
        if (toRight && getVelX() > 0) {
            System.out.println("Stopping to the right");
            setVelX(0);
        } else if (!toRight && getVelX() < 0) {
            System.out.println("Stopping to the left");
            setVelX(0);
        }
    }


    //TODO: Handle power up in Game
    public void powerup(PowerUp powerUp) {
        if (powerUp instanceof Jersey) {
            this.mode = Mode.JERSEY;
            updateImage();
        } else if (powerUp instanceof Ball) {
            this.mode = Mode.THROWER;
        } else if (powerUp instanceof Trophy) {
            this.mode = Mode.WINNER;
        }
        System.out.printf("Mario is now in %s mode\n", mode);
    }

    public void attack() {
        if (this.mode == Mode.JERSEY) {
            if (regenCharge == 300) {
                velX = 10;
                regenCharge = 0;
            }
        }
    }

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

    public void updateImage() {
        if (this.mode == Mode.JERSEY) {
            sprite = Ressource.getImage("marioStade");
        } else {
            sprite = Ressource.getImage("mario");
        }
    }

    public void update() {
        if (regenCharge < 300) regenCharge++;

        if (this.mode == Mode.JERSEY) return;


        if (getVelX() != 0) {
            long currentTime = System.currentTimeMillis();
            long timeSinceLastChange = currentTime - lastSpriteChangeTime;

            if (timeSinceLastChange > ANIMATION_TIME) {
                currentSpriteIndex = (currentSpriteIndex + 1) % sprites.length;
                lastSpriteChangeTime = currentTime - (timeSinceLastChange - ANIMATION_TIME);

            }
        } else {
            currentSpriteIndex = 0;
        }

        sprite = getCurrentSprite();
    }

    public void addCoin(){
        coins++;
        System.out.println(coins);
    }

    public int getCoins() {
        return coins;
    }
}
