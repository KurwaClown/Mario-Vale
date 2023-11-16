package view;

import core.Game;
import core.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        }
        else if (game.getGameState() == GameState.GAMEOVER){
            game.getMenu().drawGameOver(g);
            repaint();
        } else if (game.getGameState() == GameState.FLAG) {
            game.getMap().draw(g);
            drawFlagCount(game.getFlagCount());
            repaint();
        }else {
            game.getMap().draw(g);
        }

    }

    public void updateGame() {
            game.getMap().update();
        }

    public void drawFlagCount(int count) {
        Graphics g = this.getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(Ressource.getMarioFont());
        g.drawString(String.valueOf(count), 600, 350);
    }
}
    

