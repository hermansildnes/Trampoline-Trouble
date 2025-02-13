package game.environment;

import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import game.worlds.Level;

public class Collectible extends StaticBody{

    public Collectible(Level world, Shape shape) {
        super(world, shape);
    }
}
