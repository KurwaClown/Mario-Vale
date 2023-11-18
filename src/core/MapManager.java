package core;

import gameobject.*;
import gameobject.block.*;
import gameobject.collectible.Brennus;
import gameobject.character.Mario;
import gameobject.collectible.*;
import gameobject.enemy.Champi;
import gameobject.enemy.Enemy;
import gameobject.enemy.Turtle;
import view.Camera;
import view.Map;
import view.Ressource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapManager {
    private Mario mario;
    private Map map;
    String csvFilePath;

    public MapManager(Camera camera, Mario mario) {
        this.map = new Map(camera);
        this.mario = mario;
        map.addMario(mario);
        this.csvFilePath = Ressource.getMap("map2");
    }

    public void loadMapFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 0; col < values.length; col++) {
                    int id = Integer.parseInt(values[col].trim());
                    if(id == 17) this.mario.setPosition(col*64, (lineCount-1)*64);
                    else if (id != -1) {
                        addObjectToMap(idToGameObject(id, col, lineCount));
                    }

                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GameObject idToGameObject(int id, int x, int y) {
        x = x * 64;
        y = y * 64;
        return switch (id) {
            case 4 -> new Brick(x, y);
            case 5 -> new Champi(x, y);
            case 7 -> new Coin(x, y);
            case 8 -> new DeadBrick(x, y);
            case 9 -> new Flag(x);
            case 12 -> new GroundBrick(x, y);
            case 24 -> new Turtle(x, y);
            case 26 -> new Pipe(x, y, false);
            case 27 -> new Pipe(x, y, true);
            case 28 -> new Bonus(x, y, new Ball());
            case 29 -> new Bonus(x, y, new Brennus());
            case 30 -> new Bonus(x, y, new Jersey());
            case 31 -> new Bonus(x, y, new Trophy());
            default -> null;
        };
    }

    private void addObjectToMap(GameObject gameObject){
        if(gameObject == null) return;


        if(gameObject instanceof Mario){
            map.addMario((Mario) gameObject);
        } else if (gameObject instanceof Block) {
            map.addBlocks((Block) gameObject);
        } else if (gameObject instanceof PowerUp) {
            map.addPowerup((PowerUp) gameObject);
        } else if (gameObject instanceof Flag) {
            map.addFlag((Flag) gameObject);
        } else if (gameObject instanceof Enemy) {
            map.addEnemy((Enemy) gameObject);
        } else if (gameObject instanceof Coin) {
            map.addCoin((Coin) gameObject);
        }
    }

    public void removeUnusedObjects() {
        this.map.getEnemies().removeIf(enemy -> enemy.getY() > 2999 || enemy.getX() < -2999);
        this.map.getBlocks().removeIf(block -> block.getY() > 2999 || block.getX() < -2999);
        this.map.getPowerUps().removeIf(powerup -> powerup.getY() > 2999 || powerup.getX() < -2999);
        this.map.getCoins().removeIf(coin -> coin.getY() > 2999 || coin.getX() < -2999);
    }

    public Map getMap() {
        return this.map;
    }

    public void reset(Camera camera) {
        this.map = new Map(camera);
        mario.reset();
        map.addMario(mario);
        camera.reset();
        loadMapFromCSV();

    }

    public void toggleHitboxes(){
        this.map.toggleHitboxes();
    }
}

