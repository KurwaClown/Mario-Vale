package gameobject.collectible;

import gameobject.character.Mario;

public class Brennus extends PowerUp {
    public Brennus() {
        super("brennus");
    }

    @Override
    public void onTouch(Mario mario) {
        super.onTouch(mario);
        mario.addScore(600);
    }
}

