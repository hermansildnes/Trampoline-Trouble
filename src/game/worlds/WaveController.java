package game.worlds;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.Game;
import game.enemy.Enemy;
import game.enemy.EnemyController;
import game.environment.collectibles.Collectible;
import game.environment.collectibles.Collectible.CollectibleType;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Random;

/* Controls the timing of when to spawn enemies and collectibles */
public class WaveController implements StepListener {
    private final Level level;
    private ArrayList<Enemy> enemies;
    private final ArrayList<Collectible> collectibles;
    private int currentWave = 1;
    private float timer = 10f;
    private static final float WAVE_INTERVAL = 15f;
    private final Random random = new Random();
    private static final float COLLECTIBLE_INTERVAL = 5f;
    private float lastCollectibleSpawnTime = 0f;
    private final int numberOfWaves = 4;
    
    public WaveController(Level level) {
        this.level = level;
        this.enemies = level.getEnemies();
        this.collectibles = level.getCollectibles();
    }
    
    @Override
    public void preStep(StepEvent e) {
        System.out.println();
        if (getProgress() >= 1 && level.getEnemies().isEmpty()) {
            level.getGame().noitfyGameComplete();
        }

        
        timer += e.getStep();
        enemies = level.getEnemies();

        if (currentWave <= numberOfWaves) {
            // Spawn a new collectible
            if (timer - lastCollectibleSpawnTime >= COLLECTIBLE_INTERVAL) {
                spawnCollectibles();
                lastCollectibleSpawnTime = timer;
            }
            // Spawn a new wave of enemies
            if (timer >= WAVE_INTERVAL) {
                System.out.println("Wave " + currentWave + " has started!");
                currentWave++;
                spawnEnemies(1);
                lastCollectibleSpawnTime -= timer;
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
        // 75% Chance spawned collectible is a healthpack, 25% it is a gun/ammo
        if (random.nextFloat() < 0.75f) {
            collectibles.add(new Collectible(level, new Vec2(random.nextInt(20) - 10, random.nextInt(10)-5), CollectibleType.HEALTHPACK));
        } else {
            collectibles.add(new Collectible(level, new Vec2(random.nextInt(20) - 10, random.nextInt(10)-5), CollectibleType.LASERGUN));
        }
    }

    // Calculate the progress from 0-1 of the current level, 
    // based on number of waves and progress through the wave.
    public float getProgress() {
        return Math.max(0, Math.min(1, (currentWave-1 + (timer/WAVE_INTERVAL)) / (float)numberOfWaves)); 
    }
}