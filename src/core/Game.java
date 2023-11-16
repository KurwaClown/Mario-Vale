package core;

import gameobject.*;
import gameobject.block.Block;
import gameobject.block.Bonus;
import gameobject.character.Mario;
import gameobject.character.Projectile;
import gameobject.collectible.Coin;
import gameobject.collectible.PowerUp;
import gameobject.enemy.Enemy;
import view.*;

import javax.swing.*;
import java.awt.*;

// initializing the window and base variables
public class Game {
    private boolean isRunning = true;
    private final int TARGET_FPS = 60;
    private final static int WIDTH = 1300;
    private final static int HEIGHT = 730;
    private int coins = 0;
    private int score = 0;
    private GameState gameState;
    private final UserInterface userInterface;
    private final view.Camera camera;
    private final Mario mario;
    private MapManager mapManager;

    private view.Menu menu;

    public void startGame() {
        gameState=GameState.PLAYING;
    }


    private enum Direction {TOP, LEFT, RIGHT, BOTTOM}

    // Management of the game and adding object on the map
    public Game() {
        this.gameState = GameState.MENU;
        this.camera = new Camera();
        this.mario = new Mario(50, 540);
        this.mapManager = new MapManager(camera, mario);
        this.userInterface = new UserInterface(this);
        this.menu = new view.Menu();

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
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

        if (mario.getVelX() > 0 && mario.getX() - 600 > camera.getX())
            xOffset = mario.getVelX();

        camera.moveCam(xOffset, 0);

    }

    // Collision management
    private void checkCollisions() {
        checkForMapBoundaries();
        for (Direction direction : Direction.values()) {
            checkBlockCollisions(direction);
            checkEnemyCollisions(direction);
            checkFlagCollisions(direction);
            checkProjectileCollisions(direction);
        checkEnnemyBlockCollisions(direction);
        }
        checkPowerupCollisions();

        removeUnusedObjects();
    }

    private void checkForMapBoundaries() {
        if(mario.getVelX() < 0 && mario.getX() < camera.getX()){
            mario.setX(camera.getX());
        }
    }

    private void removeUnusedObjects() {
        getMap().getEnemies().removeIf(enemy -> enemy.getY() > 2999 || enemy.getX() < -2999);
        getMap().getBlocks().removeIf(block -> block.getY() > 2999 || block.getX() < -2999);
        getMap().getPowerUps().removeIf(powerup -> powerup.getY() > 2999 || powerup.getX() < -2999);
        getMap().getCoins().removeIf(coin -> coin.getY() > 2999 || coin.getX() < -2999);
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
                    if (direction == Direction.RIGHT || direction == Direction.LEFT) {
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
        if(!mario.isJumping()){
            mario.setFalling(true);
        }
        for (Block block : mapManager.getMap().getBlocks()) {
            Rectangle blockHitbox = getGameObjectHitbox(block, direction, true);

            if (marioHitbox.intersects(blockHitbox)) {
                Rectangle intersection = marioHitbox.intersection(blockHitbox);
                if (direction == Direction.LEFT && mario.getVelX() < 0) {
                    mario.setX(mario.getX() + intersection.width);
                } else if (direction == Direction.RIGHT && mario.getVelX() > 0) {
                    mario.setX(mario.getX() - intersection.width);
                } else if (direction == Direction.TOP && mario.getVelY() > 0) {
                    if(mario.isFalling()) break;
                    mario.setY(mario.getY() + intersection.height);
                    mario.setVelY(0);
                    if (block instanceof Bonus bonus) mapManager.getMap().addPowerup(bonus.getContainedPowerUp());
                    block.hit();
                } else if (direction == Direction.BOTTOM) {
                    if(mario.isJumping()) break;
                    mario.setY(block.getY() - mario.getSpriteDimension().height);
                    mario.setVelY(0);
                    mario.setJumping(false);
                    mario.setFalling(false);
                    mario.canForceJump = true;
                }
            }


        }
    }
    private void checkFlagCollisions(Direction direction) {
        Rectangle marioHitbox = getGameObjectHitbox(mario, direction, false);
        for (Flag flag : mapManager.getMap().getFlags()) {
            Rectangle flagHitbox = getGameObjectHitbox(flag, direction, true);

            if (marioHitbox.intersects(flagHitbox)) {
                Rectangle intersection = marioHitbox.intersection(flagHitbox);
                if (direction == Direction.LEFT) {
                    mario.setX(mario.getX() + intersection.width);
                } else if (direction == Direction.RIGHT) {
                    mario.setX(mario.getX() - intersection.width);
                    if (mario.getReadyToFly()) {
                        flag.flagBreak();
                        mario.Flag();
                        gameState = GameState.WIN;
                        userInterface.updateGame();

                    }
                } else if (direction == Direction.TOP) {
                    mario.setY(mario.getY() + intersection.height);
                    mario.setVelY(0);
                }
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
                    gameOver();
                else {
                    enemy.attacked();

                    mario.addScore(300);

                    mario.setY(mario.getY() - intersection.height);
                    mario.setFalling(false);
                    mario.setJumping(false);
                    mario.jump();
                }
            }
        }
    }

    private void checkPowerupCollisions() {
        for (PowerUp powerup : mapManager.getMap().getPowerUps()) {
            if (mario.getHitbox().intersects(powerup.getHitbox())) powerup.onTouch(mario);
        }
        for(Coin coin : mapManager.getMap().getCoins()){
            if(mario.getHitbox().intersects(coin.getHitbox())) coin.onTouch(mario);
        }

    }

    public void checkEnnemyBlockCollisions(Direction direction) {
        for (Enemy enemy : mapManager.getMap().getEnemies()) {
            if(!enemy.isJumping()){
                enemy.setFalling(true);
            }
            boolean enemyLookingRight = enemy.getVelX() > 0;
            Rectangle champiHitbox = enemyLookingRight ? getGameObjectHitbox(enemy, Direction.RIGHT, false)
                    : getGameObjectHitbox(enemy, Direction.LEFT, false);

            for (Block block : mapManager.getMap().getBlocks()) {
                Rectangle blockHitbox = getGameObjectHitbox(block, direction, true);

                if (champiHitbox.intersects(blockHitbox)) {
                    Rectangle intersection = champiHitbox.intersection(blockHitbox);
                    if (direction == Direction.LEFT && enemy.getVelX() < 0) {
                        enemy.setX(enemy.getX() + intersection.width);
                        enemy.inverseVelX();
                    } else if (direction == Direction.RIGHT && enemy.getVelX() > 0) {
                        enemy.setX(enemy.getX() - intersection.width);
                        enemy.inverseVelX();
                    } else if (direction == Direction.TOP && enemy.getVelY() > 0) {
                        if(enemy.isFalling()) break;
                        enemy.setY(enemy.getY() + intersection.height);
                        enemy.setVelY(0);
                        if (block instanceof Bonus bonus) mapManager.getMap().addPowerup(bonus.getContainedPowerUp());
                        block.hit();
                    } else if (direction == Direction.BOTTOM) {
                        enemy.setY(block.getY() - enemy.getSpriteDimension().height);
                        enemy.setVelY(0);
                        enemy.setJumping(false);
                        enemy.setFalling(false);
                    }
                }


            }
        }
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

    public void reset(){
        mapManager.reset(camera);
        gameState = GameState.PLAYING;
    }

    public Map getMap(){
        return mapManager.getMap();
    }

    public static void main(String[] args) {
        Game game = new Game();
    }


    public view.Menu getMenu(){
        return menu;
    }

}