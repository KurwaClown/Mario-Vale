package gameobject.collectible;

import gameobject.block.Block;
import gameobject.character.Mario;

public interface Collectible {

    void onTouch(Mario mario);

    void setLocation(Block Block);
}
