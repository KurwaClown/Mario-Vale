import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu {
    private int selectedOption = 0;

    private String[] options = {"Start", "Quit"};

    public Menu(){

    }
    public void drawMainMenu(Graphics g) {
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\coraa\\IdeaProjects\\T-JAV-501-TLS_7\\src\\font\\SuperMario2561.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            System.out.println("Police chargée avec succès.");
        } catch (IOException | FontFormatException e) {
            System.out.println("Échec du chargement de la police.");
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.BOLD, 20);
        }
        g.setFont(customFont);
        g.setColor(Color.white);
        g.drawString("Mario'Vale", 400, 300);
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.RED); // Couleur du sélecteur
            } else {
                g.setColor(Color.WHITE); // Couleur normale
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

