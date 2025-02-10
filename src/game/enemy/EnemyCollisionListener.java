package game.enemy;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.environment.Ground;
import game.environment.Trampoline;

public class EnemyCollisionListener implements CollisionListener {
    private Enemy enemy;

    public EnemyCollisionListener(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void collide(CollisionEvent e) {

        if (e.getOtherBody() instanceof Trampoline) {
            enemy.jump(20);
        }

        if (e.getOtherBody() instanceof Ground) {
            enemy.destroy();
        }
    }
}