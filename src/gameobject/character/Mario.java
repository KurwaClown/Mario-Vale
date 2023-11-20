package gameobject.character;

import gameobject.GameObject;
import gameobject.collectible.*;
import view.AudioManager;

import java.awt.image.BufferedImage;


public class Mario extends GameObject {

    private Mode mode = Mode.NORMAL;
    private final BufferedImage[] sprites;
    private int currentSpriteIndex;
    private long lastSpriteChangeTime;
    private static final long ANIMATION_TIME = 500;

    private int regenCharge = 300;
    private int counterCharge = 15;

    private final AudioManager audioManager = new AudioManager();
    private boolean isCharging = false;

    public boolean canJump = true;

    private int coins = 0;
    private int score;

    private int hp = 1;


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


    public Mario() {
        super(50, 476, "mario");
        sprites = new BufferedImage[2];
        sprites[0] = view.Ressource.getImage("mario");
        sprites[1] = view.Ressource.getImage("mario1");
        currentSpriteIndex = 0;
        lastSpriteChangeTime = System.currentTimeMillis();
        setFalling(true);
    }

    //For testing purposes only
    public void rotatePowerUp() {
        if (mode == Mode.NORMAL) {
            mode = Mode.JERSEY;
        } else if (mode == Mode.JERSEY) {
            mode = Mode.THROWER;
        } else if (mode == Mode.THROWER) {
            mode = Mode.BRENNUS;
        } else if (mode == Mode.BRENNUS) {
            mode = Mode.WINNER;
        } else if (mode == Mode.WINNER) {
            mode = Mode.NORMAL;
        }
    }

    public void reset() {
        setX(50);
        setY(550);
        setVelX(0);
        setVelY(0);
        this.hp = 1;
        mode = Mode.NORMAL;
        setLookingRight(true);
        setFalling(true);
        setJumping(false);
        this.canJump = true;
        this.coins = 0;
        this.score = 0;
    }

    public void jump() {
        if (canJump) {
            setJumping(true);
            setFalling(false);
            setVelY(11);
            audioManager.playSound("saut.wav");
            canJump = false;
        }
    }


    public void move(boolean toRight) {
        setLookingRight(toRight);
        setVelX(5);
    }

    public BufferedImage getCurrentSprite() {
        return sprites[currentSpriteIndex];
    }

    public void stop(boolean toRight) {
        if(toRight == isLookingRight()) setVelX(0);
    }


    public void powerup(PowerUp powerUp) {
        audioManager.playSound("power-up.wav");
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
            this.isCharging = regenCharge == 300;
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
        map.addProjectile(new Projectile((int) getX(), (int) getY(), isLookingRight()));
    }

    public void finish() {
    }



    public void update() {
        if (regenCharge < 300) regenCharge++;
        if (this.isCharging && this.counterCharge>0){
            setVelX(10);
            regenCharge = 0;
            counterCharge--;
        }
        else if (!this.isCharging && this.counterCharge<15){
             counterCharge++;
             setVelX(3.5);
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

    public void addCoin(){
        audioManager.playSound("piece.wav");
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

