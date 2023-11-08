import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Mario> marios; 
    private List<Champi> champis; 
    private List<Brick> bricks;
    private List<Turtle> turtles;
    private List<Plant> plants;
    private List<Powerup> powerups;
    private List<Flag> flags;


    public Map() {
        marios = new ArrayList<>();
        champis = new ArrayList<>();
        bricks = new ArrayList<>();
        plants = new ArrayList<>();
        turtles = new ArrayList<>();
        powerups = new ArrayList<>();
        flags = new ArrayList<>();
    }

    public void addMario(Mario mario) {
        marios.add(mario);
    }
    public void addChampi(Champi champi) {
        champis.add(champi);
    }
    public void addBrick(Brick brick){
        bricks.add(brick);
    }
    public void addBrick(Plant plant){
        plants.add(plant);
    }
    public void addBrick(Turtle turtle){
        turtles.add(turtle);
    }
    public void addBrick(Powerup powerup){
        powerups.add(powerup);
    }
    public void addBrick(Flag flag){
        flags.add(flag);
    }
    


    public void draw(Graphics g) {
        for (Mario mario : marios) {
            mario.draw(g);
        }
        for (Champi champi : champis) {
            champi.draw(g);
        }
        for (Brick brick : bricks){
            brick.draw(g);
        }
        for (Plant plant : plants){
            plant.draw(g);
        }
        for (Turtle turtle : turtles){
            turtle.draw(g);
        }
        for (Powerup powerup : powerups){
            powerup.draw(g);
        }
        for (Flag flag : flags){
            flag.draw(g);
        }

    }

    public void update() {
        for (Mario mario : marios) {
            mario.update();
        }
        for (Champi champi : champis) {
            champi.update();
        }
        for (Brick brick : bricks){
            brick.update();
        }
        for (Plant plant : plants){
            plant.update();
        }
        for (Turtle turtle : turtles){
            turtle.update();
        }
        for (Powerup powerup : powerups){
            powerup.update();
        }
        for (Flag flag : flags){
            flag.update();
        }

    }
}
