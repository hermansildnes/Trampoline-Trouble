package game.worlds;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.enemy.EnemyController;
import game.environment.Ground;
import game.environment.Trampoline;
import game.environment.collectibles.Collectible;
import game.player.Player;

public class Level1 extends Level {
    

    public Level1() {
        super();
        Ground ground = new Ground(this, new Vec2(0f, -14f));
        this.enemies = new ArrayList<Enemy>();

        this.player = new Player(this, new Vec2(0, -8));        
        //this.target = new Target(this);


        this.collectibles.add(new Collectible(this, new Vec2(10, -5), Collectible.CollectibleType.LASERGUN));

        this.trampolines = new ArrayList<Trampoline>();
        trampolines.add(new Trampoline(this, ground));
        trampolines.add(new Trampoline(this, 10, ground));
        trampolines.add(new Trampoline(this, -10, ground));

        ArrayList<EnemyController> enemyControllers = new ArrayList<EnemyController>();
        enemies.add(new Enemy(this, new Vec2(-10, 0)));
        for (Enemy enemy : enemies) {
            EnemyController enemyController = new EnemyController(enemy);
            enemyControllers.add(enemyController);
            this.addStepListener(enemyController);
        }

    }
}
