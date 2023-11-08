import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {
    private final Game game;

    InputManager(Game game) {
        this.game = game;
    }


    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        GameState gameState = game.getGameState();


//  TODO: Implement Mario Actions
        if (gameState == GameState.PLAYING) {
            if (keyCode == KeyEvent.VK_SPACE) {
                System.out.println("Jumping");
            } else if (keyCode == KeyEvent.VK_Q) {
                System.out.println("Moving left");
            } else if (keyCode == KeyEvent.VK_D) {
                System.out.println("Moving right");
            } else if(keyCode == KeyEvent.VK_E){
                System.out.println("Attacking");
            } else if(keyCode == KeyEvent.VK_ESCAPE){
                game.pauseGame();
            }
        } else if (gameState == GameState.PAUSED) {
            if (keyCode == KeyEvent.VK_ESCAPE) {
                game.resumeGame();
            }
        } else if (gameState == GameState.MENU) {
            if (keyCode == KeyEvent.VK_ESCAPE) {
                game.quitGame();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


}
