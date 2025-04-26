package game.enemy;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Filter;

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
    protected ArrayList<StaticBody> healthbars;
    protected String spritePath;
    private static final int MAX_HEALTH_PER_BAR = 5;
    private static final float HEALTH_BAR_SPACING = 0.7f;
    
    private static final BodyImage[] healthImages = {
        new BodyImage("data/assets/uibars/1hp.png", 2f),
        new BodyImage("data/assets/uibars/2hp.png", 2f),
        new BodyImage("data/assets/uibars/3hp.png", 2f),
        new BodyImage("data/assets/uibars/4hp.png", 2f),
        new BodyImage("data/assets/uibars/5hp.png", 2f),
    };

    public Enemy(Level world, Shape shape, Vec2 position, String spritePath, float size) {
        super(world, shape, spritePath, size);

        try {
            Filter filter = new Filter();
            filter.categoryBits = 0x0002;
            filter.maskBits = (short)(0xFFFF & ~0x0002); 
            
            // Get fixtures from body class
            java.lang.reflect.Method getFixturesMethod = 
                city.cs.engine.Body.class.getDeclaredMethod("getFixtures");
            getFixturesMethod.setAccessible(true);
            Object fixturesObj = getFixturesMethod.invoke(this);
            
            // Access getBox2DFixture through reflection
            java.lang.reflect.Method getBox2DFixtureMethod = 
                city.cs.engine.Fixture.class.getDeclaredMethod("getBox2DFixture");
            getBox2DFixtureMethod.setAccessible(true);
            
            Iterable<Object> fixtures = (Iterable<Object>) fixturesObj;
            
            // Apply filter to all fixtures
            for (Object fixtureObj : fixtures) {
                if (fixtureObj instanceof city.cs.engine.Fixture) {
                    // Access the Box2DFixture through reflection
                    org.jbox2d.dynamics.Fixture jbox2dFixture = 
                        (org.jbox2d.dynamics.Fixture) getBox2DFixtureMethod.invoke(fixtureObj);
    
                    jbox2dFixture.setFilterData(filter);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to set collision filter: " + e.getMessage());
            e.printStackTrace();
        }


        this.addCollisionListener(new EnemyCollisionListener(this));
        this.setPosition(position);

        loadAnimations();

        this.healthbars = new ArrayList<>();
        StaticBody primaryBar = new StaticBody(world);
        this.healthbars.add(primaryBar);
    }

    public Enemy(Level world, Vec2 position, String spritePath) {
        this(world, new CircleShape(1f), position, spritePath);
    }

    public Enemy(Level world, Shape shape, Vec2 position, String spritePath) {
        this(world, shape, position, spritePath, 4f);
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
        getWorld().getGame().getAudioManager().playSoundEffect("damage");
        // Destroy the enemy if health is 0, but only after death animation has finished
        if (this.health - damage <= 0) {
            die();
        } else{
            this.health -= damage;
            startAnimation(AnimationState.DAMAGE);
            updateHealthBars();
        }
    }

    public void die() {
        isDying = true;
        this.setLinearVelocity(new Vec2(0, 0));
        this.startAnimation(AnimationState.DEATH);

        for (StaticBody healthbar : healthbars) {
            healthbar.destroy();
        }
        healthbars.clear();

        Enemy enemy = this;
        
        // Single timer that does everything at once
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    enemy.stopAnimation();
                    enemy.getWorld().getWaveController().removeEnemy(enemy);
                    enemy.setGravityScale(0);
                    enemy.setPosition(new Vec2(-1000, -1000)); // Janky but works :)
                    enemy.destroy();
                }
            },
            getDeathAnimationDuration()
        );
    }

    public void updateHealthBarPositions() {
        Vec2 position = this.getPosition().add(new Vec2(0, 2f));

        for (int i = 0; i < healthbars.size(); i++) {
            StaticBody bar = healthbars.get(i);
            float yOffset = HEALTH_BAR_SPACING * (healthbars.size() - i - 1);
            bar.setPosition(new Vec2(position.x, position.y + yOffset));
        }
    }

    private void updateHealthBars() {
        int fullBars = (health-1) / MAX_HEALTH_PER_BAR;
        int remainingHealth = health - (fullBars * MAX_HEALTH_PER_BAR);
    
        if (remainingHealth == 0 && health > 0) {
            fullBars--;
            remainingHealth = MAX_HEALTH_PER_BAR;
        }
    
        int numberOfBars = fullBars + 1;
        
        // Add new bars at the bottom if needed
        while (healthbars.size() < numberOfBars) {
            StaticBody newBar = new StaticBody(getWorld());
            healthbars.addFirst(newBar); // Add at the top (index 0)
        }
    
        // Remove excess bars from the top (index 0) if needed
        // Fix: Change this to remove from index 0 (top) until we have the right number
        while (healthbars.size() > numberOfBars) {
            // Remove from index 0 (top bar)
            StaticBody bar = healthbars.removeFirst();
            bar.destroy();
        }
    
        // Update health bar images from top to bottom
        for (int i = 0; i < healthbars.size(); i++) {
            StaticBody bar = healthbars.get(i);
            bar.removeAllImages();
    
            if (i < fullBars) {
                // Full health bars (5hp)
                bar.addImage(healthImages[MAX_HEALTH_PER_BAR - 1]);
            } else if (i == fullBars) {
                // Partial health bar
                bar.addImage(healthImages[remainingHealth - 1]);
            }
        }
    
        updateHealthBarPositions();
    }
    protected abstract int getDeathAnimationDuration();

    public boolean isDying() {
        return isDying;
    }

    public void setHealth(int health) {
        this.health = health;
        updateHealthBars();
    }

    public int getHealth() {
        return this.health;
    }

    public StaticBody getHealthbar() {
        if (healthbars.isEmpty()) {
            return null;
        }
        return healthbars.getLast();
    }

    public ArrayList<StaticBody> getHealthbars() {
        return healthbars;
    }

    public void setPathFinding(boolean pathFinding) {
        this.pathFinding = pathFinding;
    }
    
    public boolean getPathFinding() {
        return pathFinding;
    }

    public void update(StepEvent e) {}
}