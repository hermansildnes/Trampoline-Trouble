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
            player.jump(200);
                
        }
        if (e.getContactBody() instanceof Enemy) {
            Enemy enemy = (Enemy) e.getContactBody();
            enemy.jump(20);   
            }
    }

    @Override
    public void endContact(SensorEvent arg0) {
    }
}