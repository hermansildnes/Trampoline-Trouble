package game.enemy;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.environment.Ground;
import game.player.Player;

/**
 * Destroys enemy if it collides with the ground, and pauses
 * pathfinding for a second if it collides with the player
 */
public class EnemyCollisionListener implements CollisionListener {
    private final Enemy enemy;

    public EnemyCollisionListener(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void collide(CollisionEvent e) {

        // Pause pathfinding for one second
        if (e.getOtherBody() instanceof Player) {
            enemy.setPathFinding(false);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            enemy.setPathFinding(true);
                        }
                    },
                    1500
            );
        }

        if (e.getOtherBody() instanceof Ground) {
            enemy.die();
        }
    }
}