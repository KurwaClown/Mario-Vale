package gameobject.collectible;

import gameobject.character.Mario;

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
