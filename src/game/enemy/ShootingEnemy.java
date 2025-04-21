package game.enemy;

import org.jbox2d.common.Vec2;

import game.worlds.Level;

public class ShootingEnemy extends Enemy {
    private static final int health = 2;

    public ShootingEnemy(Level world, Vec2 position) {
        super(world, position, "data/assets/enemy/baseenemy/");
        this.setHealth(health);    }

    @Override
    protected void loadAnimations() {
        addAnimation(AnimationState.JUMP, "jump", 4);
        addAnimation(AnimationState.DEATH, "death", 10);
        addAnimation(AnimationState.DAMAGE, "damage", 4);
    }

    @Override
    protected int getDeathAnimationDuration() {
        return 16 * 4 * 10;
    }
}
