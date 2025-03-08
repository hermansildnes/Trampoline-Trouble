package game.environment;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.enemy.Enemy;
import game.player.Player;

/* 
 * Trigger jump when an entity collides with the sensor right above the trampoline
 */
public class TrampolineSensorListener implements SensorListener {

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player player) {
            player.jump(20);
                
        }
        if (e.getContactBody() instanceof Enemy enemy) {
            enemy.jump(15);
            }
    }

    @Override
    public void endContact(SensorEvent arg0) {
    }
}