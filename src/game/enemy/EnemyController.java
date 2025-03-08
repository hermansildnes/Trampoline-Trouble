package game.enemy;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.environment.Trampoline;
import game.player.Player;

public class EnemyController implements StepListener {
    private Enemy enemy;
    private Player player;
    private ArrayList<Trampoline> trampolines;
    private Trampoline closestTrampoline;

    // Experimental
    private float movementSpeed = 4f;
    private float smoothFactor = 0.25f;    
    float targetVelocity = 0;

        public EnemyController(Enemy enemy) {
            this.enemy = enemy;
            this.player = enemy.getWorld().getPlayer();
            this.trampolines = enemy.getWorld().getTrampolines();
        }

    @Override
    public void preStep(StepEvent e) {
        enemy.getHealthbar().setPosition(enemy.getPosition().add(new Vec2(0, 2f)));
        
        // Set animation direction and update animation
        if (enemy.getPosition().x < player.getPosition().x) {
            enemy.setFacingLeft(false);
        } else {
            enemy.setFacingLeft(true);
        }

        if (enemy.isAnimating()) {
            enemy.incrementFrameCounter();
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

        // Pathfind to nearest trampoline if neccessarry to survive
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

        float smoothedVelocityX = enemy.getLinearVelocity().x + (targetVelocity - enemy.getLinearVelocity().x) * smoothFactor;
        enemy.setLinearVelocity(new Vec2(smoothedVelocityX, enemy.getLinearVelocity().y));
 
}

    @Override
    public void postStep(StepEvent e) {
    }
    
}
