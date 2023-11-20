package view;

// Creating a Class that will move as Mario go ahead
public class Camera {
    private double x = 0;
    private double y = 0;

    public void moveCam(double xOffset, double yOffset){
        setX(getX() + xOffset);
        setY(getY() + yOffset);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    private void setX(double x) {
        this.x = x;
    }

    private void setY(double y) {
        this.y = y;
    }
    public void reset(){
        setX(0);
        setY(0);
    }
}
