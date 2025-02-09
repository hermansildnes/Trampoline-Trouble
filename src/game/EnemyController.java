package game;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;

public class EnemyController implements StepListener {
    private Enemy enemy;
    private ArrayList<Trampoline> trampolines;
    private Trampoline closestTrampoline;
    private Player player;
    private float movementSpeed = 5f;


    // Experimental
    private float currentVelocityX = 0;
    private float acceleration = 2.5f;
    private float smoothFactor = 0.25f;

    public EnemyController(Enemy enemy) {
        this.enemy = enemy;
        this.trampolines = enemy.trampolines;
        this.player = enemy.player;
    
    }

    @Override
    public void preStep(StepEvent e) {
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
        double timeToReachTrampolineY = distanceToTrampoline / enemy.gravity;
        // Calculate time needed to reach trampoline.position.x
        float distanceToTrampolineX = Math.abs(enemy.getPosition().x - closestTrampoline.getPosition().x);
        double timeToReachTrampolineX = distanceToTrampolineX / this.movementSpeed;

        // Experimental
        float targetVelocity = 0;

        if (timeToReachTrampolineY <= timeToReachTrampolineX+0.3 && enemy.getLinearVelocity().y <=d 0) {
            if (enemy.getPosition().x < closestTrampoline.getPosition().x) {
                targetVelocity = this.movementSpeed;
            } else if (enemy.getPosition().x > closestTrampoline.getPosition().x) {
                targetVelocity = -this.movementSpeed;
            }
        } else {
            if (enemy.getPosition().x < player.getPosition().x) {
                targetVelocity = this.movementSpeed;
            } else if (enemy.getPosition().x > player.getPosition().x) {
                targetVelocity = -this.movementSpeed;
            }
        }

        // Smooth velocity transition
        float deltaVelocity = targetVelocity - currentVelocityX;
        currentVelocityX += deltaVelocity * acceleration * e.getStep();
        
        // Linear interpolation smoothing
        float smoothedVelocityX = currentVelocityX + (targetVelocity - currentVelocityX) * smoothFactor;
        
        enemy.setLinearVelocity(new Vec2(smoothedVelocityX, enemy.getLinearVelocity().y));
    }

    @Override
    public void postStep(StepEvent e) {
    }
    
}
