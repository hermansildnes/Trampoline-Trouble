package game.environment.collectibles;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.Sensor;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import game.worlds.Level;

public class Collectible extends StaticBody{
    private static final Shape shape = new CircleShape(1f);
    private Sensor sensor;
    public enum CollectibleType {
        LASERGUN,
    }
    private CollectibleType collectibleType;

    public Collectible(Level world, Vec2 position, CollectibleType type) {
        super(world);
        sensor = new Sensor(this, shape);
        sensor.addSensorListener(new CollectibleSensorListener(this));
        this.setPosition(position);
        this.collectibleType = type;
        this.addImage(new BodyImage("data/assets/environment/collectibles/" + collectibleType.toString().toLowerCase() + ".png", 4));
    }

    public CollectibleType getCollectibleType() {
        return collectibleType;
    }
}
