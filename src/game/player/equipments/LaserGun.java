package game.player.equipments;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.player.Player;

public class LaserGun implements Equipment {
    private Player player;
    private ArrayList<Enemy> enemies;

    public LaserGun(Player player) {
        this.player = player;
        this.enemies = player.getWorld().getEnemies();
    }

    @Override
    public void use(Vec2 mousePosition) {
        float enemyRadius = 1.0f;
        System.out.println("Shooting at " + mousePosition);
        Vec2 playerPosition = player.getPosition();
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
                    enemy.decreaseHealth(1);
                    System.out.println("Enemy health: " + enemy.getHealth());
                    break;
                }
            }
        }
    }
}
