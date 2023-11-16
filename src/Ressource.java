import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Ressource {

    private static final Map<String, BufferedImage> images = new HashMap<>();

    static {
        loadImages();
    }

    private static void loadImages() {
        try {
            images.put("mario", ImageIO.read(Ressource.class.getResource("./img/mario_running1.png")));
            images.put("mario1", ImageIO.read(Ressource.class.getResource("./img/mario_running2.png")));
            images.put("champi", ImageIO.read(Ressource.class.getResource("./img/champi.png")));
            images.put("turtle", ImageIO.read(Ressource.class.getResource("./img/turtle.png")));
            images.put("plant", ImageIO.read(Ressource.class.getResource("./img/plant.png")));
            images.put("brick", ImageIO.read(Ressource.class.getResource("./img/brick.png")));
            images.put("deadBrick", ImageIO.read(Ressource.class.getResource("./img/deadBrick.png")));
            images.put("bonus", ImageIO.read(Ressource.class.getResource("./img/bonus.png")));
            images.put("jersey", ImageIO.read(Ressource.class.getResource("./img/jersey.png")));
            images.put("trophy", ImageIO.read(Ressource.class.getResource("./img/trophy.png")));
            images.put("champiRugby", ImageIO.read(Ressource.class.getResource("./img/champiRugby.png")));
            images.put("marioStade", ImageIO.read(Ressource.class.getResource("./img/marioStade.png")));
            images.put("flag", ImageIO.read(Ressource.class.getResource("./img/flag.png")));
            images.put("coin", ImageIO.read(Ressource.class.getResource("./img/coin.png")));
            images.put("flagBroken", ImageIO.read(Ressource.class.getResource("./img/flagOnGround.png")));
            images.put("map", ImageIO.read(Ressource.class.getResource("./img/map.jpg")));
            images.put("shell", ImageIO.read(Ressource.class.getResource("./img/shell.png")));
            images.put("ball", ImageIO.read(Ressource.class.getResource("./img/ball.png")));
            images.put("littlecoin", ImageIO.read(Ressource.class.getResource("./img/littlecoin.png")));
            images.put("fondmenu", ImageIO.read(Ressource.class.getResource("./img/fond_menu.png")));
            images.put("groundBrick", ImageIO.read(Ressource.class.getResource("./img/ground.png")));
            images.put("brennus", ImageIO.read(Ressource.class.getResource("./img/brennus.png")));
            images.put("marioBrennus", ImageIO.read(Ressource.class.getResource("./img/marioval_brenus.png")));
            images.put("marioDore", ImageIO.read(Ressource.class.getResource("./img/marioval_dore.png")));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getImage(String name) {
        return images.get(name);
    }
}
