import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
// creating List to stocks the objects that will be paint on the map
public class Map {
    private List<Mario> marios; 
    private List<Champi> champis; 
    private List<Block> blocks;
    private List<Turtle> turtles;
    private List<Plant> plants;
    private List<PowerUp> powerups;
    private List<Flag> flags;

    private BufferedImage backgroundImage;
    private Camera camera;

    public Map(Camera camera) {
        marios = new ArrayList<>();
        champis = new ArrayList<>();
        blocks = new ArrayList<>();
        plants = new ArrayList<>();
        turtles = new ArrayList<>();
        powerups = new ArrayList<>();
        flags = new ArrayList<>();
        this.camera = camera;
        backgroundImage = Ressource.getImage("map");
    }
    // add to the lists
    public void addMario(Mario mario) {
        marios.add(mario);
    }
    public void addChampi(Champi champi) {
        champis.add(champi);
    }
    public void addBlocks(Block block){
        blocks.add(block);
    }
    public void addPlant(Plant plant){
        plants.add(plant);
    }
    public void addTurtle(Turtle turtle){
        turtles.add(turtle);
    }
    public void addPowerup(PowerUp powerup){
        powerups.add(powerup);
    }
    public void addFlag(Flag flag){
        flags.add(flag);
    }
    

    // draw objects in the lists
    public void draw(Graphics g) {
        g.translate(-(int)camera.getX(), -(int)camera.getY());
        for (int i = 0; i < 10; i++) {g.drawImage(backgroundImage, i*backgroundImage.getWidth(), 0, null);}
        for (Mario mario : marios) {
            mario.draw(g);
        }
        for (Champi champi : champis) {
            champi.draw(g);
        }
        for (Block block : blocks){
            block.draw(g);
        }
        for (Plant plant : plants){
            plant.draw(g);
        }
        for (Turtle turtle : turtles){
            turtle.draw(g);
        }
        for (PowerUp powerup : powerups){
            powerup.draw(g);
        }
        for (Flag flag : flags){
            flag.draw(g);
        }

    }
    // update each frame of the loop
    public void update() {
        for (Mario mario : marios) {
            mario.moveObject();
            mario.update();
        }
        for (Champi champi : champis) {
            champi.moveObject();
        }
        for (Block block : blocks){
            block.moveObject();
        }
        for (Plant plant : plants){
            plant.moveObject();
        }
        for (Turtle turtle : turtles){
            turtle.moveObject();
        }
        for (PowerUp powerup : powerups){
            powerup.moveObject();
        }
        for (Flag flag : flags){
            flag.moveObject();
        }

    }
    public List<Block> getBlocks(){
        return blocks;
    }
        public List<Mario> getMarios(){
        return marios;
    }
        public List<Champi> getChampis(){
        return champis;
    }
        public List<Plant> getPlants(){
        return plants;
    }
        public List<Turtle> getTurtles(){
        return turtles;
    }
        public List<PowerUp> getPowerUps(){
        return powerups;
    }
        public List<Flag> getFlags(){
        return flags;
    }
}
