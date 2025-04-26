package game.enemy;

import org.jbox2d.common.Vec2;

import game.worlds.Level;

public enum EnemyType {
    BASE,
    FAST,
    SHOOTING,
    BOSS;

    public Enemy create(Level level, Vec2 position) {
        return switch (this) {
            case BASE -> new BaseEnemy(level, position);
            case FAST -> new FastEnemy(level, position);
            case SHOOTING -> new ShootingEnemy(level, position);
            case BOSS -> new BossEnemy(level, position);
            default -> new BaseEnemy(level, position);
        };
    }
}
