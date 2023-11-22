package gameobject.character;

import gameobject.GameObject;
import gameobject.collectible.*;
import view.AudioManager;
import view.Resource;

import java.awt.image.BufferedImage;

public class Mario extends GameObject {

    private Mode mode = Mode.NORMAL;
    private final BufferedImage[] sprites;
    private int currentSpriteIndex;
    private long lastSpriteChangeTime;
    private static final long ANIMATION_TIME = 500;
    private boolean readyToKick = false;

    private int regenCharge = 300;
    private int counterCharge = 15;

    private final AudioManager audioManager = new AudioManager();
    private boolean isCharging = false;

    private boolean dontMove = false;

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
        sprites[0] = Resource.getImage("mario");
        sprites[1] = Resource.getImage("mario1");
        setCurrentSpriteIndex(0);
        lastSpriteChangeTime = System.currentTimeMillis();
        setFalling(true);
    }

    //For testing purposes only
    public void rotatePowerUp() {
        if (getMode() == Mode.NORMAL) {
            powerup(new Jersey());
        } else if (getMode() == Mode.JERSEY) {
            powerup(new Ball());
        } else if (getMode() == Mode.THROWER) {
            powerup(new Brennus());
        } else if(getMode() == Mode.BRENNUS){
            powerup(new Trophy());
        } else {
            powerup(null);
        }
    }

    public void reset() {
        setX(50);
        setY(550);
        setVelX(0);
        setVelY(0);
        this.hp = 1;
        setMode(Mode.NORMAL);
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
        return sprites[getCurrentSpriteIndex()];
    }

    public void stop(boolean toRight) {
        if (toRight == isLookingRight()) setVelX(0);
    }


    public void powerup(PowerUp powerUp) {
        audioManager.playSound("power-up.wav");
        if (powerUp instanceof Jersey) {
            setMode(Mode.JERSEY);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (getMode() == Mode.JERSEY) {
                                setMode(Mode.NORMAL);
                            }
                        }
                    },
                    12000
            );

        } else if (powerUp instanceof Ball) {
            setMode(Mode.THROWER);
        } else if (powerUp instanceof Brennus) {
            setMode(Mode.BRENNUS);
            this.hp = 2;
        } else if (powerUp instanceof Trophy) {
            setMode(Mode.WINNER);
            hp = 1000;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (getMode() == Mode.WINNER) {
                                setMode(Mode.NORMAL);
                                hp = 1;
                            }
                        }
                    },
                    12000
            );

        } else if (powerUp == null) {
            setMode(Mode.NORMAL);
            hp = 1;
        }
        System.out.printf("Mario is now in %s mode\n", getMode());
        System.out.println("HP: " + this.getHp());
    }

    public void attack(view.Map map) {
        if (this.getMode() == Mode.JERSEY) {
            this.isCharging = regenCharge == 300;
        } else if (this.getMode() == Mode.THROWER) {
            if (regenCharge >= 30) {
                throwBall(map);
                regenCharge -= 30;
            }
        }
    }

    public void attacked() {
        this.hp--;
        if (this.hp == 1 && this.getMode() != Mode.NORMAL) {
            setMode(Mode.NORMAL);
        }

    }

    private void throwBall(view.Map map) {
        map.addProjectile(new Projectile((int) getX(), (int) getY(), isLookingRight()));
    }

    public void update() {
        if (regenCharge < 300) regenCharge++;
        if (this.isCharging && this.counterCharge > 0) {
            setVelX(10);
            regenCharge = 0;
            counterCharge--;
        } else if (!this.isCharging && this.counterCharge < 15) {
            counterCharge++;
            setVelX(3.5);
        } else {
            this.isCharging = false;
        }
        if (this.getMode() == Mode.JERSEY) {
            setSprite(Resource.getImage("marioStade"));
        } else if (this.getMode() == Mode.BRENNUS) {
            setSprite(Resource.getImage("marioBrennus"));
        } else if (this.getMode() == Mode.WINNER) {
            setSprite(Resource.getImage("marioDore"));
        } else {
            setSprite(getCurrentSprite());
        }
        if (getVelX() != 0) {
            long currentTime = System.currentTimeMillis();
            long timeSinceLastChange = currentTime - lastSpriteChangeTime;

            if (timeSinceLastChange > ANIMATION_TIME) {
                setCurrentSpriteIndex((getCurrentSpriteIndex() + 1) % getSprites().length);
                lastSpriteChangeTime = System.currentTimeMillis();

            }
        } else {
            setCurrentSpriteIndex(0);
        }
    }

    public void transformationAnimation() {
        if (!this.getReadyToKick()) {
            this.setVelX(3);
            this.setFalling(false);
        }
    }

    public void addCoin() {
        audioManager.playSound("piece.wav");
        coins++;
        System.out.println("Score: " + this.getScore());
        System.out.println("Coins: " + this.getCoins());
    }

    public int getCoins() {
        return coins;
    }

    public int getHp() {
        return hp;
    }

    private void setMode(Mode mode) {
        this.mode = mode;
    }

    private Mode getMode() {
        return mode;
    }

    private BufferedImage[] getSprites(){
        return sprites;
    }

    private int getCurrentSpriteIndex() {
        return currentSpriteIndex;
    }

    private void setCurrentSpriteIndex(int currentSpriteIndex) {
        this.currentSpriteIndex = currentSpriteIndex;
    }
    public boolean getReadyToKick(){
        return this.readyToKick;
    }
    public void setReadyToKick(boolean value){
        this.readyToKick= value;
    }
    public boolean getDontMove(){
        return this.dontMove;
    }
    public void setDontMove(boolean value){
        this.dontMove= value;
    }
}

