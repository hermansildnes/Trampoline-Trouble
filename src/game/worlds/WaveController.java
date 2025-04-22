package game.worlds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.enemy.Enemy;
import game.enemy.EnemyBullet;
import game.enemy.EnemyController;
import game.enemy.EnemyType;

public abstract class WaveController implements StepListener {
    protected final Level level;
    protected ArrayList<Enemy> enemies = new ArrayList<>();
    protected ArrayList<EnemyBullet> bullets = new ArrayList<>();
    protected final Random random = new Random();

    // Wave configuration
    protected final int waveCount;
    protected final float collectibleInterval;
    protected final float waveCooldown = 2.0f;

    // Wave tracking
    protected int currentWave = 0;
    protected float timer = 0;
    protected float lastCollectibleSpawnTime = 0;
    protected boolean firstWaveStarted = false;
    protected float lastWaveTime = 0;

    // Status message
    protected String statusMessage = "";
    protected float statusMessageDuration = 0;

    protected boolean victoryTriggered = false;

    protected WaveConfig currentWaveConfig;

    protected Queue<EnemyType> enemyQueue = new LinkedList<>();
    protected float nextSpawnTime = 0;
    protected float spawnInterval = 1.0f;

    public WaveController (Level level, int waveCount, float collectibleInterval) {
        this.level = level;
        this.waveCount = waveCount;
        this.collectibleInterval = collectibleInterval;
    }

    @Override
    public void preStep(StepEvent e) {
        timer += e.getStep();

        Iterator<EnemyBullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            EnemyBullet bullet = bulletIterator.next();
            if (bullet.update(e.getStep())) {
                bulletIterator.remove();
                bullet.destroy();
            }
        }

        if (statusMessageDuration > 0) {
            statusMessageDuration -= e.getStep();
        }

        if (currentWave == 0 && !firstWaveStarted && timer > 2.0f) {
            startNextWave();
            firstWaveStarted = true;
        }

        if (currentWave > 0 && currentWave < waveCount && enemyQueue.isEmpty() && enemies.isEmpty() && timer - lastWaveTime >= waveCooldown) {
            setStatusMessage("Wave " + currentWave + " completed!");

            final int completedWave = currentWave;

            delayedRun(() -> {
                if (currentWave == completedWave) {
                startNextWave();
                }
            }, 2);        
        }

        if (!enemyQueue.isEmpty() && timer >= nextSpawnTime) {
            spawnNextEnemy();
        }

        if (currentWave >= waveCount && enemies.isEmpty() && enemyQueue.isEmpty()) {
            if (level.getPlayer() != null && !level.getPlayer().isDying()) {
                if (!victoryTriggered) {
                    victoryTriggered = true;
                
                    setStatusMessage("All waves completed!");
                    delayedRun(() -> {
                        level.getGame().victory();
                    }, 2);
                }
            }
        }

        if (timer-lastCollectibleSpawnTime >= collectibleInterval) {
            spawnCollectible();
            lastCollectibleSpawnTime = timer;
        }


    }

    private void startNextWave() {
        currentWave++;

        currentWaveConfig = getWaveConfig(currentWave);    
        queueEnemies(currentWaveConfig);

        nextSpawnTime  = timer + 1.0f;
        lastWaveTime = timer;

        setStatusMessage("Wave " + currentWave + " starting!");


    }

    private void queueEnemies(WaveConfig config) {
        enemyQueue.clear();

        for (EnemyType type : config .getEnemyCounts().keySet()) {
            int count = config.getEnemyCounts().get(type);
            for (int i = 0; i < count; i++) {
                enemyQueue.add(type);
            }

        }
    }

    private void spawnNextEnemy() {

        EnemyType type = enemyQueue.poll();
        Vec2 position = new Vec2(random.nextInt(20)-10, 10);
        
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

    public void addBullet(EnemyBullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(EnemyBullet bullet) {
        if (bullets.contains(bullet)) {
            bullets.remove(bullet);
        }
    }

    @Override
    public void postStep(StepEvent e) {
    }

    protected abstract WaveConfig getWaveConfig(int waveNumber);

    protected abstract void spawnCollectible();

    protected void setStatusMessage(String message) {
        statusMessage = message;
        statusMessageDuration = 2.0f;
    }

    public String getStatusMessage() {
        if (statusMessageDuration > 0) {
            return statusMessage;
        }
        return null;
    }


    private void delayedRun(Runnable runnable, float delay) {
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    runnable.run();
                }
            },
            (long)(delay * 1000)
        );
    }

    public float getProgress() {
        // No progress if no waves
        if (waveCount <= 0) return 0;
        
        // Calculate completed waves progress
        float completedWavesProgress = (float)(currentWave - 1) / waveCount;
        
        // Calculate progress through current wave
        float currentWaveProgress = 0;
        if (currentWave > 0 && currentWave <= waveCount) {
            // Calculate fraction of enemies defeated in current wave
            int totalEnemies = 0;
            if (currentWaveConfig != null) {
                totalEnemies = currentWaveConfig.getTotalCount();
            }
            
            if (totalEnemies > 0) {
                int enemiesDefeated = totalEnemies - enemies.size() - enemyQueue.size();
                currentWaveProgress = (float)enemiesDefeated / totalEnemies / waveCount;
            }
        }
        
        // Combine and limit to range [0,1]
        float totalProgress = completedWavesProgress + currentWaveProgress;
        return Math.max(0, Math.min(1, totalProgress));
    }
}
