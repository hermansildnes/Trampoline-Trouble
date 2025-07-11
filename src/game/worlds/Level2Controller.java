package game.worlds;

import org.jbox2d.common.Vec2;

import game.enemy.EnemyType;
import game.environment.collectibles.Collectible;

public class Level2Controller extends WaveController {
    private static final int WAVE_COUNT = 3;
    private static final float COLLECTIBLE_INTERVAL = 5f;

    public Level2Controller(Level level) {
        super(level, WAVE_COUNT, COLLECTIBLE_INTERVAL);
    }

    @Override
    protected WaveConfig getWaveConfig(int waveNumber) {
        WaveConfig config = new WaveConfig();
        switch (waveNumber) {
            case 1:
                return config.add(EnemyType.BASE, 2);
            case 2:
                return config.add(EnemyType.BASE, 1)
                        .add(EnemyType.FAST, 1);
            case 3:
                return config.add(EnemyType.FAST, 2);
            default:
                return config.add(EnemyType.BASE, 1);
        }
    }


    @Override
    protected void spawnCollectible() {
        Vec2 position = new Vec2(random.nextInt(20)-10, random.nextInt(10)-5);
        if (random.nextFloat() < 0.5f) {
            new Collectible(level, position, Collectible.CollectibleType.HEALTHPACK);
        } else {
            new Collectible(level, position, Collectible.CollectibleType.LASERGUN);
        }
    }
    

}
