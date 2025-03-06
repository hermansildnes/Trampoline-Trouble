package game.player.equipments;

import java.awt.Image;
import javax.swing.ImageIcon;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.player.Player;

public class LaserGun extends Equipment {

    public LaserGun(Player player) {
        super(player);
        this.ammunition = 5;
        this.icon = new ImageIcon("data/assets/environment/collectibles/lasergun.png").getImage();
    
    }

    @Override
    public void use(Vec2 mousePosition) {
        ammunition--;
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
