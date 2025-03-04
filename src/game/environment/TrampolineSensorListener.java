package game.environment;

import org.jbox2d.common.Vec2;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.enemy.Enemy;
import game.player.Player;

public class TrampolineSensorListener implements SensorListener {

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player) {
            Player player = (Player) e.getContactBody();
            
            // Only trigger the jump if the player is moving downward
            if (player.getLinearVelocity().y <= 0) {
                player.jump(200);
                
                // Debug to verify collisions
                System.out.println("Trampoline collision detected with player");
            }
        }
        if (e.getContactBody() instanceof Enemy) {
            Enemy enemy = (Enemy) e.getContactBody();
            if (enemy.getLinearVelocity().y <= 0) {
                enemy.jump(200);
                
                // Debug to verify collisions
                System.out.println("Trampoline collision detected with enemy");
            }
        }
    }

    @Override
    public void endContact(SensorEvent arg0) {
    }
}