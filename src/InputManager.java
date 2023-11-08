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


        if (keyCode == KeyEvent.VK_ESCAPE) {
            if (gameState == GameState.PAUSED) {
                game.resumeGame();
            } else if (gameState == GameState.PLAYING) {
                game.pauseGame();
            } else if (gameState == GameState.MENU){
                game.quitGame();
            }
        }

//  TODO: Implement Mario Actions
        if (gameState == GameState.PLAYING) {
            if (keyCode == KeyEvent.VK_SPACE) {
                System.out.println("Jumping");
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
