package gameobject.collectible;

import gameobject.character.Mario;

import java.awt.*;

public interface Collectible {
    Rectangle getHitbox();

    void disappear();

    void onTouch(Mario mario);

}
