// Creating a Class that will move as Mario go ahead
public class Camera {
    private double x = 0;
    private double y = 0;

    public Camera() {

    }
    public void moveCam(double xOffset, double yOffset){
        x = x + xOffset;
        y = y + yOffset;

        //System.out.println("Camera location = (" + x + ", " + y + ")");
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void reset(){
        x = 0;
        y = 0;
    }
}
