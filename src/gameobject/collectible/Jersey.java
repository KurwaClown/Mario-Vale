package gameobject.collectible;

import gameobject.character.Mario;

public class Jersey extends PowerUp{
    public Jersey() {
        super("jersey");
    }

    @Override
    public void onTouch(Mario mario) {
        super.onTouch(mario);
        mario.addScore(500);
    }
}
