package game.worlds;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import game.Game;
import game.environment.Ground;
import game.environment.Trampoline;
import game.environment.collectibles.Collectible.CollectibleType;
import game.player.Player;


public class Level1 extends Level {


    public Level1(Game game) {
        super(1, game);

        // Add the ground
        Ground ground = new Ground(this, new Vec2(0f, -14f));

        // Add trampolines
        trampolines = new ArrayList<Trampoline>();
        trampolines.add(new Trampoline(this, ground));
        trampolines.add(new Trampoline(this, 10, ground));
        trampolines.add(new Trampoline(this, -10, ground));


        // Create the player
        this.player = new Player(this, new Vec2(0, -8));
        player.setEquipment(CollectibleType.LASERGUN);

        // Initialize and add the wave controller
        this.waveController = new Level1Controller(this);
        this.addStepListener(waveController);

    }

}
