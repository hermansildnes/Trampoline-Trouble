package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.CircleShape;
import city.cs.engine.StepEvent;
import game.worlds.Level;

public class ShootingEnemy extends Enemy {
    private static final int health = 2;

    private static final float SHOOTING_COOLDOWN = 1f;

    private float shootingTimer = 0;
    private boolean canShoot = true;

    public ShootingEnemy(Level world, Vec2 position) {
        super(world, new CircleShape(1f), position, "data/assets/enemy/shootingenemy/", 8f);
        this.setHealth(health);    
    }

    @Override
    protected void loadAnimations() {
        addAnimation(AnimationState.JUMP, "jump", 2);
        addAnimation(AnimationState.DEATH, "death", 10);
        addAnimation(AnimationState.DAMAGE, "damage", 3);
        addAnimation(AnimationState.ATTACK, "attack", 6);
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

        this.startAnimation(AnimationState.ATTACK);

        Vec2 playerPos = getWorld().getPlayer().getPosition();
        Vec2 enemyPos = getPosition();
        
        int direction = (playerPos.x > enemyPos.x) ? 1 : -1;
        
        Vec2 bulletPos = new Vec2(
            enemyPos.x + direction * 0.6f,
            enemyPos.y
        );
        
        // Create bullet with proper direction
        EnemyBullet bullet = new EnemyBullet(getWorld(), bulletPos, direction);
        
        
        // Reset shooting cooldown
        canShoot = false;
        shootingTimer = SHOOTING_COOLDOWN;
    }
}
