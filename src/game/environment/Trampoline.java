package game.environment;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

public class Trampoline extends StaticBody {
    private static final Shape trampolineShape = new BoxShape(2f, 0.15f);
    private TrampolineAnimationTrigger animationTrigger;

    public Trampoline(World world) {
        super(world, trampolineShape);
        this.addImage(new BodyImage("data/assets/environment/trampoline.png", 4));
        animationTrigger = new TrampolineAnimationTrigger(world, this);
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
        animationTrigger.setPosition(position.add(new Vec2(0, 0.5f)));
        }
}
