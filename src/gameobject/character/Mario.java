package gameobject.character;

import core.Game;
import gameobject.GameObject;
import gameobject.collectible.Ball;
import gameobject.collectible.Jersey;

import gameobject.collectible.PowerUp;
import gameobject.collectible.Trophy;

import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


public class Mario extends GameObject {

    private Mode mode = Mode.NORMAL;
    private final BufferedImage[] sprites;
    private int currentSpriteIndex;
    private long lastSpriteChangeTime;
    private static final long ANIMATION_TIME = 500;

    private int regenCharge = 300;
    private int counterCharge = 15;

    private boolean isCharging = false;

    public boolean canForceJump = true;

    private int coins = 0;
    private int score;

    private int hp = 1;

    private Game game;

    private List<Projectile> projectiles = new ArrayList<>();

    public void addScore(int points) {
        score += points;
        System.out.println("Score: " + this.getScore());
    }

    public int getScore() {
        return score;
    }


    private enum Mode {
        NORMAL, JERSEY, THROWER, WINNER, BRENNUS
    }

    private int numClicks = 0;

    private boolean readytoFly = false;

    public Mario(int x, int y) {
        super(x, y, "mario");
        sprites = new BufferedImage[2];
        sprites[0] = view.Ressource.getImage("mario");
        sprites[1] = view.Ressource.getImage("mario1");
        currentSpriteIndex = 0;
        lastSpriteChangeTime = System.currentTimeMillis();
        setFalling(true);
    }

    public void reset() {
        setX(50);
        setY(550);
        setVelX(0);
        setVelY(0);
        setFalling(true);
        setJumping(false);
        this.coins = 0;
        this.score = 0;
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
            setVelX(5);
        } else {
            setVelX(-5);
        }


    }

    public BufferedImage getCurrentSprite() {
        return sprites[currentSpriteIndex];
    }

    public void stop(boolean toRight) {
        if (toRight && getVelX() > 0) {
            setVelX(0);
        } else if (!toRight && getVelX() < 0) {
            setVelX(0);
        }
    }


    public void powerup(PowerUp powerUp) {
        if (powerUp instanceof Jersey) {
            this.mode = Mode.JERSEY;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (mode == Mode.JERSEY) {
                                mode = Mode.NORMAL;
                            }
                        }
                    },
                    12000
            );

        } else if (powerUp instanceof Ball) {
            this.mode = Mode.THROWER;
        } else if (powerUp instanceof Brennus) {
            this.mode = Mode.BRENNUS;
            this.hp = 2;
        } else if (powerUp instanceof Trophy) {
            this.mode = Mode.WINNER;
            hp = 1000;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (mode == Mode.WINNER) {
                                mode = Mode.NORMAL;
                                hp =1;
                            }
                        }
                    },
                    12000
            );

        }
        System.out.printf("Mario is now in %s mode\n", mode);
    }

    public void attack(view.Map map) {
        if (this.mode == Mode.JERSEY) {
            if (regenCharge == 300) {
                this.isCharging=true;
            } else {
                isCharging = false;
            }
        } else if (this.mode == Mode.THROWER) {
            if (regenCharge >= 30) {
                throwBall(map);
                regenCharge -= 30;
            }
        }
    }

    public void attacked() {
        this.hp--;
        if (this.hp == 1 && this.mode == Mode.BRENNUS) {
            this.mode = Mode.NORMAL;
            System.out.println(this.mode);
        }

    }

    private void throwBall(view.Map map) {
        map.addProjectile(new Projectile((int) x, (int) y));
    }

    public void finish() {
        readytoFly = true;
    }

    public void Flag() {

        JOptionPane optionPane = new JOptionPane("Spam B", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog = new JDialog();
        dialog.setTitle("Message");
        dialog.setModal(true);

        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    numClicks++;
                }
            }
        });

        Timer timer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                x+= numClicks * 10;
                System.out.println("Number of presses : " + numClicks);
                numClicks = 0;
            }
        });
        timer.setRepeats(false);
        timer.start();

        dialog.pack();
        dialog.setVisible(true);
    }


    public void update() {
        if (regenCharge < 300) regenCharge++;
        if (this.isCharging && this.counterCharge>0){
            velX = 10;
            regenCharge = 0;
            counterCharge--;
        }
        else if (!this.isCharging && this.counterCharge<15){
             counterCharge++;
             velX=5;
        }
        else {
            this.isCharging=false;
        }
    if (this.mode == Mode.JERSEY) {
            setSprite(view.Ressource.getImage("marioStade"));
        } else if (this.mode == Mode.BRENNUS) {
            setSprite(view.Ressource.getImage("marioBrennus"));
        } else if (this.mode == Mode.WINNER) {
            setSprite(view.Ressource.getImage("marioDore"));
        } else {
            setSprite(getCurrentSprite());
        }
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
        }

    public boolean getReadyToFly(){
        return readytoFly;
    }

    public void addCoin(){
        coins++;
        System.out.println("Score: " + this.getScore());
        System.out.println("Coins: " + this.getCoins());
    }

            public int getCoins () {
                return coins;
            }

            public int getHp () {
                return hp;
            }

        }

