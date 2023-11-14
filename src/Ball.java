import java.awt.*;

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
