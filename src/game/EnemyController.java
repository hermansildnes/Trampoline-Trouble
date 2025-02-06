package game;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;

public class EnemyController implements StepListener {
    private Enemy enemy;
    private Vec2 position;

    public EnemyController(Enemy enemy) {
        this.enemy = enemy;
    
    }

    @Override
    public void preStep(StepEvent e) {
    }

    @Override
    public void postStep(StepEvent e) {
    }
    
}
