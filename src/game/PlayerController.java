package game;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;

public class PlayerController implements StepListener {
    private Player player;
    private KeyHandler keyHandler;

    public PlayerController(Player player, KeyHandler keyHandler) {
        this.player = player;
        this.keyHandler = keyHandler;
    
    }

    @Override
    public void preStep(StepEvent e) {
        if (keyHandler.aPressed) {
            player.setLinearVelocity(new Vec2(-7.5f, player.getLinearVelocity().y));
        }
        if (keyHandler.dPressed) {
            player.setLinearVelocity(new Vec2(7.5f, player.getLinearVelocity().y));
        }
        if (keyHandler.sPressed) {
            player.applyImpulse(new Vec2(0,-15f));
        }
    }

    @Override
    public void postStep(StepEvent e) {
    }
    
}
