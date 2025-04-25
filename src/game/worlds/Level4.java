package game.worlds;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import game.Game;
import game.environment.Ground;
import game.environment.Trampoline;
import game.environment.collectibles.Collectible.CollectibleType;
import game.player.Player;



public class Level4 extends Level {


    public Level4(Game game) {
        super(4, game);

        // Add the ground
        Ground ground = new Ground(this, new Vec2(0f, -14f));
        
        // Add the trampolines
        this.trampolines = new ArrayList<Trampoline>();
        trampolines.add(new Trampoline(this, ground));
        trampolines.add(new Trampoline(this, 10, ground));
        trampolines.add(new Trampoline(this, -10, ground));

        // Add Player
        this.player = new Player(this, new Vec2(0, -8));
        player.setEquipment(CollectibleType.LASERGUN);

        // Initialize and add the wave controller
        this.waveController = new Level4Controller(this);
        this.addStepListener(waveController);

    }

}
