public class Turtle extends GameObject{
    public int xInit;
    private int direction = 1;
    private int hp;
    public Turtle (double xLocation, double yLocation) {
        super(xLocation, yLocation, "turtle");
                 this.hp = 2;
    }
//    public void move(Block block){
//        x += direction* velX;
//         if (this.collide(block)){
//            direction = direction *(-1);
//         }
//    }
//    public void shell(Mario mario){
//        if (mario.collideFromTop(this) && hp==2){
//            sprite = Ressource.getImage("shell");
//            hp -=1;
//        }
//        else if (mario.collideFromTop(this) && hp==1){
//            this.y = 3000;
//        }
//    }
}

