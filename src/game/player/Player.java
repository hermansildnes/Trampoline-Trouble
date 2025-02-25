package game.player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;

import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import game.environment.collectibles.Collectible;
import game.player.equipments.Equipment;
import game.player.equipments.LaserGun;
import game.worlds.Level;

public class Player extends DynamicBody {
   private BodyImage targetImage = new BodyImage("data/assets/player/target.png", 1.5f);
   private StaticBody target;
   private Equipment equipment;
   private static final String assetPath = "data/assets/player/hooded/";
   private static final int  FRAMES_PER_ANIMATION = 4;
   public enum AnimationState {
      NEUTRAL,
      JUMP,
      DEATH
   }

   private static final BodyImage neutral = new BodyImage(assetPath + "neutral.png", 4);;

   private Map<AnimationState, BodyImage[]> animations;
   private AnimationState currentState = AnimationState.NEUTRAL;
   private boolean isAnimating = false;
   private int currentFrame = 0;
   private int frameCounter = 0;

   public Player(Level world, Vec2 position) {
         final Shape playerShape = new PolygonShape(-0.81f,-1.99f, -1.33f,-0.8f, -1.34f,0.83f, -0.68f,1.98f, 0.54f,1.98f, 1.18f,0.93f, 1.21f,-0.78f, 0.66f,-1.97f);
         super(world, playerShape);
         animations = new HashMap<>();
         animations.put(AnimationState.JUMP, loadAnimation("jump", 8));
         animations.put(AnimationState.DEATH, loadAnimation("death", 4));
         this.addImage(neutral);
         this.addCollisionListener(new PlayerCollisionListener(this));
         this.setPosition(position);

         this.target = new StaticBody(world);
         target.addImage(targetImage);


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
         this.setLinearVelocity(new Vec2(v.x, speed));
         //this.applyImpulse(new Vec2(0, 200f));
      }
   }

   private BodyImage[] loadAnimation(String prefix, int frameCount) {
      BodyImage[] frames = new BodyImage[frameCount];
      for (int i = 0; i < frameCount; i++) {
         frames[i] = new BodyImage(assetPath + prefix + i + ".png", 4);
      }
      return frames;
   }

   public void startAnimation(AnimationState state) {
      currentState = state;
      isAnimating = true;
      currentFrame = 0;
      frameCounter = 0;
      updateAnimation();
   }

   private void updateAnimation() {
      if (currentState == AnimationState.NEUTRAL || !isAnimating) {
         this.removeAllImages();
         this.addImage(neutral);
         return;
      }

      BodyImage[] currentAnimation = animations.get(currentState);
      if (currentFrame < currentAnimation.length) {
         this.removeAllImages();
         this.addImage(currentAnimation[currentFrame]);
      } else {
         isAnimating = false;
         this.removeAllImages();
         this.addImage(neutral);
      }
   }

   public void incrementFrameCounter() {
      if (!isAnimating) return;

      frameCounter++;
      if (frameCounter == FRAMES_PER_ANIMATION) {
         frameCounter = 0;
         currentFrame++;
         updateAnimation();
      }
   }

   public AnimationState getCurrentState() {
      return currentState;
   }

   public boolean isAnimating() {
      return isAnimating;
   }


   public void setEquipment(Collectible.CollectibleType collectibleType) {
      switch (collectibleType) {
         case LASERGUN:
            this.equipment = new LaserGun(this);
            break;
      }
   }

   public boolean hasEquipment() {
      return equipment != null;
   }

   public void useEquipment(Vec2 mousePosition) {
      if (equipment != null) {
         equipment.use(mousePosition);
      }
   }

   @Override
   public Level getWorld() {
      return (Level) super.getWorld();
   }

   public StaticBody getTarget() {
      return target;
   }
}
