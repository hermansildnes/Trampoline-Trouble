package game.player.equipments;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.player.Player;
import game.worlds.Level;
import java.util.ArrayList;

public class GunPlayer extends Player {
    private ArrayList<Enemy> enemies;

    public GunPlayer(Level world, Vec2 position) {
        super(world, position);
        this.enemies = world.getEnemies();
    }


    public void shoot(Vec2 mousePosition) {
        float enemyRadius = 1.0f;
        System.out.println("Shooting at " + mousePosition);
        Vec2 playerPosition = this.getPosition();
        Vec2 direction = mousePosition.sub(playerPosition);
        direction.normalize();
        for (Enemy enemy : enemies) {
            Vec2 enemyPosition = enemy.getPosition();
            Vec2 enemyDistance = enemyPosition.sub(playerPosition);
            float perpDistance = Vec2.cross(enemyDistance, direction);
            if (Math.abs(perpDistance) <= enemyRadius) {
                float distanceToEnemy = Vec2.dot(enemyDistance, direction);
                if (distanceToEnemy > 0) {
                    System.out.println("Hit enemy at " + enemyPosition);
                    enemy.destroy();
                    break;
                }

        
            }
        }
    }
}
