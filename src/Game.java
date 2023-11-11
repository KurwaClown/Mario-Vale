import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private boolean isRunning = true;
    private final int targetFPS = 60;
    private final long targetFrameTime = 1000 / targetFPS;

    private final static int WIDTH = 1335;
    private final static int HEIGHT = 930;

    private int coins = 0;
    private int score = 0;

    private GameState gameState;

    private InputManager inputManager;

    private UserInterface userInterface;

    private Map map;

    private Camera camera;

    private Mario mario;


    public Game() {
        this.gameState = GameState.PLAYING;
        this.inputManager = new InputManager(this);
        this.map = new Map();
        this.userInterface = new UserInterface(map);
        this.camera = new Camera();
        this.mario = new Mario(50, 700);

        map.addMario(mario);
        map.addChampi(new Champi(100, 700));

        JFrame frame = new JFrame("Mario'Vale");
        frame.add(userInterface);
        frame.addKeyListener(inputManager);
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

    public void gameLoop(){
        System.out.println("Running game at " + targetFPS + " FPS");
        long previousTime = System.currentTimeMillis();
        long lag = 0;

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

    private void updateGameLogic() {
        if (gameState == GameState.PLAYING) {
            checkCollisions();
            userInterface.updateGame();
        }
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
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

    public void increaseCoinsCount(){
        coins++;
    }

    public void increaseScore(int amount){
        score+=amount;
    }

    public void gameOver(){
        System.out.println("Game over!");
        System.out.println("Score: " + score);
        System.out.println("Coins: " + coins);
    }

    public Mario getMario() {
        return mario;
    }


    private void checkCollisions(){
        checkBottomCollisions();
        checkTopCollisions();
        checkLeftCollisions();
        checkRightCollisions();
    }

    private void checkRightCollisions() {
        List<Block> bricks = map.getBlocks();

        Rectangle marioRightHitbox = mario.getRightCollision();

        for (Block brick : bricks) {
            Rectangle blockLeftHitbox = brick.getLeftCollision();
            if (marioRightHitbox.intersects(blockLeftHitbox)) {
                mario.setX(brick.getX() - mario.getSpriteDimension().width - 1); // Adding one to be over the block
                mario.setVelX(0);
            }
        }
    }

    private void checkLeftCollisions() {
        List<Block> bricks = map.getBlocks();

        Rectangle marioLeftHitbox = mario.getLeftCollision();

        for (Block brick : bricks) {
            Rectangle blockRightHitbox = brick.getRightCollision();
            if (marioLeftHitbox.intersects(blockRightHitbox)) {
                mario.setX(brick.getX() + brick.getSpriteDimension().width + 1); // Adding one to be over the block
                mario.setVelX(0);
            }
        }
    }

    private void checkTopCollisions() {
        List<Block> bricks = map.getBlocks();

        Rectangle marioTopHitbox = mario.getTopCollision();

        for (Block brick : bricks) {
            Rectangle blockBottomHitBox = brick.getBottomCollision();
            if (marioTopHitbox.intersects(blockBottomHitBox)) {
                mario.setY(brick.getY() + mario.getSpriteDimension().height + 1); // Adding one to be over the block
                mario.setVelY(0);
            }
        }
    }

    private void checkBottomCollisions() {
        List<Block> bricks = map.getBlocks();

        Rectangle marioBottomHitBox = mario.getBottomCollision();

        for (Block brick : bricks) {
            Rectangle blockTopHitbox = brick.getTopCollision();
            if (marioBottomHitBox.intersects(blockTopHitbox)) {
                mario.setY(brick.getY() - mario.getSpriteDimension().height - 1); // Adding one to be over the block
                mario.setVelY(0);
            }
        }

        //TODO: Check collisions with ennemies and map ground
    }
    public static void main(String[] args) {
        Game game = new Game();
    }
}
    
