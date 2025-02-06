package game;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import city.cs.engine.*;

public class Enemy extends Walker {
    private float gravity;

    public Enemy(World world, Vec2 position, ArrayList<Trampoline> trampolines) {
        super(world);
        // Remove this later to create more enemy types that extends this class
        this.addImage(new BodyImage("data/character.png", 4));
        this.setPosition(position);
        this.addCollisionListener(new EnemyCollisionListener(this));
        this.gravity = world.getGravity();
    }

    public void survive() {
        
    }
}