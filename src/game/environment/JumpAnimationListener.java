package game.environment;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.player.Player;
import game.player.Player.AnimationState;

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
    }

    @Override
    public void endContact(SensorEvent arg0) {
    }
}