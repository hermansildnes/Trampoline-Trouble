package game;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

public class Trampoline extends StaticBody {

    public Trampoline(World world, StaticBody ground) {
        final Shape trampolineShape = new BoxShape(2f, 0.15f);
        super(world, trampolineShape);
        this.addImage(new BodyImage("data/trampoline.png", 4));
        this.putOn(ground);
    }
    public Trampoline(World world, float xPos, StaticBody ground) {
        final Shape trampolineShape = new BoxShape(2f, 0.15f);
        super(world, trampolineShape);
        this.addImage(new BodyImage("data/trampoline.png", 4));
        this.putOn(xPos, ground);
    }
    public Trampoline(World world, Vec2 position) {
        final Shape trampolineShape = new BoxShape(2f, 0.15f);
        super(world, trampolineShape);
        this.addImage(new BodyImage("data/trampoline.png", 4));
        this.setPosition(position);
    }

    public void putOn(StaticBody ground) {
        this.setPosition(ground.getPosition().add(new Vec2(0, 2f)));
    }
    
    public void putOn(float xPos, StaticBody ground) {
        this.setPosition(new Vec2(xPos, ground.getPosition().y + 2f));
    }
}
