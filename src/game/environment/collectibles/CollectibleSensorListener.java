package game.environment.collectibles;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.player.Player;


/*
 * Trigger the collection of a collectible when the player collides with it and destroy the collectible
 */
public class CollectibleSensorListener implements SensorListener {
    private final Collectible collectible;

    public CollectibleSensorListener(Collectible collectible) {
        this.collectible = collectible;
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player) {
            ((Player)e.getContactBody()).setEquipment(collectible.getCollectibleType());
            ((Player)e.getContactBody()).getWorld().getGame().getAudioManager().playSoundEffect("collect");
            collectible.destroy();
            }
        }


    @Override
    public void endContact(SensorEvent e) {
    }
}
