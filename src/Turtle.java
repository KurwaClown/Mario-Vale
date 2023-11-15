public class Turtle extends Enemy{
    private boolean isShell = false;

    public Turtle (double xLocation, double yLocation) {
        super(xLocation, yLocation, "turtle");

    }

    @Override
    public void attacked(){
        if (!isShell){
            transformInShell();
        }
        else if(Math.abs(this.getVelX()) < 8){
            this.setVelX(8);
        } else {
            this.setVelX(0);
        }
    }

    public void transformInShell(){
        setSprite( Ressource.getImage("shell"));
        isShell = true;
        setVelX(0);
    }
}

