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
        Font customFont = null;
        Font titleFont= null;
        String[] options = {"Start", "Quit"};
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("out/production/T-JAV-501-TLS_7/font/SuperMario2561.ttf")).deriveFont(20f);
            titleFont = customFont.deriveFont(100f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.BOLD, 20);
        }
        g.drawImage(backgroundImage, 0, 0, null);
        g.setFont(titleFont);
        String title = "Mario'Vale";
        int xStart = 340;
        for (int i = 0; i < title.length(); i++) {
            if (i==0){
                g.setColor(Color.RED);
            }
            if (i==1){
                g.setColor(Color.GREEN);
            }
            if (i==2){
                g.setColor(Color.YELLOW);
            }
            if (i==3){
                g.setColor(Color.BLUE);
            }
            if (i==4){
                g.setColor(Color.GREEN);
            }
            if (i==5){
                g.setColor(Color.BLUE);
            }
            if (i==6){
                g.setColor(Color.RED);
            }
            if (i==7){
                g.setColor(Color.YELLOW);
            }
            if (i==8){
                g.setColor(Color.GREEN);
            }
            if (i==9){
                g.setColor(Color.BLUE);
            }
            g.drawString(title.substring(i, i+1), xStart, 180);
            xStart += g.getFontMetrics().charWidth(title.charAt(i));
        }
        g.setFont(customFont);
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
        Font customFont = null;
        Font titleFont= null;
        String[] options = {"Restart", "Quit"};
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("out/production/T-JAV-501-TLS_7/font/SuperMario2561.ttf")).deriveFont(20f);
            titleFont = customFont.deriveFont(100f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.BOLD, 20);
        }
        g.setFont(titleFont);
        String title = "Game Over";
        int xStart = 400;
        for (int i = 0; i < title.length(); i++) {
            if (i==0){
                g.setColor(Color.RED);
            }
            if (i==1){
                g.setColor(Color.GREEN);
            }
            if (i==2){
                g.setColor(Color.YELLOW);
            }
            if (i==3){
                g.setColor(Color.BLUE);
            }
            if (i==4){
                g.setColor(Color.GREEN);
            }
            if (i==5){
                g.setColor(Color.BLUE);
            }
            if (i==6){
                g.setColor(Color.RED);
            }
            if (i==7){
                g.setColor(Color.YELLOW);
            }
            if (i==8){
                g.setColor(Color.GREEN);
            }

            g.drawString(title.substring(i, i+1), xStart, 300);
            xStart += g.getFontMetrics().charWidth(title.charAt(i));
        }
        g.setFont(customFont);
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

