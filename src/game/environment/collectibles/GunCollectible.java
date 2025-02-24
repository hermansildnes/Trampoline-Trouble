package game.environment.collectibles;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import game.worlds.Level;

public class GunCollectible extends Collectible {
    private static final BodyImage image = new BodyImage("data/assets/environment/collectibles/collectible_gun.png", 4f);
    public GunCollectible(Level world, Vec2 position) {
        super(world, position);
        this.addImage(image);
    }
    
}
