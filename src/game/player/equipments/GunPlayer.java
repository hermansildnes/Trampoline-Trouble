package game.player.equipments;

import org.jbox2d.common.Vec2;

import city.cs.engine.World;
import game.player.Player;

public class GunPlayer extends Player {

    public GunPlayer(World world, Vec2 position) {
        super(world, position);
    }


    public void shoot(Vec2 mousePosition) {
        System.out.println("Shooting at " + mousePosition);
        Vec2 playerPosition = this.getPosition();
        Vec2 direction = mousePosition.sub(playerPosition);
        direction.normalize();
    }
    
}
