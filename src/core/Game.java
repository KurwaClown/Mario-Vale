package core;

import gameobject.Flag;
import gameobject.GameObject;
import gameobject.block.Block;
import gameobject.block.Bonus;
import gameobject.block.Pipe;
import gameobject.character.Mario;
import gameobject.character.Projectile;
import gameobject.collectible.Coin;
import gameobject.collectible.PowerUp;
import gameobject.enemy.Enemy;
import gameobject.enemy.Missile;
import view.AudioManager;
import view.Camera;
import view.Map;
import view.UserInterface;

import javax.swing.*;
import java.awt.*;

// initializing the window and base variables
public class Game {
    private boolean isRunning = true;
    private final static int WIDTH = 1300;
    private final static int HEIGHT = 730;
    private GameState gameState;

    private final AudioManager audioManager = new AudioManager();
    private final UserInterface userInterface;
    private final view.Camera camera;
    private final Mario mario;
    private final MapManager mapManager;

    private final view.Menu menu;
    private int numClicks;

    public void startGame() {
        gameState = GameState.PLAYING;
    }

    public int getFlagCount() {
        return numClicks;
    }


    private enum Direction {BOTTOM, TOP, LEFT, RIGHT }

    // Management of the game and adding object on the map
    public Game() {
        this.gameState = GameState.MENU;
        this.camera = new Camera();
        this.mario = new Mario();
        this.mapManager = new MapManager(camera, mario);
        this.userInterface = new UserInterface(this);
        this.menu = new view.Menu(this.getCamera(),this);

        mapManager.loadMapFromCSV();

        JFrame frame = new JFrame("Mario'Vale");
        frame.add(userInterface);
        frame.addKeyListener(new InputManager(this));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);

        Thread gameThread = new Thread(this::gameLoop);

