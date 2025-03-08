package game.environment;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

public class Ground extends StaticBody{

    public Ground(World world, Vec2 position) {

        super(world, new BoxShape(20f, 1f));
        this.setPosition(position);
        this.addImage(new BodyImage("data/assets/environment/ground.png", 3f));
    }
}