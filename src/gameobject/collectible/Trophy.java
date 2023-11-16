package gameobject.collectible;

import gameobject.character.Mario;
import gameobject.collectible.PowerUp;

public class Trophy extends PowerUp{
    public Trophy() {
        super("trophy");
    }

    @Override
    public void onTouch(Mario mario) {
        super.onTouch(mario);
        mario.addScore(1000);
    }
}
