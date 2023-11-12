import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// initializing the window and base variables
public class Game {

    private boolean isRunning = true;
    private final int targetFPS = 60;
    private final static int WIDTH = 1335;
    private final static int HEIGHT = 930;

    private int coins = 0;
    private int score = 0;

    private GameState gameState;

    private final InputManager inputManager;

    private final UserInterface userInterface;

    private Jersey jersey;

    private final Map map;

    private final Camera camera;

    private final Mario mario;


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
        map.addChampi(new Champi(800, 750));
        map.addBlocks(new Bonus(300, 600));

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

    // Game loop 60Hz
    public void gameLoop() {
        System.out.println("Running game at " + targetFPS + " FPS");
        long previousTime = System.currentTimeMillis();
        long lag = 0;
        long targetFrameTime = 1000 / targetFPS;

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
            checkCollisions();
            userInterface.updateGame();
            updateCamera();
        }
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
        System.out.println("Score: " + score);
        System.out.println("Coins: " + coins);
    }

    public void increaseCoinsCount() {
        coins++;
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    private void updateCamera() {
        double xOffset = 0;

        if (mario.getVelX() > 0 && mario.getX() - 600 > camera.getX())
            xOffset = mario.getVelX();

        camera.moveCam(xOffset, 0);

    }

    // Collision management
    private void checkCollisions() {
        checkBottomCollisions();
        checkTopCollisions();
        checkLeftCollisions();
        checkRightCollisions();
    }

    private void checkRightCollisions() {
        List<PowerUp> powerups = map.getPowerUps();
        List<Block> blocks = map.getBlocks();
        List<Champi> champis =map.getChampis();
        Rectangle marioRightHitbox = mario.getRightCollision();

        for (Block block : blocks) {
            Rectangle blockLeftHitbox = block.getLeftCollision();
            if (marioRightHitbox.intersects(blockLeftHitbox)) {
                Rectangle intersection = marioRightHitbox.intersection(blockLeftHitbox);
                mario.setX(mario.getX() - intersection.width);
                mario.setVelX(0);
            }
                for (Champi champi : champis) {
                Rectangle champiRightHitBox = champi.getRightCollision();                                                  
                if (champiRightHitBox.intersects(blockLeftHitbox)) {
                    Rectangle intersection1 = champiRightHitBox.intersection(blockLeftHitbox);
                    champi.setX(champi.getX() - intersection1.width);
                    champi.setVelX(champi.getVelX() * (-1));
                }
            }

        }

        for (Enemy enemy : map.getChampis()) {
            Rectangle blockTopHitbox = enemy.getTopCollision();
            if (marioRightHitbox.intersects(blockTopHitbox)) {

                gameOver();
            }
        }
        for (PowerUp powerup : powerups) {
            Rectangle powerupLeftHitBox = powerup.getLeftCollision();
            if (marioRightHitbox.intersects(powerupLeftHitBox)) {
                Rectangle intersection1 = marioRightHitbox.intersection(powerupLeftHitBox);
                mario.setY(mario.getY() + intersection1.height);
                if (powerup instanceof Jersey) {
                    Jersey jersey = (Jersey) powerup;
                    mario.setIsRugbymanTrue();
                    mario.updateImage();
                    Timer timer = new Timer(12000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mario.setIsRugbymanFalse();
                            mario.updateImage();
                            ((Timer) e.getSource()).stop();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();

                }
            }
        }

    }

    private void checkLeftCollisions() {
        List<Block> blocks = map.getBlocks();
        List<PowerUp> powerups = map.getPowerUps();
        List<Champi> champis = map.getChampis();
        Rectangle marioLeftHitBox = mario.getLeftCollision();

        for (Block block : blocks) {
            Rectangle blockRightHitbox = block.getRightCollision();
            if (marioLeftHitBox.intersects(blockRightHitbox)) {
                Rectangle intersection = marioLeftHitBox.intersection(blockRightHitbox);
                mario.setX(mario.getX() + intersection.width);
                mario.setVelX(0);
            }
            for (Champi champi : champis) {
                Rectangle champiLeftHitBox = champi.getLeftCollision();                                                  
                if (champiLeftHitBox.intersects(blockRightHitbox)) {
                    Rectangle intersection1 = champiLeftHitBox.intersection(blockRightHitbox);
                    champi.setX(champi.getX() + intersection1.width);
                    champi.setVelX(champi.getVelX() * (-1));
                }
            }
        }

        for (Enemy enemy : map.getChampis()) {
            Rectangle blockTopHitbox = enemy.getTopCollision();
            if (marioLeftHitBox.intersects(blockTopHitbox)) {
                gameOver();
            }
        }
        for (PowerUp powerup : powerups) {
            Rectangle powerupRightHitBox = powerup.getRightCollision();
            if (marioLeftHitBox.intersects(powerupRightHitBox)) {
                Rectangle intersection1 = marioLeftHitBox.intersection(powerupRightHitBox);
                mario.setY(mario.getY() + intersection1.height);
                if (powerup instanceof Jersey) {
                    Jersey jersey = (Jersey) powerup;
                    mario.setIsRugbymanTrue();
                    mario.updateImage();
                    Timer timer = new Timer(12000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mario.setIsRugbymanFalse();
                            mario.updateImage();
                            ((Timer) e.getSource()).stop();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();

                }
            }
        }
    }

    private void checkTopCollisions() {
        List<Block> blocks = map.getBlocks();
        List<PowerUp> powerups = map.getPowerUps();
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
                if (block instanceof Bonus) {
                    Bonus bonus = (Bonus) block;
                    if (this.jersey == null) {
                        this.jersey = new Jersey(1000, 10000); // Set the initial position off-screen or hidden
                    }
                    bonus.displayBonus(this.jersey);
                    map.addPowerup(this.jersey);
                }
                for (PowerUp powerup : powerups) {
                    Rectangle powerupBottomHitBox = powerup.getBottomCollision();
                    if (marioTopHitbox.intersects(powerupBottomHitBox)) {
                        Rectangle intersection1 = marioTopHitbox.intersection(powerupBottomHitBox);
                        mario.setY(mario.getY() + intersection1.height);
                        if (powerup instanceof Jersey) {
                            Jersey jersey = (Jersey) powerup;
                            mario.setIsRugbymanTrue();
                            mario.updateImage();
                            Timer timer = new Timer(12000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    mario.setIsRugbymanFalse();
                                    mario.updateImage();
                                    ((Timer) e.getSource()).stop();
                                }
                            });
                            timer.setRepeats(false);
                            timer.start();

                        }
                    }
                }
            }
        }
    }

    private void checkBottomCollisions() {
        List<Block> blocks = map.getBlocks();
        List<PowerUp> powerups = map.getPowerUps();
        Rectangle marioBottomHitBox = mario.getBottomCollision();

        for (Block block : blocks) {
            Rectangle blockTopHitbox = block.getTopCollision();
            if (marioBottomHitBox.intersects(blockTopHitbox)) {
                Rectangle intersection = marioBottomHitBox.intersection(blockTopHitbox);
                mario.setY(mario.getY() - intersection.height);
                mario.setVelY(0);
                mario.setFalling(false);
                mario.setJumping(false);

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
        for (PowerUp powerup : powerups) {
            Rectangle powerupTopHitBox = powerup.getTopCollision();
            if (marioBottomHitBox.intersects(powerupTopHitBox)) {
                Rectangle intersection = marioBottomHitBox.intersection(powerupTopHitBox);
                mario.setY(mario.getY() + intersection.height);
                if (powerup instanceof Jersey) {
                    Jersey jersey = (Jersey) powerup;
                    mario.setIsRugbymanTrue();
                    mario.updateImage();
                    Timer timer = new Timer(12000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mario.setIsRugbymanFalse();
                            mario.updateImage();
                            ((Timer) e.getSource()).stop();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();

                }
            }
        }
    }

    public Mario getMario() {
        return mario;
    }
    public GameState getGameState() {
        return gameState;
    }
    public static void main(String[] args) {
        Game game = new Game();
    }
}
