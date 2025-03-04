package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.*;
import game.worlds.Level;

public class Enemy extends Walker {
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
        //Shape shape = new BoxShape(1, 2);

        super(world, new CircleShape(1.0f));
        // Remove this later to create more enemy types that extends this class
        this.addImage(new BodyImage("data/assets/player/character.png", 4));
        this.addCollisionListener(new EnemyCollisionListener(this));
        this.setPosition(position);
        this.healthbar = new StaticBody(world);
        healthbar.addImage(healthImages[health - 1]);
        
    }

    @Override
    public void jump(float speed) {

        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, 0));
        this.applyImpulse(new Vec2(this.getLinearVelocity().x, speed));
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

    public int getHealth() {
        return this.health;
    }

    public StaticBody getHealthbar() {
        return healthbar;
    }
}