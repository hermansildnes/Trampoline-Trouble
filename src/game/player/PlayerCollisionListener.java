package game.player;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.enemy.Enemy;
import game.environment.Collectible;
import game.environment.Ground;
import game.environment.Trampoline;
import game.worlds.Level;

public class PlayerCollisionListener implements CollisionListener {
    private Player player;

    public PlayerCollisionListener(Player player) {
        this.player = player;
    }

    @Override
    public void collide(CollisionEvent e) {

        if (e.getOtherBody() instanceof Enemy) {
           //player.destroy();
        }

        if (e.getOtherBody() instanceof Trampoline) {
            player.jump(20);

        }

        if (e.getOtherBody() instanceof Ground) {
            player.destroy();
            ((Level)player.getWorld()).getTarget().destroy();
        }
        if (e.getOtherBody() instanceof Collectible) {
            //player.attatchCollectible(e.getOtherBody());
            e.getOtherBody().destroy();

        }
    }
}