package game.player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jbox2d.common.Vec2;

import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.Fixture;
import city.cs.engine.GhostlyFixture;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;



public class Target extends StaticBody{
    private BodyImage image = new BodyImage("data/art/target.png", 1.5f);
    private Player player;

    public Target(World world, Player player) {
        super(world);
        GhostlyFixture fixture = new GhostlyFixture(this, new CircleShape(1.0f));
        this.addImage(image);
        this.player = player;
    }
    
}
