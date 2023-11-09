import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class UserInterface extends JPanel {
    private Image backgroundImage;
    private Map gameMap; 

    public UserInterface(Map map) {
        gameMap = map; 


        try {
            backgroundImage = ImageIO.read(new File("/img/map.png"));
            backgroundImage = backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                backgroundImage = backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                repaint();
            }
        });

        setFocusable(true);
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
