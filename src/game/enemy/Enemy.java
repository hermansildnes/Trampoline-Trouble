package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.*;
import game.Animatable;
import game.worlds.Level;
/*
 * Generic Enemy class that specifies an enemy with health, and that can pathfind to the player
 */
public abstract class Enemy extends Animatable {
    protected int health;
    protected boolean isDying = false;
    protected boolean pathFinding = true;
    protected StaticBody healthbar;
    protected String spritePath;
    
    private static final BodyImage[] healthImages = {
        new BodyImage("data/assets/uibars/1hp.png", 2f),
        new BodyImage("data/assets/uibars/2hp.png", 2f),
        new BodyImage("data/assets/uibars/3hp.png", 2f),
        new BodyImage("data/assets/uibars/4hp.png", 2f),
        new BodyImage("data/assets/uibars/5hp.png", 2f),
    };

    public Enemy(Level world, Vec2 position, String spritePath) {
        super(world, new CircleShape(1f), spritePath);

        this.addCollisionListener(new EnemyCollisionListener(this));
        this.setPosition(position);

        loadAnimations();

        this.healthbar = new StaticBody(world);        
    }

    protected abstract void loadAnimations();

    // Overriding the walker implementation of jump to make it more suitable for the trampolines
    @Override
    public void jump(float speed) {

        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, 0));
        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, speed));
    }

    @Override
    public Level getWorld() {
       return (Level) super.getWorld();
    }

    public void decreaseHealth(int damage) {
        // Destroy the enemy if health is 0, but only after death animation has finished
        if (this.health - damage <= 0) {
            die();
        } else{
            this.health -= damage;
            startAnimation(AnimationState.DAMAGE);
            healthbar.removeAllImages();
            healthbar.addImage(healthImages[health - 1]);
        }
    }

    public void die() {
        isDying = true;
        this.setLinearVelocity(new Vec2(0, 0));
        this.startAnimation(AnimationState.DEATH);
        Enemy enemy = this;
        this.healthbar.destroy();

        new java.util.Timer().schedule(
        new java.util.TimerTask() {
            @Override
            public void run() {
                enemy.removeAllImages();
                enemy.setPosition(new Vec2(-1000, -1000)); // Janky but works :)
            }
        },
        getDeathAnimationDuration() - 50  // 50ms before the destroy timer fires
        );
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        enemy.destroy();
                        enemy.getWorld().removeEnemy(enemy);

                    }
                },
                
                getDeathAnimationDuration() + 256// frames per animation * steps per frame * milliseconds per step
        );
    }

    protected abstract int getDeathAnimationDuration();

    public boolean isDying() {
        return isDying;
    }

    public void setHealth(int health) {
        this.health = health;
        if (healthbar != null) {
            healthbar.removeAllImages();
            healthbar.addImage(healthImages[health - 1]);
        }
    }

    public int getHealth() {
        return this.health;
    }

    public StaticBody getHealthbar() {
        return healthbar;
    }

    public void setPathFinding(boolean pathFinding) {
        this.pathFinding = pathFinding;
    }
    
    public boolean getPathFinding() {
        return pathFinding;
    }

    public void update(StepEvent e) {}
}