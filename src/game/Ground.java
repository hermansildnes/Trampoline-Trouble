package game;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

public class Ground extends StaticBody{    

    public Ground(World world, Vec2 position) {
        super(world);
        Shape shape = new BoxShape(50f, 0.5f);
        StaticBody ground = new StaticBody(world, shape);
        ground.setPosition(position);
        ground.addImage(new BodyImage("data/ground.png", 2.25f));
    }
}
