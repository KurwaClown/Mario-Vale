import javax.swing.*;
import java.awt.*;
// Base window, painting all the elements of the map
public class UserInterface extends JPanel {

    private final MapManager mapManager;

    public UserInterface(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        mapManager.getMap().draw(g);
    }


    public void updateGame() {
        mapManager.getMap().update();
        repaint(); 
    }
    
}
