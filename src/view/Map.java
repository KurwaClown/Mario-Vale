package view;

import gameobject.Flag;
import gameobject.block.Block;
import gameobject.character.Mario;
import gameobject.character.Projectile;
import gameobject.collectible.Coin;
import gameobject.collectible.Collectible;
import gameobject.collectible.PowerUp;
import gameobject.enemy.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// creating List to stocks the objects that will be paint on the map
public class Map {
    private Mario mario;
    private final List<Enemy> enemies = new CopyOnWriteArrayList<>();
    private final List<Block> blocks = new CopyOnWriteArrayList<>();
    private final List<PowerUp> powerups = new CopyOnWriteArrayList<>();

    private Flag flag;
    private final List<Coin> coins =new CopyOnWriteArrayList<>();

    private final List<Projectile> projectiles = new CopyOnWriteArrayList<>();
    private final BufferedImage backgroundImage = Resource.getImage("map");
    private final BufferedImage littlecoin = Resource.getImage("littlecoin");
    private final Camera camera;

    private boolean debugMode = false;


    public Map(Camera camera) {
        this.camera = camera;
    }

    // add to the lists
    public void addMario(Mario mario) {
        this.mario = mario;
    }

    public void addBlocks(Block block) {
        blocks.add(block);
    }

    public void addCollectible(Collectible collectible) {
        if(collectible instanceof PowerUp powerup) powerups.add(powerup);
        else if(collectible instanceof Coin coin) coins.add(coin);
    }

    public void addFlag(Flag flag) {
        this.flag = flag;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }


    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }



    // draw objects in the lists
    public void draw(Graphics g) {
        g.translate(-(int) camera.getX(), -(int) camera.getY());
        for (int i = 0; i < 10; i++) {
            g.drawImage(backgroundImage, i * backgroundImage.getWidth(), -150, null);
        }

        g.setFont(Resource.getMarioFont());
        g.setColor(Color.white);


        mario.draw(g);
        if(debugMode){
            this.drawLocation(g);
            mario.drawHitboxes(g);
            mario.drawData(g);
        }
        g.drawString(String.valueOf(mario.getCoins()), (int) camera.getX() + 1270, (int) camera.getY() + 30);
        g.drawImage(littlecoin, (int) camera.getX() + 1230, (int) camera.getY() + 8, null);

        for (Block block : blocks) {
            block.draw(g);
            if(debugMode)block.drawHitboxes(g);
        }
        for (PowerUp powerup : powerups) {
            powerup.draw(g);
        }

        if(this.flag != null) flag.draw(g);

        for (Enemy enemy : enemies) {
            enemy.draw(g);
            if(debugMode){
                enemy.drawHitboxes(g);
                enemy.drawData(g);
            }
        }
        for (Coin coin : coins) {
            coin.draw(g);
        }
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }


    }

    private void drawLocation(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Camera Coordinates : " + "X: " + (int)camera.getX() + " | Y: " + (int) camera.getY(), (int)camera.getX() + 10, 20);

    }

    // Update each map element
    public void update() {
            mario.moveObject();
            mario.update();

        for (Block block : blocks) {
            block.moveObject();
        }
        for (PowerUp powerup : powerups) {
            powerup.moveObject();
        }
            if(this.flag != null)flag.moveObject();

        for (Enemy enemy : enemies) {
            if (enemy instanceof gameobject.enemy.Champi champi) champi.update(mario.getHitbox());
            else enemy.update();
            enemy.moveObject();
        }
        for (Projectile projectile : projectiles) {
            projectile.moveObject();
        }

    }

    public List<Block> getBlocks() {
        return blocks;
    }


    public List<PowerUp> getPowerUps() {
        return powerups;
    }

    public Flag getFlag() {
        return flag;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void toggleDebugMode() {
        this.debugMode = !this.debugMode;
    }

    public void reset(){
        enemies.clear();
        blocks.clear();
        powerups.clear();
        coins.clear();
        projectiles.clear();
        camera.reset();
    }
}
