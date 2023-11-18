package gameobject.block;

import view.Ressource;

public class Pipe extends Block{

    public Pipe(int x, int y, boolean isTop) {
        super(x, y, "pipeBase");
        if(isTop) setSprite(Ressource.getImage("pipeTop"));
    }

}
