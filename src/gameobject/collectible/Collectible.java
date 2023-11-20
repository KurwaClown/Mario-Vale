package gameobject.collectible;

import gameobject.block.Block;
import gameobject.block.Bonus;
import gameobject.character.Mario;

import java.awt.*;

public interface Collectible {
    Rectangle getHitbox();

    void disappear();

    void onTouch(Mario mario);

    void setLocation(Block Block);
}
