package game.player;


import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.GhostlyFixture;
import city.cs.engine.StaticBody;
import city.cs.engine.World;



public class Target extends StaticBody{
    private BodyImage image = new BodyImage("data/assets/player/target.png", 1.5f);

    public Target(World world) {
        super(world);
        GhostlyFixture fixture = new GhostlyFixture(this, new CircleShape(1.0f));
        this.addImage(image);
    }
    
}
