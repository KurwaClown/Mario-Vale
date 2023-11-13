import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class MapManager {
    private Mario mario;
    private Map map;
    private Champi champi;
    public MapManager(Camera camera, Mario mario){
        this.map = new Map(camera);
        this.mario = mario;
        map.addMario(mario);
    }
    public void loadMapFromCSV(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Remplacez la virgule par le délimiteur utilisé dans votre CSV
                // Supposons que le format CSV est: type,x,y,[paramètres optionnels]
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
                    case "Bonus":
                        // Supposons que le quatrième valeur est le type de bonus
                        String bonusType = values[3];
                        map.addBlocks(new Bonus(x, y, new Jersey())); // Remplacez par le bon type de bonus
                        break;
                    // Ajoutez d'autres cas selon les types d'objets de votre jeu
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getMap() {
        return this.map;
    }
}

