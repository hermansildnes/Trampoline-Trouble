package game.player.equipments;

import javax.swing.ImageIcon;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.player.Player;

/*
 * Specifies ammunition and how to use LaserGun collectible
 */
public class LaserGun extends Equipment {

    public LaserGun(Player player) {
        super(player);
        this.ammunition = 15;
        this.icon = new ImageIcon("data/assets/environment/collectibles/lasergun.png").getImage();
    
    }

    @Override
    public void use(Vec2 mousePosition) {
        ammunition--;
        float enemyRadius = 1.0f;
        // Calculate vector from player to mouse and normalize it
        Vec2 playerPosition = player.getPosition();
        Vec2 direction = mousePosition.sub(playerPosition);
        direction.normalize();
        
        // Check if the laser hits any enemy
        for (Enemy enemy : enemies) {
            // Calculate vector from player to enemy
            Vec2 enemyPosition = enemy.getPosition();
            Vec2 enemyDistance = enemyPosition.sub(playerPosition);
            // Calculate perpendicular distance from player to enemy
            float perpDistance = Vec2.cross(enemyDistance, direction);
            
            // Check if laser hits enemy
            if (Math.abs(perpDistance) <= enemyRadius) {
                // Check if enemy is in front of player
                float distanceToEnemy = Vec2.dot(enemyDistance, direction);
                if (distanceToEnemy > 0) {
                    enemy.decreaseHealth(1);
                    break;
                }
            }
        }
    }
    public void addAmmo(int ammo) {
        this.ammunition += ammo;
    }

}
