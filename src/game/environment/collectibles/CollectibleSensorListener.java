package game.environment.collectibles;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.player.Player;
import game.player.equipments.GunPlayer;

public class CollectibleSensorListener implements SensorListener {
    private Collectible collectible;

    public CollectibleSensorListener(Collectible collectible) {
        this.collectible = collectible;
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (collectible instanceof GunCollectible) {
            if (e.getContactBody() instanceof Player) {
                ((Player)e.getContactBody()).getGunPlayer();
                collectible.destroy();
            }
        }
    }

    @Override
    public void endContact(SensorEvent e) {
    }
}
