package game.environment;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.Animatable.AnimationState;
import game.enemy.Enemy;
import game.player.Player;

/*
 * Start the jump animation right before an entity collides with the trampoline
 */
public class JumpAnimationListener implements SensorListener {

    public JumpAnimationListener() {
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player player) {
            if (player.getLinearVelocity().y <= 0) {
                player.startAnimation(AnimationState.JUMP);
            }
        }
        else if (e.getContactBody() instanceof Enemy enemy) {
            if (enemy.getLinearVelocity().y <= 0) {
                enemy.startAnimation(AnimationState.JUMP);
            }
        }
    }

    @Override
    public void endContact(SensorEvent arg0) {
    }
}