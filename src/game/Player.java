package game;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.jbox2d.common.Vec2;

import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.World;

public class Player extends DynamicBody {

   public Player(World world, Vec2 position) {
        final Shape playerShape = new PolygonShape(-0.81f,-1.99f, -1.33f,-0.8f, -1.34f,0.83f, -0.68f,1.98f, 0.54f,1.98f, 1.18f,0.93f, 1.21f,-0.78f, 0.66f,-1.97f);
        super(world, playerShape);
        this.addImage(new BodyImage("data/character.png", 4));
        this.addCollisionListener(new PlayerCollisionListener(this));
        this.setPosition(position);

      // Workaround to call the setFixedRotation method from the Body class, which is package-private
      try {
         Field f = Body.class.getDeclaredField("b2body");
         f.setAccessible(true);
         Object b2body = f.get(this);
         b2body.getClass().getDeclaredMethod("setFixedRotation", boolean.class).invoke(b2body, true);
      } catch (NoSuchFieldException | SecurityException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
         e.printStackTrace();
      }
    }

    // Jump method ripped from the Walker class in city.cs.engine
   public void jump(float speed) {
      Vec2 v = this.getLinearVelocity();
      if (Math.abs(v.y) < 0.01F) {
         //this.setLinearVelocity(new Vec2(v.x, speed));
         this.applyImpulse(new Vec2(0, 200f));
      }

   }
    
}
