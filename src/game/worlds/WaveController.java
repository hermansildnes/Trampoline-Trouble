package game.worlds;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.enemy.Enemy;
import game.enemy.EnemyController;
import game.enemy.EnemyType;

public abstract class WaveController implements StepListener {
    protected final Level level;
    protected ArrayList<Enemy> enemies = new ArrayList<>();;
    protected final Random random = new Random();

    // Wave configuration
    protected final int waveCount;
    protected final float waveInterval;
    protected final float collectibleInterval;

    // Wave tracking
    protected int currentWave = 0;
    protected float timer = 0;
    protected float lastCollectibleSpawnTime = 0;

    protected Queue<EnemyType> enemyQueue = new LinkedList<>();
    protected float nextSpawnTime = 0;
    protected float spawnInterval = 1.0f;

    public WaveController (Level level, int waveCount, float waveInterval, float collectibleInterval) {
        this.level = level;

        this.waveCount = waveCount;
        this.waveInterval = waveInterval;
        this.collectibleInterval = collectibleInterval;
    }

    @Override
    public void preStep(StepEvent e) {
        timer += e.getStep();

        if (!enemyQueue.isEmpty() && timer >= nextSpawnTime) {
            spawnNextEnemy();
        }

        if (getProgress() >= 1.0f && enemies.isEmpty() && enemyQueue.isEmpty()) {
            level.getGame().victory();   
        }

        if (timer-lastCollectibleSpawnTime >= collectibleInterval) {
            spawnCollectible();
            lastCollectibleSpawnTime = timer;
        }

        if (currentWave < waveCount && enemyQueue.isEmpty() && timer >= waveInterval) {
            startNextWave();
        }
    }

    private void startNextWave() {
        currentWave++;
        WaveConfig config = getWaveConfig(currentWave);    
        queueEnemies(config);

        nextSpawnTime = timer;
        if (currentWave < waveCount) {
            timer = 0;
        }
    }

    private void queueEnemies(WaveConfig config) {
        for (EnemyType type : config .getEnemyCounts().keySet()) {
            int count = config.getEnemyCounts().get(type);
            for (int i = 0; i < count; i++) {
                enemyQueue.add(type);
            }
        }
    }

    private void spawnNextEnemy() {

        EnemyType type = enemyQueue.poll();
        Vec2 position = new Vec2(random.nextInt(20)-10, random.nextInt(10)-5);
        Enemy enemy = type.create(level, position);
        level.addStepListener(new EnemyController(enemy));
        enemies.add(enemy);
        nextSpawnTime += spawnInterval;
    }

    public void removeEnemy(Enemy enemy) {
        if (enemies.contains(enemy)) {
            enemies.remove(enemy);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void postStep(StepEvent e) {
    }

    protected abstract WaveConfig getWaveConfig(int waveNumber);

    protected abstract void spawnCollectible();



    public float getProgress() {
        return Math.max(0, Math.min(1, (currentWave-1 + (timer/waveInterval)) / (float)waveCount)); 
    }
}