        gameThread.start();
    }

    // Game loop 60Hz
    public void gameLoop() {
        int TARGET_FPS = 60;

        System.out.println("Running game at " + TARGET_FPS + " FPS");

        long previousTime = System.currentTimeMillis();
        long lag = 0;
        long targetFrameTime = 1000 / TARGET_FPS;

        while (isRunning) {
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - previousTime;
            previousTime = currentTime;

            lag += deltaTime;

            while (lag >= targetFrameTime) {
                updateGameLogic();
                lag -= targetFrameTime;
            }

            long sleepTime = targetFrameTime - lag;

            if (sleepTime > 0) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.out.println("Error while sleeping thread");

                }
            }
        }
    }

    // Logics of the Game
    private void updateGameLogic() {
        if (gameState == GameState.PLAYING) {
            userInterface.updateGame();
            checkCollisions();
            updateCamera();
            audioManager.playLoopSound("./src/resource/sound/playingmusic.wav");
        }
        userInterface.repaint();
    }

    public void pauseGame() {
        this.gameState = GameState.PAUSED;
        System.out.println("Game paused");
    }

    public void resumeGame() {
        this.gameState = GameState.PLAYING;
        System.out.println("Game resumed");
    }

    public void quitGame() {
        isRunning = false;
    }

    public void gameOver() {
        gameState = GameState.GAMEOVER;
        System.out.println("Game over!");
        System.out.println("Score: " + mario.getScore());
        System.out.println("Coins: " + mario.getCoins());
    }


    private void updateCamera() {
        double xOffset = 0;

        if (mario.getVelX() > 0 && mario.getX() - 600 > camera.getX()) xOffset = mario.getVelX();

        camera.moveCam(xOffset, 0);
    }

    public void nextLevel(){
        mapManager.goToNextLevel();
        gameState= GameState.PLAYING;
    }
    public void victory(){
        gameState = GameState.WIN;
    }

    // Collision management
    private void checkCollisions() {

        for (Direction direction : Direction.values()) {
            checkBlockCollisions(direction);
            checkEnemyCollisions(direction);
            checkFlagCollisions(direction);
            checkProjectileCollisions(direction);
            checkEnemyBlockCollisions(direction);
        }
        checkPowerupCollisions();
        checkForMapBoundaries();
        mapManager.removeUnusedObjects();
    }

    public void toggleDebugMode(){
        this.mapManager.toggleDebugMode();
    }

    private void checkForMapBoundaries() {
        if (mario.getX() < camera.getX()) {
            mario.setX(camera.getX());
        }
        if(mario.getY() > camera.getY() + userInterface.getHeight()){
            gameOver();
        }
    }


    private void checkProjectileCollisions(Direction direction) {
        for (Projectile projectile : getMap().getProjectiles()) {
            for (Enemy enemy : getMap().getEnemies()) {
                if (projectile.getHitbox().intersects(enemy.getHitbox())) {
                    enemy.disappear();
                    projectile.disappear();
                    mario.addScore(300);
                }
            }

            Rectangle projectileHitbox = getGameObjectHitbox(projectile, direction, false);

            for (Block block : mapManager.getMap().getBlocks()) {
                Rectangle blockHitbox = getGameObjectHitbox(block, direction, true);

                if (projectileHitbox.intersects(blockHitbox)) {
                    Rectangle intersection = projectileHitbox.intersection(blockHitbox);
                    if (direction == Direction.RIGHT || direction == Direction.LEFT || direction == Direction.TOP) {
                        projectile.disappear();
                    } else if (direction == Direction.BOTTOM) {
                        projectile.setY(projectile.getY() - intersection.height);
                        projectile.setVelY(7.5);
                        projectile.setJumping(true);
                        projectile.setFalling(false);
                    }
                }


            }
        }
    }

    private void checkBlockCollisions(Direction direction) {
        Rectangle marioHitbox = getGameObjectHitbox(mario, direction, false);
        if (!mario.isJumping()) {
            mario.setFalling(true);
        }
        for (Block block : mapManager.getMap().getBlocks()) {
            Rectangle blockHitbox = getGameObjectHitbox(block, direction, true);

            if (marioHitbox.intersects(blockHitbox)) {
                Rectangle intersection = marioHitbox.intersection(blockHitbox);
                if (direction == Direction.LEFT) {
                    mario.setX(block.getX() + block.getSpriteDimension().width+1);
                } else if (direction == Direction.RIGHT) {
                    mario.setX(block.getX() - mario.getSpriteDimension().getWidth());
                } else if (direction == Direction.TOP) {
                    if (mario.isFalling()) break;
                    mario.setY(mario.getY() + intersection.height);
                    mario.setVelY(0);
                    if (block instanceof Bonus bonus) mapManager.getMap().addCollectible(bonus.getContainedCollectible());
                    block.hit();
                } else if (direction == Direction.BOTTOM) {
                    if (mario.isJumping()) break;
                    mario.setY(block.getY() - mario.getSpriteDimension().height + 1);
                    mario.setVelY(0);
                    mario.setFalling(false);
                    mario.canJump = true;
                }
            }


        }
    }

    private void checkFlagCollisions(Direction direction) {
        if(gameState == GameState.FLAG) return;
        Rectangle marioHitbox = getGameObjectHitbox(mario, direction, false);
        Flag flag = mapManager.getMap().getFlag();
            Rectangle flagHitbox = getGameObjectHitbox(flag, direction, true);

            if (marioHitbox.intersects(flagHitbox)) {
                Rectangle intersection = marioHitbox.intersection(flagHitbox);
                if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                    mario.setX(mario.getX() - intersection.width);
                    flag.flagBreak();
                    gameState = GameState.FLAG;
                    flagGame();
                    userInterface.updateGame();
                } else if (direction == Direction.TOP) {
                    mario.setY(mario.getY() + intersection.height);
                    mario.setVelY(0);
                }


        }
    }

    private void checkEnemyCollisions(Direction direction) {
        Rectangle marioHitbox = getGameObjectHitbox(mario, direction, false);

        for (Enemy enemy : mapManager.getMap().getEnemies()) {
            Rectangle enemyHitbox = getGameObjectHitbox(enemy, direction, true);
            if (marioHitbox.intersects(enemyHitbox)) {
                Rectangle intersection = marioHitbox.intersection(enemyHitbox);
                if (direction == Direction.TOP || direction == Direction.LEFT || direction == Direction.RIGHT)

                    if (mario.getVelX() > enemy.getVelX() && mario.getVelX()>5) {
                        enemy.disappear();
                    } else {
                        System.out.println(direction);
                        if(mario.getHp() > 1) enemy.attacked();
                        mario.attacked();
                    }
                else {
                    enemy.attacked();
                    mario.addScore(300);
                    mario.setY(mario.getY() - intersection.height);
                    mario.setFalling(false);
                    mario.setJumping(false);
                    mario.canJump = true;
                    mario.jump();
                }
                if (mario.getHp() <= 0) {
                    gameOver();
                    audioManager.playSound("game-over.wav");
                }
            }
        }
    }

    private void checkPowerupCollisions() {
        for (PowerUp powerup : mapManager.getMap().getPowerUps()) {
            if (mario.getHitbox().intersects(powerup.getHitbox())) powerup.onTouch(mario);
        }
        for (Coin coin : mapManager.getMap().getCoins()) {
            if (mario.getHitbox().intersects(coin.getHitbox())) coin.onTouch(mario);
        }

    }

    private void checkEnemyBlockCollisions(Direction direction) {
        for (Enemy enemy : mapManager.getMap().getEnemies()) {
            if (!enemy.isJumping() && !(enemy instanceof Missile)) {
                enemy.setFalling(true);
            }
            Rectangle enemyHitbox = getGameObjectHitbox(enemy, direction, false);

            for (Block block : mapManager.getMap().getBlocks()) {
                Rectangle blockHitbox = getGameObjectHitbox(block, direction, true);

                if (enemyHitbox.intersects(blockHitbox)) {
                    Rectangle intersection = enemyHitbox.intersection(blockHitbox);
                    if (direction == Direction.LEFT && !enemy.isLookingRight()) {
                        enemy.setX(enemy.getX() + enemy.getVelX());
                        enemy.inverseVelX();
                    } else if (direction == Direction.RIGHT && enemy.isLookingRight()) {
                        enemy.setX(enemy.getX() - enemy.getVelX());
                        enemy.inverseVelX();
                    } else if (direction == Direction.TOP && enemy.isJumping()) {
                        if (enemy.isFalling()) break;
                        enemy.setY(enemy.getY() + intersection.height);
                        enemy.setVelY(0);
                        if (block instanceof Bonus bonus) mapManager.getMap().addCollectible(bonus.getContainedCollectible());
                        block.hit();
                    } else if (direction == Direction.BOTTOM && enemy.isFalling()) {
                        if(block instanceof Pipe) System.out.println(enemy + " from bottom");
                        enemy.setY(block.getY() - enemy.getSpriteDimension().height);
                        enemy.setVelY(0);
                        enemy.setJumping(false);
                        enemy.setFalling(false);
                    }
                }
            }
        }
    }

    public void flagGame() {
        Timer timer = new Timer(5000, e -> {
            mario.setX(mario.getX() + numClicks * 10);
            System.out.println("Number of presses : " + numClicks);
            victory();
            audioManager.playSound("niveau-termine.wav");
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void increaseNumClicks() {
        numClicks++;
    }

    private Rectangle getGameObjectHitbox(GameObject object, Direction direction, boolean isOpposite) {
        return switch (direction) {
            case LEFT -> isOpposite ? object.getRightCollision() : object.getLeftCollision();
            case RIGHT -> isOpposite ? object.getLeftCollision() : object.getRightCollision();
            case BOTTOM -> isOpposite ? object.getTopCollision() : object.getBottomCollision();
            case TOP -> isOpposite ? object.getBottomCollision() : object.getTopCollision();
        };
    }

    public Mario getMario() {
        return mario;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void reset() {
        mapManager.reset(camera);
        gameState = GameState.PLAYING;
    }

    public Map getMap() {
        return mapManager.getMap();
    }

    public static void main(String[] args) {
        new Game();
    }


    public view.Menu getMenu() {
        return menu;
    }

    public view.Camera getCamera() {
        return camera;
    }

}