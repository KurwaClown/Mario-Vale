package gameobject.collectible;

import gameobject.GameObject;
import gameobject.block.Block;
import gameobject.character.Mario;

import java.awt.*;

public interface Collectible {

    void onTouch(Mario mario);

    void setLocation(Block Block);

    void draw(Graphics g);

    Rectangle getHitbox();
}
