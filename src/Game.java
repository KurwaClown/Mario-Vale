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
    private final Camera camera;
    private final Mario mario;
    private MapManager mapManager;


    private enum Direction {LEFT, RIGHT, TOP, BOTTOM}

    // Management of the game and adding object on the map
    public Game() {
        this.gameState = GameState.PLAYING;
        this.camera = new Camera();
        this.mario = new Mario(50, 700);
        this.mapManager = new MapManager(camera, mario, ".\\out\\production\\T-JAV-501-TLS_7\\mapcsv\\map1.csv");
        this.userInterface = new UserInterface(mapManager.getMap());

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
            checkFlagCollisions(direction);
        }
        checkPowerupCollisions();
        checkEnnemyBlockCollisions();
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
                if (direction == Direction.LEFT) {
                    mario.setX(mario.getX() + intersection.width);
                } else if (direction == Direction.RIGHT) {
                    mario.setX(mario.getX() - intersection.width);
                } else if (direction == Direction.TOP) {
                    mario.setY(mario.getY() + intersection.height);
                    mario.setVelY(0);
                    if (block instanceof Bonus bonus) mapManager.getMap().addPowerup(bonus.getContainedPowerUp());
                    block.hit();
                } else if (direction == Direction.BOTTOM) {
                    mario.setY(mario.getY() - intersection.height);
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

    public void checkEnnemyBlockCollisions() {
        //TODO: check for all enemies
        for (Enemy enemy : mapManager.getMap().getEnemies()) {
            boolean enemyLookingRight = enemy.getVelX() > 0;
            Rectangle champiHitbox = enemyLookingRight ? getGameObjectHitbox(enemy, Direction.RIGHT, false)
                    : getGameObjectHitbox(enemy, Direction.LEFT, false);

            for (Block block : mapManager.getMap().getBlocks()) {
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

    public void reset(){
        mapManager.reset(camera);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

}