package game.environment;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.enemy.Enemy;
import game.player.Player;

public class TrampolineSensorListener implements SensorListener {

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player) {
            Player player = (Player) e.getContactBody();
            player.jump(20);
                
        }
        if (e.getContactBody() instanceof Enemy) {
            Enemy enemy = (Enemy) e.getContactBody();
            enemy.jump(15);   
            }
    }

    @Override
    public void endContact(SensorEvent arg0) {
    }
}