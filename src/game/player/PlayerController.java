package game.player;

import java.awt.Toolkit;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.KeyHandler;
import game.MouseHandler;
import game.Animatable.AnimationState;

/* Controller for the player, implementing movement, animation and the players target */
public class PlayerController implements StepListener {
    private final Player player;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public PlayerController(Player player, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.player = player;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    
    
    }

    @Override
    public void preStep(StepEvent e) {


        // Update animation
        if (player.isAnimating()) {
            player.incrementFrameCounter();
        }
        
        if (player.isDying()) {
            return;
        }
        // Move based on keys pressed
        if (keyHandler.leftPressed) {
            player.setLinearVelocity(new Vec2(-7.5f, player.getLinearVelocity().y));
        }
        if (keyHandler.rightPressed) {
            player.setLinearVelocity(new Vec2(7.5f, player.getLinearVelocity().y));
        }
        if (keyHandler.downPressed) {
            if (player.getLinearVelocity().y > -10f) {
                player.applyImpulse(new Vec2(0f, -15f));
        }}
        // use equipment if mouse clicked
        if (mouseHandler.mouseClicked) {
            if (player.hasEquipment()) {
                player.useEquipment(mouseHandler.mousePosition);
                player.startAnimation(AnimationState.ATTACK);
            mouseHandler.mouseClicked = false;
            }
        }
        // Change sprite animation direction to face the mouse/target
        if (mouseHandler.mousePosition.x < player.getPosition().x) {
            player.setFacingLeft(true);
        } else {
            player.setFacingLeft(false);
        }

        // Target logic calculating with some basic vector maths
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
