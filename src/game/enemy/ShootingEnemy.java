package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.StepEvent;
import game.worlds.Level;

public class ShootingEnemy extends Enemy {
    private static final int health = 2;

    private static final float SHOOTING_COOLDOWN = 3.0f;

    private float shootingTimer = 0;
    private boolean canShoot = true;

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

    @Override
    public void update(StepEvent e) {
        super.update(e);

        if (!canShoot) {
            shootingTimer -= e.getStep();
            if (shootingTimer <= 0) {
                canShoot = true;
            }
        }

        if (canShoot) {
            shootAtPlayer();
        }
    }

    private void shootAtPlayer() {
        Vec2 playerPos = getWorld().getPlayer().getPosition();
        Vec2 enemyPos = getPosition();

        float direction = Math.signum(playerPos.x - enemyPos.x);
        Vec2 directionX = new Vec2(direction, 0);
        
        EnemyBullet bullet = new EnemyBullet(getWorld(), enemyPos, directionX);

        canShoot = false;
        shootingTimer = SHOOTING_COOLDOWN;
    }
}
