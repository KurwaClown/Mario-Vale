package gameobject.collectible;

import gameobject.character.Mario;

public class Ball extends PowerUp{
    public Ball() {
        super("ball");
    }

    @Override
    public void onTouch(Mario mario) {
        super.onTouch(mario);
        mario.addScore(750);
    }
}
