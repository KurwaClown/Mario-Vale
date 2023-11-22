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
        } else if (game.getGameState() == GameState.TRANSITION) {
            game.getMap().draw(g);
        } else if (game.getGameState() == GameState.TRANSFORMATION){
            game.getMap().draw(g);
            Graphics2D g2d = (Graphics2D) g;
            float thickness = 4.0f;
            g2d.setStroke(new BasicStroke(thickness));
            int x = (int)game.getMario().getX()+64;
            int y = (int)game.getMario().getY()+95;
            int length = 50;
            if (!game.getShoot().isAngleLocked()) {
                g2d.drawLine(x, y, x + (int) (length * Math.cos(Math.toRadians(game.getShoot().getAngle()))), y - (int) (length * Math.sin(Math.toRadians(game.getShoot().getAngle()))));
            }
            else if (!game.getShoot().isPowerLocked() && game.getShoot().isAngleLocked()){
                g2d.fillRect(x, y, 10, (int) game.getShoot().getPower());
            }
            else if (game.getShoot().isPowerLocked()&& game.getShoot().isAngleLocked() && !game.getShoot().getTransformed()){
               game.transformation();
               game.getShoot().setTransformed(true);

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
    

