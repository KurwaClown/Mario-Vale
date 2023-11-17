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
    private final List<Mario> marios = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Block> blocks = new ArrayList<>();
    private final List<PowerUp> powerups = new ArrayList<>();
    private final List<Flag> flags = new ArrayList<>();
    private final List<Coin> coins = new ArrayList<>();

    private List<Projectile> projectiles = new ArrayList<>();
    private final BufferedImage backgroundImage = Ressource.getImage("map");
    private final BufferedImage littlecoin = Ressource.getImage("littlecoin");
    private final Camera camera;


    public Map(Camera camera) {
        this.camera = camera;
    }

    // add to the lists
    public void addMario(Mario mario) {
        marios.add(mario);
    }

    public void addBlocks(Block block) {
        blocks.add(block);
    }

    public void addPowerup(PowerUp powerup) {
        powerups.add(powerup);
    }

    public void addFlag(Flag flag) {
        flags.add(flag);
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

        for (Mario mario : marios) {
            mario.draw(g);
            g.drawString(String.valueOf(mario.getCoins()), (int)camera.getX() + 1270,  (int)camera.getY() + 30);
            g.drawImage(littlecoin,(int)camera.getX()+ 1230, (int) camera.getY()+8, null);
        }
        for (Block block : blocks) {
            block.draw(g);
        }
        for (PowerUp powerup : powerups) {
            powerup.draw(g);
        }
        for (Flag flag : flags) {
            flag.draw(g);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(g);
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
        for (Mario mario : marios) {
            mario.moveObject();
            mario.update();

        }
        for (Block block : blocks) {
            block.moveObject();
        }
        for (PowerUp powerup : powerups) {
            powerup.moveObject();
        }
        for (Flag flag : flags) {
            flag.moveObject();
        }
        for (Enemy enemy : enemies) {
            if(enemy instanceof gameobject.enemy.Champi champi) champi.update(marios.getFirst().getHitbox());
            else enemy.update();
            enemy.moveObject();
        }
        for(Projectile projectile : projectiles){
            projectile.moveObject();
        }

    }

    public List<Block> getBlocks() {
        return blocks;
    }


    public List<PowerUp> getPowerUps() {
        return powerups;
    }

    public List<Flag> getFlags() {
        return flags;
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
}
