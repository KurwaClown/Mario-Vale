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
                game.getMario().jump();
            } else if (keyCode == KeyEvent.VK_Q) {
                game.getMario().move(false);
            } else if (keyCode == KeyEvent.VK_D) {
                game.getMario().move(true);
            } else if(keyCode == KeyEvent.VK_E){
                game.getMario().attack();
            } else if(keyCode == KeyEvent.VK_ESCAPE){
                game.pauseGame();
            } else if (keyCode == KeyEvent.VK_F){
                game.getMario().finish();
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
        int keyCode = event.getKeyCode();
        GameState gameState = game.getGameState();
        if (gameState == GameState.PLAYING) {
            if (keyCode == KeyEvent.VK_Q) {
                game.getMario().stop(false);
            } else if (keyCode == KeyEvent.VK_D) {
                game.getMario().stop(true);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


}
