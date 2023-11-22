package core;

import gameobject.Flag;
import gameobject.GameObject;
import gameobject.block.*;
import gameobject.character.Mario;
import gameobject.collectible.*;
import gameobject.enemy.Canon;
import gameobject.enemy.Champi;
import gameobject.enemy.Enemy;
import gameobject.enemy.Turtle;
import view.Camera;
import view.Map;
import view.Menu;
import view.Resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapManager {
    private final Mario mario;
    private Map map;

    private int score;
    private int lastGeneratedX = 0;
    private final int BLOCK_WIDTH = 64;
    private final int GENERATION_THRESHOLD = 1;

    private Menu menu;

    private int currentLevel = 1;
    String csvFilePath;

    public MapManager(Camera camera, Mario mario, Menu menu) {
        this.menu = menu;

        this.map = new Map(camera);
        this.mario = mario;
        map.addMario(mario);

    }
    public void choosedMap() {
        if (menu.isEndurance()) {
            this.csvFilePath = Resource.getMap("endurance");
        } else {
            this.csvFilePath = Resource.getMap("map2");
        }
        loadMapFromCSV();
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
            System.out.println("Error while loading map, check the csv file");
            System.out.println("Closing game");
            System.exit(1);
        }
    }
    public void generateGroundIfNecessary(Camera camera) {
        int generationPointX = (int)camera.getX()  + 1300+ GENERATION_THRESHOLD * BLOCK_WIDTH;
        if (lastGeneratedX + BLOCK_WIDTH <= generationPointX) {
            for (int x = lastGeneratedX; x < generationPointX; x += BLOCK_WIDTH) {
                Block groundBlock = new GroundBrick(x, 700 - BLOCK_WIDTH);
                map.addBlocks(groundBlock);
            }
            lastGeneratedX = generationPointX;
        }
    }
    public void goToNextLevel() {
        currentLevel++;
        map.reset();
        csvFilePath = Resource.getMap("map" + currentLevel);
        loadMapFromCSV();

    }


    private GameObject idToGameObject(int id, int x, int y) {
        x = x * 64;
        y = y * 64;
        return switch (id) {
            case 2 -> new Bonus(x, y, new Coin(-100, -2000));
            case 4 -> new Brick(x, y);
            case 5 -> new Champi(x, y- 64);
            case 7 -> new Coin(x, y);
            case 8 -> new DeadBrick(x, y);
            case 9 -> new Flag(x);
            case 12 -> new GroundBrick(x, y);
            case 13 -> new BonusBrick(x, y, new Ball());
            case 14 -> new BonusBrick(x, y, new Brennus());
            case 15 -> new BonusBrick(x, y, new Jersey());
            case 16 -> new BonusBrick(x, y, new Trophy());
            case 18 -> new BonusBrick(x, y, new Coin(-100, -2000));
            case 24 -> new Turtle(x, y- 64);
            case 26 -> new Pipe(x, y, false);
            case 27 -> new Pipe(x, y, true);
            case 28 -> new Bonus(x, y, new Ball());
            case 29 -> new Bonus(x, y, new Brennus());
            case 30 -> new Bonus(x, y, new Jersey());
            case 31 -> new Bonus(x, y, new Trophy());
            case 32 -> new Canon(x,y, getMap());
            default -> null;
        };
    }

    private void addObjectToMap(GameObject gameObject){
        if(gameObject == null) return;

        if(gameObject instanceof Mario){
            map.addMario((Mario) gameObject);
        } else if (gameObject instanceof Block) {
            map.addBlocks((Block) gameObject);
        } else if (gameObject instanceof PowerUp || gameObject instanceof Coin) {
            map.addCollectible((Collectible) gameObject);
        } else if (gameObject instanceof Flag) {
            map.addFlag((Flag) gameObject);
        } else if (gameObject instanceof Enemy) {
            map.addEnemy((Enemy) gameObject);
        }
    }

    public void removeUnusedObjects() {
        this.map.getEnemies().removeIf(enemy -> enemy.getY() > 2999 || enemy.getX() < -2999);
        this.map.getBlocks().removeIf(block -> block.getY() > 2999 || block.getX() < -2999);
        this.map.getCollectibles().removeIf(powerup -> ((GameObject)powerup).getY() > 2999 || ((GameObject) powerup).getX() < -2999);
    }

    public Map getMap() {
        return this.map;
    }

    public void reset(Camera camera) {
        camera.reset();
        this.map = new Map(camera);
        lastGeneratedX =0;
        loadMapFromCSV();
        generateGroundIfNecessary(camera);
        mario.reset();
        map.addMario(mario);

    }

    public void toggleDebugMode(){
        this.map.toggleDebugMode();
    }

}

