package game.environment;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.Animatable.AnimationState;
import game.enemy.Enemy;
import game.player.Player;

public class JumpAnimationListener implements SensorListener {

    public JumpAnimationListener() {
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player) {
            Player player = (Player) e.getContactBody();
            if (player.getLinearVelocity().y <= 0) {
                player.startAnimation(AnimationState.JUMP);
               System.out.println("Trampoline animation triggered");

            }
        }
        else if (e.getContactBody() instanceof Enemy) {
            Enemy enemy = (Enemy) e.getContactBody();
            if (enemy.getLinearVelocity().y <= 0) {
                enemy.startAnimation(AnimationState.JUMP);
               System.out.println("Trampoline animation triggered");

            }
        }
    }

    @Override
    public void endContact(SensorEvent arg0) {
    }
}