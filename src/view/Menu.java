package view;

import core.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu {
    private int selectedOption = 0;

    private final String[] options = {"Start", "Quit"};

    private final Game game;

    private boolean endurance =false;

    private final BufferedImage coin = Resource.getImage("coin");

    private final Camera camera;
    private final AudioManager audioManager = new AudioManager();
    private final BufferedImage backgroundImage = Resource.getImage("fondmenu");

    public Menu(Camera camera, Game game){
        this.camera = camera;
        this.game = game;
    }
    public void drawMainMenu(Graphics g) {
        audioManager.playLoopSound("./src/resource/sound/haka.wav");
        String[] options = {"Classic","Endurance"};
        g.drawImage(backgroundImage, 0, 0, null);

        drawMenuTitle("Mario'Vale", 400, g);

        drawMenu(g, options);
    }

    private void drawMenu(Graphics g, String[] options) {
        g.setFont(Resource.getMarioFont());
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.RED);
                int stringWidth = g.getFontMetrics().stringWidth(options[i]);
                int stringHeight = g.getFontMetrics().getHeight();
                g.drawRect((int)game.getCamera().getX()+640, 450 + i * 100 - stringHeight, stringWidth + 20, stringHeight + 10);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], (int)game.getCamera().getX()+650, 450 + i * 100);
        }
    }

    public void drawPauseMenu(Graphics g) {
        String[] options = {"Resume", "Menu"};
        //g.drawImage(backgroundImage, 0, 0, null);

        drawMenuTitle("Pause", (int)game.getCamera().getX()+500, g);

        drawMenu(g, options);
    }
    public void drawGameOver (Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1300, 730);
        drawMenuTitle("Game Over", 400, g);

        String[] options = {"Restart", "Quit"};
        g.setFont(Resource.getMarioFont());
        g.setColor(Color.white);
        if (this.isEndurance()){
            g.drawString("Score : "+game.Score(), 600, 400);
        }
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.RED);
                int stringWidth = g.getFontMetrics().stringWidth(options[i]);
                int stringHeight = g.getFontMetrics().getHeight();
                g.drawRect(390, 400 + i * 100 - stringHeight, stringWidth + 20, stringHeight + 10);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], 400, 400 + i * 100);
        }
    }
    public void drawWinMenu(Graphics g) {
        String[] options = {"Next", "Quit"};

        drawMenuTitle("Bravo !", (int)camera.getX()+400, g);
        g.setFont(Resource.getMarioFont().deriveFont(60f));
        g.drawString(String.valueOf(game.getMario().getCoins()), (int) camera.getX() + 850, 450);
        g.drawImage(coin, (int) camera.getX() + 780, 398, null);
        g.drawString("Score : "+game.getMario().getScore(), (int) camera.getX() + 780, 550);
        g.setFont(Resource.getMarioFont());
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.RED);
                int stringWidth = g.getFontMetrics().stringWidth(options[i]);
                int stringHeight = g.getFontMetrics().getHeight();
                g.drawRect((int)camera.getX()+640, 450 + i * 100 - stringHeight, stringWidth + 20, stringHeight + 10);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], (int)camera.getX()+650, 450 + i * 100);
        }

    }


    public void drawMenuTitle(String title, int xStart, Graphics g){
        g.setFont(Resource.getMarioFont().deriveFont(100f));
        Color[] colors = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE};
        for (int i = 0; i < title.length(); i++) {
            // Use modulo to cycle through the array of colors
            g.setColor(colors[i % colors.length]);

            g.drawString(title.substring(i, i + 1), xStart, 180);
            xStart += g.getFontMetrics().charWidth(title.charAt(i));
        }
    }
    public int getSelectedOption(){
        return selectedOption;
    }
    public String[] getOption(){
        return options;
    }
    public void setSelectedOption(int value) {
        selectedOption = value;
    }
    public void setEndurance(boolean value){
        this.endurance=value;
    }
    public boolean isEndurance(){
        return this.endurance;
    }

}

