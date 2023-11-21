package view;

import core.Game;
import core.GameState;

import javax.swing.*;
import java.awt.*;

// Base window, painting all the elements of the map
public class UserInterface extends JPanel {
    private final Game game;

    public UserInterface(Game game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game.getGameState() == GameState.MENU) {
            game.getMenu().drawMainMenu(g);
            repaint();

        } else if (game.getGameState() == GameState.GAMEOVER) {
            game.getMenu().drawGameOver(g);
        } else if (game.getGameState() == GameState.FLAG) {
            game.getMap().draw(g);
            addGreyMask(g);
            drawFlagCount(g);
        } else if (game.getGameState() == GameState.PAUSED) {
            game.getMap().draw(g);
            game.getMenu().drawPauseMenu(g);
            addGreyMask(g);
            repaint();
        } else if (game.getGameState() == GameState.WIN) {
            game.getMap().draw(g);
            game.getMenu().drawWinMenu(g);
        } else if (game.getGameState() == GameState.TRANSFORMATION) {
            game.getMario().moveObject();
            game.getMario().update();
            game.getMap().draw(g);
            if (!game.getMario().getReadyToKick()) {
                game.getMario().setVelX(3);
                game.getMario().setFalling(false);
            }
            if ((game.getMario().getX() > (game.getCamera().getX() + 1310))&& !game.getMario().getDontMove()) {
                game.getMario().setX(game.getMapManager().getMap().getFlag().getX() + 800);
                game.getMario().setReadyToKick(true);
                game.getMario().setDontMove(true);
                game.getMario().setVelX(0);
            }
            if (game.getMario().getReadyToKick()&& game.getCamera().getX()<game.getMario().getX()-50) {
                game.getCamera().moveCam(3, 0);
            }
        } else {
                game.getMap().draw(g);
            }

        }



    public void updateGame() {
            game.getMap().update();
    }

    public void drawFlagCount(Graphics g) {
        g.setFont(Resource.getMarioFont().deriveFont(60f));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(game.getFlagCount()), (int)game.getCamera().getX()+(getWidth()/2),  400);
    }

    private void addGreyMask(Graphics g) {
        g.setColor(new Color(128, 128, 128, 128));
        g.fillRect((int)game.getCamera().getX(), (int)game.getCamera().getY(), 1300, 800);
    }
}
    

