import javax.swing.*;
import java.awt.*;

// initializing the window and base variables
public class Game {
    private boolean isRunning = true;
    private final int TARGET_FPS = 60;
    private final static int WIDTH = 1335;
    private final static int HEIGHT = 930;
    private int coins = 0;
    private int score = 0;
    private GameState gameState;
    private final UserInterface userInterface;
    private final Map map;
    private final Camera camera;
    private final Mario mario;

    private enum Direction {LEFT, RIGHT, TOP, BOTTOM}

    // Management of the game and adding object on the map
    public Game() {
        this.gameState = GameState.PLAYING;
        this.camera = new Camera();
        this.map = new Map(camera);
        this.userInterface = new UserInterface(map);
        this.mario = new Mario(50, 700);

        map.addMario(mario);
        for (int i = 0; i < 100; i++) {
            map.addBlocks(new Brick(200 * i, 600));
        }
        map.addEnemy(new Champi(800, 750));
        map.addBlocks(new Bonus(1000, 600, new Jersey()));
        map.addBlocks(new Brick(600, 750));
        map.addBlocks(new Brick(1000, 750));

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
        for (Direction direction : Direction.values()) {
            checkBlockCollisions(direction);
            checkEnemyCollisions(direction);
            checkPowerupCollisions(direction);
        }
        checkEnnemyBlockCollisions();
    }

    private void checkBlockCollisions(Direction direction) {
        Rectangle marioHitbox = getGameObjectHitbox(mario, direction, false);

        for (Block block : map.getBlocks()) {
            Rectangle blockHitbox = getGameObjectHitbox(block, direction, true);

            if (marioHitbox.intersects(blockHitbox)) {
                Rectangle intersection = marioHitbox.intersection(blockHitbox);
                if (direction == Direction.LEFT) {
                    mario.setX(mario.getX() + intersection.width);
                } else if (direction == Direction.RIGHT) {
                    mario.setX(mario.getX() - intersection.width);
                } else if (direction == Direction.TOP) {
                    mario.setY(mario.getY() + intersection.height);
                    mario.setVelY(0);
                    if (block instanceof Bonus bonus) map.addPowerup(bonus.getContainedPowerUp());
                    block.hit();
                } else {
                    mario.setY(mario.getY() - intersection.height);
                    mario.setVelY(0);
                    mario.setFalling(false);
                    mario.setJumping(false);
                }
            }


        }
    }

    private void checkEnemyCollisions(Direction direction) {
        Rectangle marioHitbox = getGameObjectHitbox(mario, direction, false);

        //TODO: check for all enemies
        for (Enemy enemy : map.getEnemies()) {
            Rectangle enemyHitbox = getGameObjectHitbox(enemy, direction, true);
            if (marioHitbox.intersects(enemyHitbox)) {
                Rectangle intersection = marioHitbox.intersection(enemyHitbox);
                if (direction == Direction.TOP || direction == Direction.LEFT || direction == Direction.RIGHT)
                    gameOver();
                else {
                    mario.setY(mario.getY() - intersection.height);
                    enemy.disappear();
                    mario.setFalling(false);
                    mario.setJumping(false);
                    mario.jump();
                }
            }
        }
    }

    private void checkPowerupCollisions(Direction direction) {
        Rectangle marioHitbox = getGameObjectHitbox(mario, direction, false);

        for (PowerUp powerup : map.getPowerUps()) {
            Rectangle powerUpHitbox = getGameObjectHitbox(powerup, direction, true);

            if (marioHitbox.intersects(powerUpHitbox)) {
                if (powerup instanceof Jersey) {
                    mario.setIsRugbyman(true);
                    mario.updateImage();
                    Timer timer = new Timer(12000, e -> {
                        mario.setIsRugbyman(false);
                        mario.updateImage();
                        ((Timer) e.getSource()).stop();
                    });
                    timer.setRepeats(false);
                    timer.start();

                }
            }
        }
    }

    public void checkEnnemyBlockCollisions() {
        //TODO: check for all enemies
        for (Enemy enemy : map.getEnemies()) {
            boolean enemyLookingRight = enemy.getVelX() > 0;
            Rectangle champiHitbox = enemyLookingRight ? getGameObjectHitbox(enemy, Direction.RIGHT, false)
                    : getGameObjectHitbox(enemy, Direction.LEFT, false);

            for (Block block : map.getBlocks()) {
                Rectangle blockHitbox = enemyLookingRight ? getGameObjectHitbox(block, Direction.RIGHT, true)
                        : getGameObjectHitbox(block, Direction.LEFT, true);

                if (champiHitbox.intersects(blockHitbox)) {
                    Rectangle intersection = champiHitbox.intersection(blockHitbox);
                    double newX = enemyLookingRight ? enemy.getX() - intersection.width : enemy.getX() + intersection.width;
                    enemy.setX(newX);
                    enemy.inverseVelX();
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

    public static void main(String[] args) {
        Game game = new Game();
    }
}