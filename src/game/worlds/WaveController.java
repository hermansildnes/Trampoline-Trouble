package game.worlds;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.enemy.Enemy;
import game.enemy.EnemyController;
import game.environment.collectibles.Collectible;
import game.environment.collectibles.Collectible.CollectibleType;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaveController implements StepListener {
    private Level level;
    private ArrayList<Enemy> enemies;
    private int currentWave = 10;
    private float timer = 0.01F;
    private static final float WAVE_INTERVAL = 15f;
    private Random random = new Random();
    
    private float collectibleChance = 0.75f; // 50% chance per wave to spawn a collectible
    
    public WaveController(Level level) {
        this.level = level;
        this.enemies = level.getEnemies();
    }
    
    @Override
    public void preStep(StepEvent e) {
        timer += e.getStep();
        enemies = level.getEnemies();

        if (timer >= WAVE_INTERVAL) {
            currentWave++;
            System.out.println("Wave " + currentWave + " has started!");
            spawnWave();
            timer = 0;
        }
            if (timer % 2<=0.1f) {
                if (enemies != null) {
                    System.out.println(timer);
                }
        }
    }
    
    @Override
    public void postStep(StepEvent e) {
        // Not needed for this implementation
    }

    private void spawnWave() {
        int enemiesToSpawn = 2;
        spawnEnemies(enemiesToSpawn);
        
        if (random.nextFloat() < collectibleChance) {
        spawnCollectibles();
        }
    }

    private void spawnEnemies(int enemiesToSpawn) {
        for (int i = 0; i < enemiesToSpawn; i++) {
            float xPos = random.nextInt(20) - 10;
            Enemy enemy = new Enemy(level, new Vec2(random.nextInt(20) - 10, 10));
            level.addStepListener(new EnemyController(enemy));
            enemies.add(enemy);
        }
    }

    private void spawnCollectibles() {
        Collectible collectible = new Collectible(level, new Vec2(random.nextInt(20) - 10, 10), CollectibleType.LASERGUN);
    }
}