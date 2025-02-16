package game.environment;

import org.jbox2d.common.Vec2;

import city.cs.engine.BoxShape;
import city.cs.engine.Sensor;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

public class TrampolineAnimationTrigger extends StaticBody {
    private Sensor sensor;

    public TrampolineAnimationTrigger(World world, Trampoline trampoline) {
        super(world);
        sensor = new Sensor(this, new BoxShape(2f, 1f));
        sensor.addSensorListener(new TrampolineSensorListener(this));
        this.setPosition(trampoline.getPosition().add(new Vec2(0f, 0.5f)));
    }
    
}
