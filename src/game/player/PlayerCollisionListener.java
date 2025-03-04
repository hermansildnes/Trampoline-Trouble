package game.player;

import org.jbox2d.common.Vec2;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.enemy.Enemy;
import game.environment.Ground;
import game.environment.Trampoline;

public class PlayerCollisionListener implements CollisionListener {
    private Player player;

    public PlayerCollisionListener(Player player) {
        this.player = player;

    }

    @Override
    public void collide(CollisionEvent e) {

        if (e.getOtherBody() instanceof Enemy) {
        }

        // if (e.getOtherBody() instanceof Trampoline) {
        //     player.jump(20);

        // }

        if (e.getOtherBody() instanceof Ground) {
            player.startAnimation(Player.AnimationState.DEATH);
            player.setLinearVelocity(new Vec2(0f,0f));
            player.getTarget().destroy();

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            player.destroy();

                        }
                    },
                    4 * 4 * 16  // frames * steps per frame * milliseconds per step
            );
        }
    }
}
