package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.Player;
import city.cs.engine.Walker;

public class PlayerCollisionListener implements CollisionListener {
    private Player player;

    public PlayerCollisionListener(Player player) {
        this.player = player;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Walker) {
            player.destroy();
        }

        if (e.getOtherBody() instanceof Trampoline) {
            player.jump(10);
        }
    }
}