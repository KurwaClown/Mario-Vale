import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class MapManager {
    private Mario mario;
    private Map map;
    String csvFilePath;
    public MapManager(Camera camera, Mario mario, String csvFilePath){
        this.map = new Map(camera);
        this.mario = mario;
        map.addMario(mario);
        this.csvFilePath = csvFilePath;
    }
    public void loadMapFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String type = values[0];
                int x = Integer.parseInt(values[1]);
                int y = Integer.parseInt(values[2]);

                switch (type) {
                    case "Mario":
                        this.mario = new Mario(x, y);
                        map.addMario(mario);
                        break;
                    case "Brick":
                        map.addBlocks(new Brick(x, y));
                        break;
                    case "Champi":
                        map.addEnemy(new Champi(x, y));
                        break;
                    case "Turtle":
                    map.addEnemy(new Turtle(x, y));
                    break;
                    case "Bonus":
                        String bonusType = values[3];
                        PowerUp powerUp = new Jersey();
                        if(bonusType.equals("Ball")) powerUp = new Ball();
                        else if(bonusType.equals("Trophy")) powerUp = new Trophy();
                        map.addBlocks(new Bonus(x, y, powerUp));
                        break;
                    case "Coin":
                        map.addCoin(new Coin(x, y));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getMap() {
        return this.map;
    }
    public void reset(Camera camera){
        this.map = new Map(camera);
        mario.reset();
        loadMapFromCSV();
    }
}

