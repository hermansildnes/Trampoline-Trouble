package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.CircleShape;
import city.cs.engine.StepEvent;
import game.worlds.Level;

public class BossEnemy extends Enemy {
    private static final int health = 15;

    // Shooting logic fields
    private static final float SHOOTING_COOLDOWN = 2.5f;
    private float shootingTimer = SHOOTING_COOLDOWN;
    private boolean canShoot = false; // Start on cooldown

    private String bulletSprite;

    public BossEnemy(Level world, Vec2 position) {
        super(world, new CircleShape(2f), position, "data/assets/enemy/bossenemy/");
        this.setHealth(health);    }


    @Override
    protected void loadAnimations() {
        addAnimation(AnimationState.JUMP, "jump", 4);
        addAnimation(AnimationState.DEATH, "death", 7);
        addAnimation(AnimationState.DAMAGE, "damage", 4);
        addAnimation(AnimationState.ATTACK, "attack", 8);
    }

    @Override
    protected int getDeathAnimationDuration() {
        return 16 * 4 * 7;
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

        if (direction == 1) {
            bulletSprite = "data/assets/enemy/bossenemy/right/bullet.png";
        } else {
            bulletSprite = "data/assets/enemy/bossenemy/left/bullet.png";
        }   
        
        // Create bullet with proper direction
        EnemyBullet bullet = new EnemyBullet(getWorld(), bulletPos, direction, new CircleShape(0.8f), bulletSprite, 0.8f*4);
        
        
        // Reset shooting cooldown
        canShoot = false;
        shootingTimer = SHOOTING_COOLDOWN;
    }
}
