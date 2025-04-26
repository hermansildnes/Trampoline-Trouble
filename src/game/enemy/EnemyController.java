package game.enemy;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.environment.Trampoline;
import game.player.Player;

/*
 * Controller for the enemy. Handles enemy movement and pathfinding.
 * Prioritises pathfinding to nearest trampoline if necessary for survival,
 * otherwise pathfinds to the player
 */

public class EnemyController implements StepListener {
    private final Enemy enemy;
    private final Player player;
    private final ArrayList<Trampoline> trampolines;
    private Trampoline closestTrampoline;

    private final float movementSpeed = 4f;
    private final float smoothFactor = 0.25f;
    float targetVelocity;

        public EnemyController(Enemy enemy) {
            this.enemy = enemy;
            this.player = enemy.getWorld().getPlayer();
            this.trampolines = enemy.getWorld().getTrampolines();
        }

    @Override
    public void preStep(StepEvent e) {
        
        if (enemy.isAnimating()) {
            enemy.incrementFrameCounter();
        }

        if (enemy.isDying) {
            return;
        }

        enemy.update(e);

        // Sets the position of the healthbar right above the enemy
        enemy.updateHealthBarPositions();
        
        // Set animation direction and update animation
        if (enemy.getPosition().x < player.getPosition().x) {
            enemy.setFacingLeft(false);
        } else {
            enemy.setFacingLeft(true);
        }


        // Calculate closest trampoline
        for (Trampoline trampoline: trampolines) {
            if (closestTrampoline == null) {
                closestTrampoline = trampoline;
            } else {
                if (Math.abs(trampoline.getPosition().x - enemy.getPosition().x) < Math.abs(closestTrampoline.getPosition().x - enemy.getPosition().x)) {
                    closestTrampoline = trampoline;
                }
            }
        }

        // Calculate time until reached trampoline.position.y
        float distanceToTrampoline = Math.abs(enemy.getPosition().y - closestTrampoline.getPosition().y);
        double timeToReachTrampolineY = distanceToTrampoline / enemy.getWorld().getGravity();
        // Calculate time needed to reach trampoline.position.x
        float distanceToTrampolineX = Math.abs(enemy.getPosition().x - closestTrampoline.getPosition().x);
        double timeToReachTrampolineX = distanceToTrampolineX / this.movementSpeed;

        // More aggressive stuck detection - check for minimal movement
        boolean isStuck = Math.abs(enemy.getLinearVelocity().x) < 0.8f && 
                        Math.abs(enemy.getLinearVelocity().y) < 0.8f;

        boolean stuckOnTrampoline = false;
        if (closestTrampoline != null && isStuck) {
            // If close to the closest trampoline and stuck
            if (Math.abs(closestTrampoline.getPosition().x - enemy.getPosition().x) < 2.0f && Math.abs(closestTrampoline.getPosition().y - enemy.getPosition().y) < 2.0f) {
                stuckOnTrampoline = true;
                
                // Determine which side of the trampoline the enemy is on
                float dx = enemy.getPosition().x - closestTrampoline.getPosition().x;
                float dy = enemy.getPosition().y - closestTrampoline.getPosition().y;
                
                // Apply force away from the trampoline
                float nudgeX = dx == 0 ? (Math.random() > 0.5 ? 3.0f : -3.0f) : Math.signum(dx) * 3.0f;
                float nudgeY = dy >= 0 ? 6.0f : -1.0f; // Push up if above, down if below
                
                // Apply a strong impulse to escape
                enemy.applyImpulse(new Vec2(nudgeX, nudgeY));
                enemy.setLinearVelocity(new Vec2(nudgeX * 2, nudgeY));
            }
        }

        // Skip normal movement if we're unsticking from a trampoline to avoid negative feedback loop
        if (stuckOnTrampoline) {
            return;
        }

        // Pathfind to nearest trampoline if necessary to survive
        if (timeToReachTrampolineY <= timeToReachTrampolineX+0.3 && enemy.getLinearVelocity().y <= 0) {
            if (enemy.getPosition().x < closestTrampoline.getPosition().x) {
                enemy.setLinearVelocity(new Vec2(this.movementSpeed, enemy.getLinearVelocity().y));
            } else if (enemy.getPosition().x > closestTrampoline.getPosition().x) {
                enemy.setLinearVelocity(new Vec2(-this.movementSpeed, enemy.getLinearVelocity().y));
            }
            // Otherwise, pathfind to player
        } else if (enemy.getPathFinding()) {
            if (enemy.getPosition().x < player.getPosition().x) {
                targetVelocity = this.movementSpeed;
            } else if (enemy.getPosition().x > player.getPosition().x) {
                targetVelocity = -this.movementSpeed;
            }
        }
        // Apply linear interpolation smoothing to make enemy not get stuck on player location
        float smoothedVelocityX = enemy.getLinearVelocity().x + (targetVelocity - enemy.getLinearVelocity().x) * smoothFactor;
        enemy.setLinearVelocity(new Vec2(smoothedVelocityX, enemy.getLinearVelocity().y));
 
}

    @Override
    public void postStep(StepEvent e) {
    }
    
}
