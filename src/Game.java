import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
// initializing the window and base variables
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

// Management of the game and adding object on the map
    public Game() {
        this.gameState = GameState.PLAYING;
        this.inputManager = new InputManager(this);
        this.camera = new Camera();
        this.map = new Map(camera);
        this.userInterface = new UserInterface(map);
        this.mario = new Mario(50, 700);

        map.addMario(mario);
        for (int i = 0; i < 100; i++) {
            map.addBlocks(new Brick(200 * i, 600));
        }
        map.addChampi(new Champi(2000, 750));

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
    //Game loop 60Hz managing the lag
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
    //Logics of the Game
    private void updateGameLogic() {
        if (gameState == GameState.PLAYING) {
            checkCollisions();
            userInterface.updateGame();
            updateCamera();
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
        gameState = GameState.GAMEOVER;
        System.out.println("Game over!");
        System.out.println("Score: " + score);
        System.out.println("Coins: " + coins);
    }

    public Mario getMario() {
        return mario;
    }

    private void updateCamera() {
        double xOffset = 0;

        if (mario.getVelX() > 0 && mario.getX() - 600 > camera.getX()) xOffset = mario.getVelX();

        camera.moveCam(xOffset, 0);

    }
    // Collision management
    private void checkCollisions(){
        checkBottomCollisions();
        checkTopCollisions();
        checkLeftCollisions();
        checkRightCollisions();
    }

    private void checkRightCollisions() {
        List<Block> blocks = map.getBlocks();
        Rectangle marioRightHitbox = mario.getRightCollision();

        for (Block block : blocks) {
            Rectangle blockLeftHitbox = block.getLeftCollision();
            if (marioRightHitbox.intersects(blockLeftHitbox)) {
                Rectangle intersection = marioRightHitbox.intersection(blockLeftHitbox);
                mario.setX(mario.getX() - intersection.width); // Adjust by intersection width
                mario.setVelX(0);
            }
        }

        for (Enemy enemy : map.getChampis()) {
            Rectangle blockTopHitbox = enemy.getTopCollision();
            if (marioRightHitbox.intersects(blockTopHitbox)) {

                gameOver();
            }
        }
    }

    private void checkLeftCollisions() {
        List<Block> blocks = map.getBlocks();
        Rectangle marioLeftHitbox = mario.getLeftCollision();

        for (Block block : blocks) {
            Rectangle blockRightHitbox = block.getRightCollision();
            if (marioLeftHitbox.intersects(blockRightHitbox)) {
                Rectangle intersection = marioLeftHitbox.intersection(blockRightHitbox);
                mario.setX(mario.getX() + intersection.width); // Adjust by intersection width
                mario.setVelX(0);
            }
        }

        for (Enemy enemy : map.getChampis()) {
            Rectangle blockTopHitbox = enemy.getTopCollision();
            if (marioLeftHitbox.intersects(blockTopHitbox)) {
                gameOver();
            }
        }
    }


    private void checkTopCollisions() {
        List<Block> blocks = map.getBlocks();
        Rectangle marioTopHitbox = mario.getTopCollision();

        for (Block block : blocks) {
            Rectangle blockBottomHitBox = block.getBottomCollision();
            if (marioTopHitbox.intersects(blockBottomHitBox)) {
                Rectangle intersection = marioTopHitbox.intersection(blockBottomHitBox);
                mario.setY(mario.getY() + intersection.height); 
                mario.setVelY(0);
                if (block instanceof Brick) {
                    Brick brick = (Brick) block;
                    brick.disappear();
                }
            }
        }
    }


    private void checkBottomCollisions() {
        List<Block> blocks = map.getBlocks();
        Rectangle marioBottomHitBox = mario.getBottomCollision();

        for (Block block : blocks) {
            Rectangle blockTopHitbox = block.getTopCollision();
            if (marioBottomHitBox.intersects(blockTopHitbox)) {
                Rectangle intersection = marioBottomHitBox.intersection(blockTopHitbox);
                mario.setY(mario.getY() - intersection.height); 
                mario.setVelY(0);

            }
        }

        for (Enemy enemy : map.getChampis()) {
            Rectangle blockTopHitbox = enemy.getTopCollision();
            if (marioBottomHitBox.intersects(blockTopHitbox)) {
                Rectangle intersection = marioBottomHitBox.intersection(blockTopHitbox);
                mario.setY(mario.getY() - intersection.height); 
                mario.setVelY(0);
                enemy.disappear();
                mario.setFalling(false);
                mario.setJumping(false);
                mario.jump();
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
    
