package game.environment;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.PolygonShape;
import city.cs.engine.Sensor;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

public class Trampoline extends StaticBody {
    // private static final Shape trampolineShape = new BoxShape(2f, 0.15f);
    private static final Shape trampolineShape = new PolygonShape(
            -2.0f, -0.5f, // bottom left
            -1.8f, -0.15f, // left edge
            -1.5f, 0.1f, // left of center
            0.0f, 0.3f, // center top (slightly higher)
            1.5f, 0.1f, // right of center
            1.8f, -0.15f, // right edge
            2.0f, -0.5f // bottom right
    );

    private static final Shape sensorShape = new BoxShape(1.8f, 0.2f);

    private StaticBody animationTrigger;
    private Sensor triggerSensor;

    // Add a sensor at the top of the trampoline for more reliable jump detection
    private StaticBody topSensorBody;
    private Sensor topSensor;

    public Trampoline(World world) {
        super(world, trampolineShape);
        this.addImage(new BodyImage("data/assets/environment/trampoline.png", 4));
       // Add an additional sensor for more reliable detection
        topSensorBody = new StaticBody(world);
        topSensor = new Sensor(topSensorBody, sensorShape);
        topSensor.addSensorListener(new TrampolineSensorListener());

        animationTrigger = new StaticBody(world);
        triggerSensor = new Sensor(animationTrigger, sensorShape);
        triggerSensor.addSensorListener(new JumpAnimationListener());

 

    }

    public Trampoline(World world, StaticBody ground) {
        this(world);
        this.putOn(ground);
    }

    public Trampoline(World world, float xPos, StaticBody ground) {
        this(world);
        this.putOn(xPos, ground);
    }

    public void putOn(StaticBody ground) {
        this.setPosition(ground.getPosition().add(new Vec2(0, 3f)));
    }

    public void putOn(float xPos, StaticBody ground) {
        this.setPosition(new Vec2(xPos, ground.getPosition().y + 3f));
    }

    @Override
    public void setPosition(Vec2 position) {
        super.setPosition(position);
        topSensorBody.setPosition(this.getPosition().add(new Vec2(0, 0.4f)));
        animationTrigger.setPosition(this.getPosition().add(new Vec2(0, 3f)));

    }
}
