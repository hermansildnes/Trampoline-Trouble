package game.enemy;

import org.jbox2d.common.Vec2;

import city.cs.engine.*;
import game.worlds.Level;

public class Enemy extends Walker {
    private Level level;

    public Enemy(Level world, Vec2 position) {
        Shape shape = new BoxShape(1, 2);
        super(world, shape);
        // Remove this later to create more enemy types that extends this class
        this.addImage(new BodyImage("data/character.png", 4));
        this.addCollisionListener(new EnemyCollisionListener(this));
        this.setPosition(position);
        this.level = world;
    }

    public Level getLevel() {
        return this.level;
    }
}