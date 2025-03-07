package game.player;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import game.Animatable;
import game.environment.collectibles.Collectible;
import game.player.equipments.Equipment;
import game.player.equipments.LaserGun;
import game.worlds.Level;

public class Player extends Animatable {
   private int health = 5;
   private BodyImage targetImage = new BodyImage("data/assets/player/extras/target.png", 1.5f);
   private StaticBody target;
   private Equipment equipment;
   
   private static final Shape PLAYER_SHAPE = new PolygonShape(
      -0.81f, -2.1f,   // Bottom left (slightly lower)
      -1.33f, -0.8f,   // Left side
      -1.34f, 0.83f,   // Left middle
      -0.68f, 1.98f,   // Top left
      0.54f, 1.98f,    // Top right
      1.18f, 0.93f,    // Right middle
      1.21f, -0.78f,   // Right side
      0.66f, -2.1f     // Bottom right (slightly lower)
   );

   public Player(Level world, Vec2 position) {
      super(world, PLAYER_SHAPE, "data/assets/player/");
      this.addCollisionListener(new PlayerCollisionListener(this));
      this.setPosition(position);

      addAnimation(AnimationState.JUMP, "jump", 8);
      addAnimation(AnimationState.DEATH, "death", 4);
      addAnimation(AnimationState.ATTACK, "attack", 8);

      this.target = new StaticBody(world);
      target.addImage(targetImage);

    }

    @Override
    public void jump(float speed) {

        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, 0));
        //this.applyImpulse(new Vec2(this.getLinearVelocity().x, speed));
        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, speed));
    }

   public void setEquipment(Collectible.CollectibleType collectibleType) {
      switch (collectibleType) {
         case LASERGUN:
            this.equipment = new LaserGun(this);
            break;

         case HEALTHPACK:
            if (this.health < 5) {
               this.health++;
            }
            break;
      }
   }

   public boolean hasEquipment() {
      return equipment != null;
   }
   public Equipment getEquipment() {
      return equipment;
   }

   public void useEquipment(Vec2 mousePosition) {
      if (equipment != null) {
         if (equipment.getAmmunition() <= 0) {
            equipment.use(mousePosition);
            equipment = null;
         } else {
         equipment.use(mousePosition);
         }
      }
   }

   @Override
   public Level getWorld() {
      return (Level) super.getWorld();
   }

   public StaticBody getTarget() {
      return this.target;
   }

   public int getHealth() {
      return this.health;
   }

   public void decreaseHealth(int damage) {
      if (this.health - damage <= 0) {
         System.out.println("Player destroyed");
         target.destroy();
         this.destroy();
      } else {
         this.health -= damage;
      }
   }
}
