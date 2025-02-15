package game.worlds;

import java.awt.Graphics2D;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import game.enemy.Enemy;
import game.enemy.EnemyController;
import game.environment.Trampoline;
import game.player.Player;
import game.player.Target;

public class Level1 extends Level {
    

    public Level1() {
        super();
        StaticBody ground = new StaticBody(this, new BoxShape(50f, 0.5f));
        ground.setPosition(new Vec2(0f, -14.5f));
        //Ground ground = new Ground(world, new Vec2(0f, -10.5f));
        this.player = new Player(this, new Vec2(0, -8));        
        this.target = new Target(this, player);

        this.trampolines = new ArrayList<Trampoline>();
        trampolines.add(new Trampoline(this, ground));
        trampolines.add(new Trampoline(this, 10, ground));
        trampolines.add(new Trampoline(this, -10, ground));

        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        ArrayList<EnemyController> enemyControllers = new ArrayList<EnemyController>();
        enemies.add(new Enemy(this, new Vec2(-10, 0)));
        for (Enemy enemy : enemies) {
            EnemyController enemyController = new EnemyController(enemy);
            enemyControllers.add(enemyController);
            this.addStepListener(enemyController);
        }
    }
}
