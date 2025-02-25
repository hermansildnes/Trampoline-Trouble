package game.environment.collectibles;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.player.Player;

public class CollectibleSensorListener implements SensorListener {
    private Collectible collectible;

    public CollectibleSensorListener(Collectible collectible) {
        this.collectible = collectible;
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player) {
            ((Player)e.getContactBody()).setEquipment(collectible.getCollectibleType());
            collectible.destroy();
            }
        }


    @Override
    public void endContact(SensorEvent e) {
    }
}
