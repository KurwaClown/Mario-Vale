package view;

import gameobject.*;
import gameobject.block.Block;
import gameobject.character.Mario;
import gameobject.character.Projectile;
import gameobject.collectible.Coin;
import gameobject.collectible.PowerUp;
import gameobject.enemy.Enemy;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;

// creating List to stocks the objects that will be paint on the map
public class Map {
    private Mario mario;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Block> blocks = new ArrayList<>();
    private final List<PowerUp> powerups = new ArrayList<>();
    private Flag flag = new Flag(1600);
    private final List<Coin> coins = new ArrayList<>();

    private List<Projectile> projectiles = new ArrayList<>();
    private final BufferedImage backgroundImage = Ressource.getImage("map");
    private final BufferedImage littlecoin = Ressource.getImage("littlecoin");
    private final Camera camera;

    private boolean drawHitboxes = false;


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

    public void addPowerup(PowerUp powerup) {
        powerups.add(powerup);
    }

    public void addFlag(Flag flag) {
        this.flag = flag;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addCoin(Coin coin) {
        coins.add(coin);
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

        g.setFont(Ressource.getMarioFont());
        g.setColor(Color.white);


        mario.draw(g);
        if(drawHitboxes)mario.drawHitboxes(g);
        g.drawString(String.valueOf(mario.getCoins()), (int) camera.getX() + 1270, (int) camera.getY() + 30);
        g.drawImage(littlecoin, (int) camera.getX() + 1230, (int) camera.getY() + 8, null);

        for (Block block : blocks) {
            block.draw(g);
            if(drawHitboxes)block.drawHitboxes(g);
        }
        for (PowerUp powerup : powerups) {
            powerup.draw(g);
        }

        flag.draw(g);

        for (Enemy enemy : enemies) {
            enemy.draw(g);
            if(drawHitboxes)enemy.drawHitboxes(g);
        }
        for (Coin coin : coins) {
            coin.draw(g);
        }
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }


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
            flag.moveObject();

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

    public void toggleHitboxes() {
        this.drawHitboxes = !this.drawHitboxes;
    }
}
