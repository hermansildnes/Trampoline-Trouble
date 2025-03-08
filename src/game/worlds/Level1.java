package game.worlds;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.environment.Ground;
import game.environment.Trampoline;
import game.environment.collectibles.Collectible;
import game.environment.collectibles.Collectible.CollectibleType;
import game.player.Player;

public class Level1 extends Level {

    private WaveController waveController;

    public Level1() {
        super();
        Ground ground = new Ground(this, new Vec2(0f, -14f));
        this.trampolines = new ArrayList<Trampoline>();
        trampolines.add(new Trampoline(this, ground));
        trampolines.add(new Trampoline(this, 10, ground));
        trampolines.add(new Trampoline(this, -10, ground));

        this.enemies = new ArrayList<Enemy>();
        this.collectibles = new ArrayList<Collectible>();
        this.player = new Player(this, new Vec2(0, -8));
        player.setEquipment(CollectibleType.LASERGUN);

        // Initialize and add the wave controller
        waveController = new WaveController(this);
        this.addStepListener(waveController);

    }

}
