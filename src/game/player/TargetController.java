package game.player;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.MouseHandler;

public class TargetController implements StepListener {
    private Player player;
    private Target target;
    private MouseHandler mouseHandler;

    public TargetController(Target target, Player player, MouseHandler mouseHandler) {
        this.target = target;
        this.player = player;
        this.mouseHandler = mouseHandler;
    }

    @Override
    public void preStep(StepEvent e) {
        Vec2 playerPos = player.getPosition();
        Vec2 mousePos = mouseHandler.mousePosition;
        Vec2 difference = mousePos.sub(playerPos);
        if (difference.length() > 8) {
            difference.normalize();
            difference.mulLocal(8);
        }
        target.setPosition(playerPos.add(difference));
    }

    @Override
    public void postStep(StepEvent e) {
    }
    
}
