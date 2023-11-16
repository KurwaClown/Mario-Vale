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
        }
        else if (game.getGameState() == GameState.GAMEOVER){
            game.getMenu().drawGameOver(g);
            repaint();
        }else {
            game.getMap().draw(g);
        }

    }

    public void updateGame() {
        game.getMap().update();
    }
    
}