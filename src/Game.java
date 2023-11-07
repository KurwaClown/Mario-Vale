public class Game {

    private boolean isRunning;
    private final int targetFPS = 60;
    private final int targetFrameTime = 1000 / targetFPS;



    public void gameLoop(){
        long previousTime = System.currentTimeMillis();
        long lag = 0;
        while (isRunning) {
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - previousTime;
            previousTime = currentTime;

            lag += deltaTime;

            while (lag >= targetFrameTime) {
                updateGameLogic();
                lag -= targetFrameTime;
            }

            long sleepTime = targetFrameTime - lag;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void updateGameLogic() {
    }

}
