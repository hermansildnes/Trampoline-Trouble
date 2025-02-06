package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

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