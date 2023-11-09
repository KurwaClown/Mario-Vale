import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Champi extends JPanel {
    private int rectX, rectY;
    private final int rectSpeed = 1;
    private BufferedImage image; 
    private double rotationAngle = 0;
    private int direction =1;

    public Champi() {
        setPreferredSize(new Dimension(600, 400));
        rectX = 80;
        rectY = 150;

        setFocusable(true);
        requestFocusInWindow();

        try {
            image = ImageIO.read(new File("champi.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        rectX += rectSpeed * direction; 
        if (rectX <= 0 || rectX >= 100) {
            direction *= -1; 
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (image != null) {
            g2d.translate(rectX + image.getWidth() / 2, rectY + image.getHeight() / 2);
            g2d.rotate(rotationAngle);
            g2d.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, null);
        }

        g2d.dispose();
    }




}

