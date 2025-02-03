package game;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.World;

public class Player extends DynamicBody {
    
    public Player(World world, Vec2 position) {
        //final Shape playerShape = new PolygonShape(-0.81f,-1.99f, -1.33f,-0.8f, -1.34f,0.83f, -0.68f,1.98f, 0.54f,1.98f, 1.18f,0.93f, 1.21f,-0.78f, 0.66f,-1.97f);
        final Shape playerShape = new BoxShape(1.5f, 2.5f);
        super(world, playerShape);
        //this.setFixedRotation(true);
        this.addImage(new BodyImage("data/character.png", 4));
        this.addCollisionListener(new PlayerCollisionListener(this));
        this.setPosition(position);
    }

   public void jump(float speed) {
      Vec2 v = this.getLinearVelocity();
      if (Math.abs(v.y) < 0.01F) {
         this.setLinearVelocity(new Vec2(v.x, speed));
      }

   }
    
}
