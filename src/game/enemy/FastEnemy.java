package game.enemy;

import org.jbox2d.common.Vec2;

import game.worlds.Level;

public class FastEnemy extends Enemy {
    private static final int health = 3;

    public FastEnemy(Level world, Vec2 position) {
        super(world, position, "data/assets/enemy/fastenemy/");
        this.setHealth(health);    }

    @Override
    public void setLinearVelocity(Vec2 velocity) {
        super.setLinearVelocity(new Vec2(velocity.x * 1.5f, velocity.y));
    }

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
