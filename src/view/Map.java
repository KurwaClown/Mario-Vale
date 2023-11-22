package view;

import gameobject.Flag;
import gameobject.block.Block;
import gameobject.character.Mario;
import gameobject.character.Projectile;
import gameobject.collectible.Collectible;
import gameobject.collectible.GreenMissile;
import gameobject.collectible.PowerUp;
import gameobject.collectible.RedMissile;
import gameobject.enemy.Enemy;
import gameobject.KickBall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// creating List to stocks the objects that will be paint on the map
public class Map {
    private Mario mario;
    private final List<Enemy> enemies = new CopyOnWriteArrayList<>();
    private final List<Block> blocks = new CopyOnWriteArrayList<>();
    private final List<Collectible> collectibles = new CopyOnWriteArrayList<>();

    private Flag flag;

    private KickBall kickBall;

    private final List<Projectile> projectiles = new CopyOnWriteArrayList<>();
    private final BufferedImage backgroundImage = Resource.getImage("map");
    private final BufferedImage littlecoin = Resource.getImage("littlecoin");
    private final Camera camera;

    private boolean debugMode = false;


    private int radians = 0;
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
        collectibles.add(collectible);
    }

    public void addFlag(Flag flag) {
        this.flag = flag;
    }
    public void addKickBall(KickBall kickBall){
        this.kickBall=kickBall;
    }

    public KickBall getKickBall(){
        return kickBall;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }


    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }


    // draw objects in the lists
    public void draw(Graphics g) {
        Graphics2D g2d = ((Graphics2D) g);
        g2d.translate(-(int) camera.getX(), -(int) camera.getY());
        for (int i = 0; i < 10; i++) {
            g2d.drawImage(backgroundImage, i * backgroundImage.getWidth(), -150, null);
        }

        g2d.setFont(Resource.getMarioFont());
        g2d.setColor(Color.white);
        if(kickBall != null) {
            AffineTransform transform = g2d.getTransform();
            if(kickBall.getY() < 520)g2d.rotate(Math.toRadians(radians = radians-12), kickBall.getX() + (double) kickBall.getSpriteDimension().width /2, kickBall.getY() + (double) kickBall.getSpriteDimension().height /2);
            kickBall.draw(g2d);
            g2d.setTransform(transform);
        }

        mario.draw(g2d);
        if(debugMode){
            this.drawLocation(g2d);
            mario.drawHitboxes(g2d);
            mario.drawData(g2d);
        }
        g2d.drawString(String.valueOf(mario.getCoins()), (int) camera.getX() + 1270, (int) camera.getY() + 30);
        g2d.drawImage(littlecoin, (int) camera.getX() + 1230, (int) camera.getY() + 8, null);

        for (Block block : blocks) {
            block.draw(g2d);
            if(debugMode)block.drawHitboxes(g2d);
        }
        for (Collectible collectible : collectibles) {
            collectible.draw(g2d);
        }

        if(this.flag != null) flag.draw(g2d);

        for (Enemy enemy : enemies) {
            enemy.draw(g2d);
            if(debugMode){
                enemy.drawHitboxes(g2d);
                enemy.drawData(g2d);
            }
        }
        for (Projectile projectile : projectiles) {
            projectile.draw(g2d);
        }


    }

    private void drawLocation(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Camera Coordinates : " + "X: " + (int) camera.getX() + " | Y: " + (int) camera.getY(), (int) camera.getX() + 10, 20);

    }

    // Update each map element
    public void update() {
        mario.update();
        mario.moveObject();

        if (this.flag != null) flag.moveObject();

        for (Collectible collectible : collectibles) {
            if(collectible instanceof GreenMissile || collectible instanceof RedMissile) {
                ((PowerUp) collectible).update();
                ((PowerUp) collectible).moveObject();
            }
        }
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


    public List<Collectible> getCollectibles() {
        return collectibles;
    }

    public Flag getFlag() {
        return flag;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }


    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void toggleDebugMode() {
        this.debugMode = !this.debugMode;
    }

    public Camera getCamera() {
        return camera;
    }
    public void reset(){
        enemies.clear();
        blocks.clear();
        collectibles.clear();
        projectiles.clear();
        camera.reset();
    }
}
