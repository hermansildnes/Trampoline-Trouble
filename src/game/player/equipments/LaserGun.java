package game.player.equipments;

import javax.swing.ImageIcon;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.player.Player;

public class LaserGun extends Equipment {

    public LaserGun(Player player) {
        super(player);
        this.ammunition = 30;
        this.icon = new ImageIcon("data/assets/environment/collectibles/lasergun.png").getImage();
    
    }

    @Override
    public void use(Vec2 mousePosition) {
        ammunition--;
        float enemyRadius = 1.0f;

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
                    enemy.decreaseHealth(1);
                    break;
                }
            }
        }
    }

}
