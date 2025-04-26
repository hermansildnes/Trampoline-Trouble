package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import game.worlds.Level;

public class EnemyBullet extends DynamicBody {
    private static final float BULLET_SIZE = 0.3f;
    private static final float BULLET_SPEED = 25.0f;
    private float lifetime = 0;
    private final int direction;


    public EnemyBullet(Level world, Vec2 position, int direction, Shape shape, String spritePath, float spriteSize) {
        super(world, shape);
        this.direction = direction;
        
        // Set position
        this.setPosition(position);
        
        // Apply velocity in X direction only
        this.setLinearVelocity(new Vec2(direction * BULLET_SPEED, 0));
        
        // Disable gravity effects
        this.setGravityScale(0);
        
        // Enable bullet physics for better collision detection
        this.setBullet(true);
        
        
        // Add a visual for the bullet
        this.addImage(new BodyImage(spritePath, spriteSize));
        
        // Register with wave controller
        world.getWaveController().addBullet(this);
    }

    public EnemyBullet(Level world, Vec2 position, int direction) {
        this(world, position, direction, new CircleShape(BULLET_SIZE), "data/assets/enemy/shootingenemy/bullet00.png", BULLET_SIZE*8);
    }

    public boolean update(float deltaTime) {
        lifetime += deltaTime;
        float BULLET_MAX_LIFETIME = 3.0f;
        if (lifetime >= BULLET_MAX_LIFETIME) {
            return true;
        }
        // Ensure bullet maintains constant horizontal velocity
        Vec2 currentVelocity = this.getLinearVelocity();
        if (currentVelocity.x != direction * BULLET_SPEED || currentVelocity.y != 0) {
            this.setLinearVelocity(new Vec2(direction * BULLET_SPEED, 0));
        }
        return false;
    }
}
