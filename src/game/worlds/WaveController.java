package game.worlds;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.enemy.Enemy;
import game.enemy.EnemyController;
import game.environment.collectibles.Collectible;
import game.environment.collectibles.Collectible.CollectibleType;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Random;

public class WaveController implements StepListener {
    private Level level;
    private ArrayList<Enemy> enemies;
    private ArrayList<Collectible> collectibles;
    private int currentWave = 0;
    private float timer = 10f;
    private static final float WAVE_INTERVAL = 15f;
    private Random random = new Random();
    private static final float COLLECTIBLE_INTERVAL = 5f;
    private float lastCollectibleSpawnTime = 0f;
    
    
    public WaveController(Level level) {
        this.level = level;
        this.enemies = level.getEnemies();
        this.collectibles = level.getCollectibles();
    }
    
    @Override
    public void preStep(StepEvent e) {
        timer += e.getStep();
        enemies = level.getEnemies();

        if (currentWave <= 4) {
            if (timer - lastCollectibleSpawnTime >= COLLECTIBLE_INTERVAL) {
                spawnCollectibles();
                lastCollectibleSpawnTime = timer;
            }

            if (timer >= WAVE_INTERVAL) {
                currentWave++;
                System.out.println("Wave " + currentWave + " has started!");
                spawnEnemies(1);
                timer = 0;
            } 
        }
    }
    
    @Override
    public void postStep(StepEvent e) {
    }

    private void spawnEnemies(int enemiesToSpawn) {
        for (int i = 0; i < enemiesToSpawn; i++) {
            Enemy enemy = new Enemy(level, new Vec2(random.nextInt(20) - 10, 10));
            level.addStepListener(new EnemyController(enemy));
            enemies.add(enemy);
        }
    }

    private void spawnCollectibles() {
        if (random.nextInt(4) < 3) {
            System.out.println("Spawning healthpack");
        collectibles.add(new Collectible(level, new Vec2(random.nextInt(20) - 10, random.nextInt(10)-5), CollectibleType.HEALTHPACK));
        // } else {
        // Collectible collectible = new Collectible(level, new Vec2(random.nextInt(20) - 10, 10), CollectibleType.LASERGUN);
        }
    }
}