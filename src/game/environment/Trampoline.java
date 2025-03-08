package game.environment;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.PolygonShape;
import city.cs.engine.Sensor;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
/* 
 * Trampoline class that specifies a trampoline staticbody, with a sensor 
 * attatched that triggers the jump animation, and a sensor that triggers jumping
 */
public class Trampoline extends StaticBody {
    // Obtained using the polygon editor
    private static final Shape trampolineShape = new PolygonShape(
            -2.0f, -0.5f,
            -1.8f, -0.15f,
            -1.5f, 0.1f,
            0.0f, 0.3f,
            1.5f, 0.1f,
            1.8f, -0.15f,
            2.0f, -0.5f
    );
    // Sensor shape for the sensors
    private static final Shape sensorShape = new BoxShape(1.8f, 0.2f);

    // Add a sensor above the trampoline that triggers jump animation
    private final StaticBody animationTrigger;

    // Add a sensor at the top of the trampoline for more reliable jump detection
    private final StaticBody topSensorBody;

    public Trampoline(World world) {
        super(world, trampolineShape);
        this.addImage(new BodyImage("data/assets/environment/trampoline.png", 4));
        
        // Add  sensor for more reliable detection
        topSensorBody = new StaticBody(world);
        Sensor topSensor = new Sensor(topSensorBody, sensorShape);
        topSensor.addSensorListener(new TrampolineSensorListener());

        // Add animation triggering sensor 
        animationTrigger = new StaticBody(world);
        Sensor triggerSensor = new Sensor(animationTrigger, sensorShape);
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

    // Override setPosition to also set the position of the attatched sensors
    @Override
    public void setPosition(Vec2 position) {
        super.setPosition(position);
        topSensorBody.setPosition(this.getPosition().add(new Vec2(0, 0.4f)));
        animationTrigger.setPosition(this.getPosition().add(new Vec2(0, 3f)));

    }
}
