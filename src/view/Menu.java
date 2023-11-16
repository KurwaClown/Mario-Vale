package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu {
    private int selectedOption = 0;

    private String[] options = {"Start", "Quit"};

    private final BufferedImage backgroundImage = Ressource.getImage("fondmenu");

    public Menu(){

    }
    public void drawMainMenu(Graphics g) {
        String[] options = {"Start", "Quit"};
        g.drawImage(backgroundImage, 0, 0, null);

        drawMenuTitle("Mario'Vale", 400, g);

        g.setFont(Ressource.getMarioFont());
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.RED);
                int stringWidth = g.getFontMetrics().stringWidth(options[i]);
                int stringHeight = g.getFontMetrics().getHeight();
                g.drawRect(640, 450 + i * 100 - stringHeight, stringWidth + 20, stringHeight + 10);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], 650, 450 + i * 100);
        }
    }
    public void drawGameOver (Graphics g){

        drawMenuTitle("Game Over", 400, g);

        String[] options = {"Restart", "Quit"};
        g.setFont(Ressource.getMarioFont());
        g.setColor(Color.white);

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

    public void drawMenuTitle(String title, int xStart, Graphics g){
        g.setFont(Ressource.getMarioFont().deriveFont(100f));
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
}
