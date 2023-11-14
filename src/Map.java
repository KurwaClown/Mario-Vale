import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;

// creating List to stocks the objects that will be paint on the map
public class Map {
    private final List<Mario> marios = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Block> blocks = new ArrayList<>();
    private final List<PowerUp> powerups = new ArrayList<>();
    private final List<Flag> flags = new ArrayList<>();
    private final BufferedImage backgroundImage = Ressource.getImage("map");

    private final BufferedImage littlecoin = Ressource.getImage("littlecoin");
    private final Camera camera;

    private Mario mario;

    public Map(Camera camera) {
        this.camera = camera;
    }

    // add to the lists
    public void addMario(Mario mario) {
        marios.add(mario);
    }

    public void addBlocks(Block block) {
        blocks.add(block);
    }

    public void addPowerup(PowerUp powerup) {
        powerups.add(powerup);
    }

    public void addFlag(Flag flag) {
        flags.add(flag);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    // draw objects in the lists
    public void draw(Graphics g) {
        g.translate(-(int) camera.getX(), -(int) camera.getY());
        for (int i = 0; i < 10; i++) {
            g.drawImage(backgroundImage, i * backgroundImage.getWidth(), 0, null);
        }
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
        g.drawString("01", (int)camera.getX() + 1270,  (int)camera.getY() + 30);
        g.drawImage(littlecoin,(int)camera.getX()+ 1230, (int) camera.getY()+8, null);
        for (Mario mario : marios) {
            mario.draw(g);
        }
        for (Block block : blocks) {
            block.draw(g);
        }
        for (PowerUp powerup : powerups) {
            powerup.draw(g);
        }
        for (Flag flag : flags) {
            flag.draw(g);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

    }

    // Update each map element
    public void update() {
        for (Mario mario : marios) {
            mario.moveObject();
            mario.update();
        }
        for (Block block : blocks) {
            block.moveObject();
        }
        for (PowerUp powerup : powerups) {
            powerup.moveObject();
        }
        for (Flag flag : flags) {
            flag.moveObject();
        }
        for (Enemy enemy : enemies) {
            enemy.update();
            enemy.moveObject();
        }

    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<Mario> getMarios() {
        return marios;
    }

    public List<PowerUp> getPowerUps() {
        return powerups;
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
