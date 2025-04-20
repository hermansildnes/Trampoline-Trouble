package game.enemy;

import org.jbox2d.common.Vec2;

import game.worlds.Level;

public enum EnemyType {
    BASE,
    FAST,
    SHOOTING;

    public Enemy create(Level level, Vec2 position) {
        switch (this) {
            case BASE:
                return new BaseEnemy(level, position);
            case FAST:
                return new FastEnemy(level, position);
            case SHOOTING:
                return new ShootingEnemy(level, position);
            default:
                return new BaseEnemy(level, position);
        }
    }
}
