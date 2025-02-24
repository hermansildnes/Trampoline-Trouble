package game.player;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.KeyHandler;
import game.MouseHandler;
import game.player.equipments.GunPlayer;

public class PlayerController implements StepListener {
    private Player player;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public PlayerController(Player player, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.player = player;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    
    
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
        if (mouseHandler.mouseClicked) {
            if (player instanceof GunPlayer) {
                ((GunPlayer)player).shoot(mouseHandler.mousePosition);
            mouseHandler.mouseClicked = false;
        }
        }

        if (player.isJumping()) {
            player.incrementFrameCounter();
        }
    }

    @Override
    public void postStep(StepEvent e) {
    }
    
}
