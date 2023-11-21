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
import gameobject.enemy.Canon;
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

    private int score;

    private final AudioManager audioManager = new AudioManager();
    private double previousCameraX = 0;
    private final UserInterface userInterface;
    private final view.Camera camera;
    private final Mario mario;
    private final MapManager mapManager;

    private final view.Menu menu;
    private int numClicks;


    public void startGame() {
        setGameState(GameState.PLAYING);
    }

    public int getFlagCount() {
        return numClicks;
    }


    private enum Direction {BOTTOM, TOP, LEFT, RIGHT}

    // Management of the game and adding object on the map
    public Game() {
        setGameState(GameState.MENU);
        this.camera = new Camera();
        this.mario = new Mario();
        this.menu = new view.Menu(getCamera(), this);
        this.mapManager = new MapManager(getCamera(), getMario(), menu);
        this.userInterface = new UserInterface(this);

        JFrame frame = new JFrame("Mario'Vale");
        frame.add(getUI());
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
        double currentCameraX = getCamera().getX();
        double cameraOffset = currentCameraX - previousCameraX;

        if (getGameState() == GameState.PLAYING) {
            getAudioManager().playLoopSound("./src/resource/sound/playingmusic.wav");
            getUI().updateGame();
            checkCollisions();
            updateCamera();

            if (getMenu().isEndurance()) {
                moveCanonsWithCamera(cameraOffset);
                getMapManager().generateGroundIfNecessary(getCamera());
                if (getMario().getY() < 0) {
                    getMario().setY(0);
                }
            }
        }


        previousCameraX = currentCameraX;
        getUI().repaint();
    }

    public void pauseGame() {
        setGameState(GameState.PAUSED);
        System.out.println("Game paused");
    }

    public void resumeGame() {
        setGameState(GameState.PLAYING);
        System.out.println("Game resumed");
    }

    public void quitGame() {
        isRunning = false;
    }

    public void gameOver() {
        audioManager.playSound("game-over.wav");
        setGameState(GameState.GAMEOVER);
        System.out.println("Game over!");
        System.out.println("Score: " + getMario().getScore());
        System.out.println("Coins: " + getMario().getCoins());
    }


    private void updateCamera() {
        double xOffset = 0;

        if (getMario().getVelX() > 0 && getMario().getX() - 600 > getCamera().getX()) xOffset = getMario().getVelX();

        getCamera().moveCam(xOffset, 0);
    }

    public void nextLevel() {
        getMapManager().goToNextLevel();
        setGameState(GameState.PLAYING);
    }

    public void victory() {
        setGameState(GameState.WIN);
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
        getMapManager().removeUnusedObjects();
    }

    public void toggleDebugMode() {
        this.getMapManager().toggleDebugMode();
    }

    private void checkForMapBoundaries() {
        if (getMario().getX() < getCamera().getX()) {
            getMario().setX(getCamera().getX());
        }
        if (getMario().getY() > getCamera().getY() + getUI().getHeight()) {
            gameOver();
        }
    }


    private void checkProjectileCollisions(Direction direction) {
        for (Projectile projectile : getMap().getProjectiles()) {
            for (Enemy enemy : getMap().getEnemies()) {
                if (projectile.getHitbox().intersects(enemy.getHitbox())) {
                    enemy.disappear();
                    projectile.disappear();
                    getMario().addScore(300);
                }
            }

            Rectangle projectileHitbox = getGameObjectHitbox(projectile, direction, false);

            for (Block block : getMapManager().getMap().getBlocks()) {
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
        Rectangle marioHitbox = getGameObjectHitbox(getMario(), direction, false);
        if (!getMario().isJumping()) {
            getMario().setFalling(true);
        }
        for (Block block : getMapManager().getMap().getBlocks()) {
            Rectangle blockHitbox = getGameObjectHitbox(block, direction, true);

            if (marioHitbox.intersects(blockHitbox)) {
                Rectangle intersection = marioHitbox.intersection(blockHitbox);
                if (direction == Direction.LEFT) {
                    getMario().setX(block.getX() + block.getSpriteDimension().width + 1);
                } else if (direction == Direction.RIGHT) {
                    getMario().setX(block.getX() - getMario().getSpriteDimension().getWidth());
                } else if (direction == Direction.TOP) {
                    if (getMario().isFalling()) break;
                    getMario().setY(getMario().getY() + intersection.height);
                    getMario().setVelY(0);
                    if (block instanceof Bonus bonus)
                        getMapManager().getMap().addCollectible(bonus.getContainedCollectible());
                    block.hit();
                } else if (direction == Direction.BOTTOM) {
                    if (getMario().isJumping()) break;
                    getMario().setY(block.getY() - getMario().getSpriteDimension().height + 1);
                    getMario().setVelY(0);
                    getMario().setFalling(false);
                    getMario().canJump = true;
                }
            }


        }
    }

    private void checkFlagCollisions(Direction direction) {
        if (getGameState() == GameState.FLAG) return;
        Rectangle marioHitbox = getGameObjectHitbox(getMario(), direction, false);
        Flag flag = getMapManager().getMap().getFlag();
        if (flag == null) return;
        Rectangle flagHitbox = getGameObjectHitbox(flag, direction, true);

        if (marioHitbox.intersects(flagHitbox)) {
            Rectangle intersection = marioHitbox.intersection(flagHitbox);
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                getMario().setX(getMario().getX() - intersection.width);
                flag.flagBreak();
                setGameState(GameState.FLAG);
                flagGame();
                getUI().updateGame();
            } else if (direction == Direction.TOP) {
                getMario().setY(getMario().getY() + intersection.height);
                getMario().setVelY(0);
            }


        }
    }

    private void checkEnemyCollisions(Direction direction) {
        Rectangle marioHitbox = getGameObjectHitbox(getMario(), direction, false);

        for (Enemy enemy : getMapManager().getMap().getEnemies()) {
            Rectangle enemyHitbox = getGameObjectHitbox(enemy, direction, true);
            if (marioHitbox.intersects(enemyHitbox)) {
                Rectangle intersection = marioHitbox.intersection(enemyHitbox);
                if (direction == Direction.TOP || direction == Direction.LEFT || direction == Direction.RIGHT)

                    if (getMario().getVelX() > enemy.getVelX() && getMario().getVelX() > 5) {
                        enemy.disappear();
                    } else {
                        System.out.println(direction);
                        if (getMario().getHp() > 1) enemy.attacked();
                        getMario().attacked();
                    }
                else {
                    enemy.attacked();
                    getMario().addScore(300);
                    getMario().setY(getMario().getY() - intersection.height);
                    getMario().setFalling(false);
                    getMario().setJumping(false);
                    getMario().canJump = true;
                    getMario().jump();
                }
                if (getMario().getHp() <= 0) {
                    gameOver();
                }
            }
        }
    }

    private void checkPowerupCollisions() {
        for (PowerUp powerup : getMapManager().getMap().getPowerUps()) {
            if (getMario().getHitbox().intersects(powerup.getHitbox())) powerup.onTouch(getMario());
        }
        for (Coin coin : getMapManager().getMap().getCoins()) {
            if (getMario().getHitbox().intersects(coin.getHitbox())) coin.onTouch(getMario());
        }

    }

    private void checkEnemyBlockCollisions(Direction direction) {
        for (Enemy enemy : getMapManager().getMap().getEnemies()) {
            if (!enemy.isJumping() && !(enemy instanceof Missile) && !(enemy instanceof Canon)) {
                enemy.setFalling(true);
            }
            Rectangle enemyHitbox = getGameObjectHitbox(enemy, direction, false);

            for (Block block : getMapManager().getMap().getBlocks()) {
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
                        if (block instanceof Bonus bonus)
                            getMapManager().getMap().addCollectible(bonus.getContainedCollectible());
                        block.hit();
                    } else if (direction == Direction.BOTTOM && enemy.isFalling()) {
                        if (block instanceof Pipe) System.out.println(enemy + " from bottom");
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
            getMario().setX(getMario().getX() + numClicks * 10);
            System.out.println("Number of presses : " + numClicks);
            gameState = GameState.TRANSFORMATION;
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

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void reset() {
        getMapManager().reset(getCamera());
        setGameState(GameState.PLAYING);
    }

    public Map getMap() {
        return getMapManager().getMap();
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

    public MapManager getMapManager() {
        return mapManager;
    }

    public void moveCanonsWithCamera(double offset) {
        for (Enemy enemy : getMapManager().getMap().getEnemies()) {
            if (enemy instanceof Canon) {
                enemy.setX(enemy.getX() + offset);
            }
        }
    }

    public void resetPreviousCameraX() {
        previousCameraX = 0;
    }

    public AudioManager getAudioManager(){
        return this.audioManager;
    }

    public UserInterface getUI() {
        return userInterface;
    }

    public int Score(){
        if(menu.isEndurance()){
            return score = (int)camera.getX()*10;
        } else{
            return score = mario.getScore();
        }
    }

}