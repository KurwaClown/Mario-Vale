public abstract class PowerUp extends GameObject{

    public PowerUp(String spriteName) {
        super(-100, -3000, spriteName);
    }

    public void setLocation(Block block){
        x= block.x;
        y= block.y -96;
    }

    public void disappear(){
        y = 3000;
    }
}
