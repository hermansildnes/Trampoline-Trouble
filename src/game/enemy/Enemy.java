package game.enemy;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import city.cs.engine.*;
import game.environment.Trampoline;
import game.player.Player;

public class Enemy extends Walker {
    public float gravity;
    public ArrayList<Trampoline> trampolines;
    public Player player;

    public Enemy(World world, Vec2 position, Player player, ArrayList<Trampoline> trampolines) {
        Shape shape = new BoxShape(1, 2);
        super(world, shape);
        // Remove this later to create more enemy types that extends this class
        this.addImage(new BodyImage("data/character.png", 4));
        this.addCollisionListener(new EnemyCollisionListener(this));
        this.setPosition(position);
        this.gravity = world.getGravity();
        this.trampolines = trampolines;
        this.player = player;
    }

    public void survive() {
        
    }
}