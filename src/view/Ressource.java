package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ressource {

    private static final Map<String, BufferedImage> images = new HashMap<>();

    private static Font marioFont;

    private static Map<String, String> maps = new HashMap<>();

    static {
        loadImages();
        loadFont();
        loadMaps();
    }

    private static void loadMaps() {
        maps.put("map1", Ressource.class.getResource("../mapcsv/map1.csv").getFile());
    }

    private static void loadFont() {
        try {
            File fontFile = new File(Objects.requireNonNull(Ressource.class.getResource("../font/SuperMario256.ttf")).getFile());
            marioFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(20f);
            System.out.println("Mario Font loaded");
        } catch (Exception e) {
            marioFont = new Font("SansSerif", Font.BOLD, 20);
            System.out.println("Mario Font not loaded, using SansSerif");
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(marioFont);
    }

    private static void loadImages() {
        try {
            images.put("mario", ImageIO.read(Ressource.class.getResource("../img/mario_running1.png")));
            images.put("mario1", ImageIO.read(Ressource.class.getResource("../img/mario_running2.png")));
            images.put("champi", ImageIO.read(Ressource.class.getResource("../img/champi.png")));
            images.put("turtle", ImageIO.read(Ressource.class.getResource("../img/turtle.png")));
            images.put("plant", ImageIO.read(Ressource.class.getResource("../img/plant.png")));
            images.put("brick", ImageIO.read(Ressource.class.getResource("../img/brick.png")));
            images.put("deadBrick", ImageIO.read(Ressource.class.getResource("../img/deadBrick.png")));
            images.put("bonus", ImageIO.read(Ressource.class.getResource("../img/bonus.png")));
            images.put("jersey", ImageIO.read(Ressource.class.getResource("../img/jersey.png")));
            images.put("trophy", ImageIO.read(Ressource.class.getResource("../img/trophy.png")));
            images.put("champiRugby", ImageIO.read(Ressource.class.getResource("../img/champiRugby.png")));
            images.put("marioStade", ImageIO.read(Ressource.class.getResource("../img/marioStade.png")));
            images.put("flag", ImageIO.read(Ressource.class.getResource("../img/flag.png")));
            images.put("coin", ImageIO.read(Ressource.class.getResource("../img/coin.png")));
            images.put("flagBroken", ImageIO.read(Ressource.class.getResource("../img/flagOnGround.png")));
            images.put("map", ImageIO.read(Ressource.class.getResource("../img/map.jpg")));
            images.put("shell", ImageIO.read(Ressource.class.getResource("../img/shell.png")));
            images.put("ball", ImageIO.read(Ressource.class.getResource("../img/ball.png")));
            images.put("littlecoin", ImageIO.read(Ressource.class.getResource("../img/littlecoin.png")));
            images.put("fondmenu", ImageIO.read(Ressource.class.getResource("../img/fond_menu.png")));
            images.put("groundBrick", ImageIO.read(Ressource.class.getResource("../img/ground.png")));
            System.out.println("Images loaded");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getImage(String name) {
        return images.get(name);
    }

    public static Font getMarioFont() {
        return marioFont;
    }

    public static String getMap(String name) {
        return maps.get(name);
    }
}
