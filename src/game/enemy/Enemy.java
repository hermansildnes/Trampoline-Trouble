package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.*;
import game.Animatable;
import game.worlds.Level;

public class Enemy extends Animatable {
    private int health = 5;
    private static final BodyImage[] healthImages = {
        new BodyImage("data/assets/uibars/1hp.png", 2f),
        new BodyImage("data/assets/uibars/2hp.png", 2f),
        new BodyImage("data/assets/uibars/3hp.png", 2f),
        new BodyImage("data/assets/uibars/4hp.png", 2f),
        new BodyImage("data/assets/uibars/5hp.png", 2f),
    };
    private StaticBody healthbar;
    private final Shape shape = new CircleShape(1.0f);

    public Enemy(Level world, Vec2 position) {
        super(world, new BoxShape(1f, 2f), "data/assets/enemy/");

       //super(world, new CircleShape(1.0f));
        // Remove this later to create more enemy types that extends this class
        this.addCollisionListener(new EnemyCollisionListener(this));
        this.setPosition(position);
        addAnimation(AnimationState.JUMP, "jump", 4);
        addAnimation(AnimationState.DEATH, "death", 10);

        this.healthbar = new StaticBody(world);
        healthbar.addImage(healthImages[health - 1]);
        
    }

    @Override
    public void jump(float speed) {

        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, 0));
        //this.applyImpulse(new Vec2(this.getLinearVelocity().x, speed));
        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, speed));
    }

    @Override
    public Level getWorld() {
       return (Level) super.getWorld();
    }

    public void decreaseHealth(int damage) {
        if (this.health - damage <= 0) {
            System.out.println("Enemy destroyed");
            healthbar.destroy();
            this.destroy();

        } else{
            this.health -= damage;
            healthbar.removeAllImages();
            healthbar.addImage(healthImages[health - 1]);
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public StaticBody getHealthbar() {
        return healthbar;
    }
}