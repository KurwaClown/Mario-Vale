package gameobject.block;

import view.Resource;

public class Pipe extends Block{

    public Pipe(int x, int y, boolean isTop) {
        super(x, y, "pipeBase");
        if(isTop) setSprite(Resource.getImage("pipeTop"));
    }

}
