package game.worlds;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import game.Game;
import game.enemy.Enemy;
import game.environment.Ground;
import game.environment.Trampoline;
import game.environment.collectibles.Collectible;
import game.environment.collectibles.Collectible.CollectibleType;
import game.player.Player;


/* First level specifying where ground, trampolines etc should be placed and 
 * equips the player with a gun.
 */
public class Level1 extends Level {


    public Level1(Game game) {
        super(1, game);
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
        this.waveController = new WaveController(this);
        this.addStepListener(waveController);

    }

}
