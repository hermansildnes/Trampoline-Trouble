package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.CircleShape;
import city.cs.engine.DynamicBody;
import game.worlds.Level;
import game.worlds.WaveController;

public class EnemyBullet extends DynamicBody {
    private static final float BULLET_SIZE = 0.3f;
    private static final float BULLET_SPEED = 25.0f;
    private static float BULLET_MAX_LIFETIME = 3.0f;
    private float lifetime = 0;
    private Vec2 direction;


    public EnemyBullet(Level world, Vec2 position, Vec2 direction) {
        super(world, new CircleShape(BULLET_SIZE));
        float length = (float) Math.sqrt(direction.x * direction.x + direction.y * direction.y);
        if (length > 0) {
            // Create a normalized direction vector
            this.direction = new Vec2(direction.x / length, direction.y / length);
        } else {
            // Default direction if input is a zero vector
            this.direction = new Vec2(1, 0);
        }
        
        this.setPosition(position);
        
        // Apply velocity with normalized direction for consistent speed
        this.setLinearVelocity(new Vec2(
            this.direction.x * BULLET_SPEED,
            this.direction.y * BULLET_SPEED
        ));
        
        this.setGravityScale(0);
        this.setBullet(true);
        
        // Add a small rotation based on direction (visual effect)
        if (this.direction.x < 0) {
            this.setAngle((float)Math.PI); // Rotate 180 degrees if moving left
        }
        
        world.getWaveController().addBullet(this);
    }

    public boolean update(float deltaTime) {
        lifetime += deltaTime;
        if (lifetime >= BULLET_MAX_LIFETIME) {
            return true;
        }
        return false;
    }
}
