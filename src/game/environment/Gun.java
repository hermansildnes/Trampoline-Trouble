package game.environment;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import game.worlds.Level;

public class Gun extends Collectible{
    public Gun(Level world) {
        super(world, new CircleShape(0.5f));
        this.addImage(new BodyImage("data/collectibles/gun.png", 0.5f));
    }
}
