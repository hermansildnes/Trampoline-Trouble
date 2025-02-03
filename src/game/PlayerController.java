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
            System.out.println("A pressed");
            player.setLinearVelocity(new Vec2(-5f, player.getLinearVelocity().y));
        }
        if (keyHandler.dPressed) {
            System.out.println("D pressed");
            player.setLinearVelocity(new Vec2(5f, player.getLinearVelocity().y));
        }
    }

    @Override
    public void postStep(StepEvent e) {
    }
    
}
