package game.player;

import org.jbox2d.common.Vec2;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.enemy.Enemy;
import game.environment.Ground;

/* Deals with some of the collision logic for the player, although some is handled
 * in the trampoline or enemy collisionListeners
 */
public class PlayerCollisionListener implements CollisionListener {
    private final Player player;

    public PlayerCollisionListener(Player player) {
        this.player = player;
        
    }

    @Override
    public void collide(CollisionEvent e) {

        // Take damage if collide with enemy
        if (e.getOtherBody() instanceof Enemy) {            
            player.decreaseHealth(1);
        }

        // Start death animation if collide with ground and destroy player
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
                    4 * 4 * 16  // frames per animation * steps per frame * milliseconds per step
            );
        }
    }
}
