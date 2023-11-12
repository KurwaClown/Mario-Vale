import javax.swing.*;
import java.awt.*;
// Base window, painting all the elements of the map
public class UserInterface extends JPanel {
    private Map gameMap; 

    public UserInterface(Map map) {
        gameMap = map;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameMap.draw(g);
    }


    public void updateGame() {
        gameMap.update();
        repaint(); 
    }
    
}
