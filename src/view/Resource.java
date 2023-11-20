package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Resource {

    private static final Map<String, BufferedImage> images = new HashMap<>();

    private static Font marioFont;

    private static final Map<String, String> maps = new HashMap<>();

    static {
        loadImages();
        loadFont();
        loadMaps();
    }

    private static void loadMaps() {
        maps.put("map1", Objects.requireNonNull(Resource.class.getResource("../resource/map/map1.csv")).getFile());
        maps.put("map2", Objects.requireNonNull(Resource.class.getResource("../resource/map/map2.csv")).getFile());
    }

    private static void loadFont() {
        try {
            File fontFile = new File(Objects.requireNonNull(Resource.class.getResource("../resource/font/SuperMario256.ttf")).getFile());
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
            images.put("mario", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/mario_running1.png"))));
            images.put("mario1", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/mario_running2.png"))));
            images.put("champi", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/champi.png"))));
            images.put("turtle", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/turtle.png"))));
            images.put("plant", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/plant.png"))));
            images.put("brick", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/brick.png"))));
            images.put("deadBrick", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/deadBrick.png"))));
            images.put("bonus", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/bonus.png"))));
            images.put("jersey", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/jersey.png"))));
            images.put("trophy", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/trophy.png"))));
            images.put("champiRugby", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/champiRugby.png"))));
            images.put("marioStade", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/marioStade.png"))));
            images.put("flag", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/flag.png"))));
            images.put("coin", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/coin.png"))));
            images.put("flagBroken", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/flagOnGround.png"))));
            images.put("map", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/map.jpg"))));
            images.put("shell", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/shell.png"))));
            images.put("ball", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/ball.png"))));
            images.put("ballSmall", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/ballSmall.png"))));
            images.put("littlecoin", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/littlecoin.png"))));
            images.put("fondmenu", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/fond_menu.png"))));
            images.put("groundBrick", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/ground.png"))));
            images.put("brennus", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/brennus.png"))));
            images.put("marioBrennus", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/marioval_brenus.png"))));
            images.put("marioDore", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/marioval_dore.png"))));
            images.put("pipeBase", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/pipebody.png"))));
            images.put("pipeTop", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/pipetop.png"))));
            images.put("missile", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/missile.png"))));
            images.put("canon", ImageIO.read(Objects.requireNonNull(Resource.class.getResource("../resource/img/canon.png"))));
            System.out.println("Images loaded");

        } catch (IOException e) {
            System.out.println("Error while loading images");
            System.out.println("Closing game");
            System.exit(1);
        }
    }

    public static BufferedImage getImage(String name) {
        return images.get(name);
    }

    public static BufferedImage getFlippedImage(BufferedImage image) {
        return flipHorizontally(image);
    }

    private static BufferedImage flipHorizontally(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage flippedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = flippedImage.createGraphics();

        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1)); // Flip horizontally
        at.concatenate(AffineTransform.getTranslateInstance(-width, 0)); // Translate back

        g2d.setTransform(at);
        g2d.drawImage(originalImage, 0, 0, null);

        g2d.dispose();

        return flippedImage;
    }

    public static Font getMarioFont() {
        return marioFont;
    }

    public static String getMap(String name) {
        return maps.get(name);
    }
}
