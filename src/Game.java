import javax.swing.*;
import java.awt.*;

public class Game {

    private boolean isRunning = true;
    private final int targetFPS = 60;
    private final long targetFrameTime = 1000 / targetFPS;

    private final static int WIDTH = 1268;
    private final static int HEIGHT = 708;

    private int coins = 0;
    private int score = 0;

    private GameState gameState;

    private InputManager inputManager;

    private UserInterface userInterface;

    private Camera camera;

    private Mario mario;


    public Game() {
        this.gameState = GameState.PLAYING;
        this.inputManager = new InputManager(this);
        this.userInterface = new UserInterface(this, WIDTH, HEIGHT);
        this.camera = new Camera();
        this.mario = new Mario();


        JFrame frame = new JFrame("Mario'Vale");
        frame.add(userInterface);
        frame.addKeyListener(inputManager);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

//    public void gameLoop(){
//        System.out.println("Running game at " + targetFPS + " FPS");
//        long previousTime = System.currentTimeMillis();
//        long lag = 0;
//
//        while (isRunning) {
//            long currentTime = System.currentTimeMillis();
//            long deltaTime = currentTime - previousTime;
//            previousTime = currentTime;
//
//            lag += deltaTime;
//
//            while (lag >= targetFrameTime) {
//                updateGameLogic();
//                lag -= targetFrameTime;
//            }
//
//            long sleepTime = targetFrameTime - lag;
//
//            if (sleepTime > 0) {
//                try {
//                    Thread.sleep(sleepTime);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private void updateGameLogic() {
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

}
