package game.player;

import java.awt.Toolkit;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.UserView;
import game.KeyHandler;
import game.MouseHandler;
import game.Animatable.AnimationState;

public class PlayerController implements StepListener {
    private Player player;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private UserView view;

    public PlayerController(Player player, KeyHandler keyHandler, MouseHandler mouseHandler, UserView view) {
        this.player = player;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.view = view;
    
    
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
            if (player.getLinearVelocity().y > -10f) {
                player.applyImpulse(new Vec2(0f, -15f));
        }}
        if (mouseHandler.mouseClicked) {
            if (player.hasEquipment()) {
                player.useEquipment(mouseHandler.mousePosition);
                player.startAnimation(AnimationState.ATTACK);
            mouseHandler.mouseClicked = false;
            }
        }

        if (mouseHandler.mousePosition.x < player.getPosition().x) {
            player.setFacingLeft(true);
        } else {
            player.setFacingLeft(false);
        }

        if (player.isAnimating()) {
            player.incrementFrameCounter();
        }

        // Target logic
        Vec2 playerPos = player.getPosition();
        Vec2 difference = mouseHandler.mousePosition.sub(playerPos);
        if (difference.length() > 8) {
            difference.normalize();
            difference.mulLocal(8);
        }
        player.getTarget().setPosition(playerPos.add(difference));

    }

    @Override
    public void postStep(StepEvent e) {
        Toolkit.getDefaultToolkit().sync();
    }
    
}
