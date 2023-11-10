import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface extends JPanel {
    private BufferedImage backgroundImage;
    private Map gameMap; 

    public UserInterface(Map map) {
        gameMap = map; 
        backgroundImage = Ressource.getImage("map");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);

        gameMap.draw(g);
    }


    public void updateGame() {
        gameMap.update();
        repaint(); 
    }
    
}
