import javax.swing.*;
import java.awt.*;

public class UserInterface extends JPanel{

    private Game game;

    public UserInterface(Game game, int width, int height){

        this.game = game;

        this.setMinimumSize(new Dimension(width, height));
        this.setSize(new Dimension(width, height));
        this.setVisible(true);
    }

}
