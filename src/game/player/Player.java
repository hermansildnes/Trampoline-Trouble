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

/* Player class implementing the player with its target, 
* and functions to interact with collectable equipment 
*/
public class Player extends Animatable {
   private int health = 5;
    private final StaticBody target;
   private Equipment equipment;
   
   // Obtained using the polygon editor
   private static final Shape PLAYER_SHAPE = new PolygonShape(
      -0.81f, -2.1f,
      -1.33f, -0.8f,
      -1.34f, 0.83f,
      -0.68f, 1.98f,
      0.54f, 1.98f,
      1.18f, 0.93f,
      1.21f, -0.78f,
      0.66f, -2.1f
   );

   public Player(Level world, Vec2 position) {
      super(world, PLAYER_SHAPE, "data/assets/player/");
      this.addCollisionListener(new PlayerCollisionListener(this));
      this.setPosition(position);

      // Specify animations for the player
      addAnimation(AnimationState.JUMP, "jump", 8);
      addAnimation(AnimationState.DEATH, "death", 4);
      addAnimation(AnimationState.ATTACK, "attack", 8);

      // Create a target
      this.target = new StaticBody(world);
      target.addImage(new BodyImage("data/assets/player/extras/target.png", 1.5f));

    }

    // Overriding the walker implementation of jump to make it more suitable for the trampolines
    @Override
    public void jump(float speed) {

        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, 0));
        this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, speed));
    }

    // Specify how each collectible equipment is equipped
   public void setEquipment(Collectible.CollectibleType collectibleType) {
      switch (collectibleType) {
         // Attach Lasergun or add ammunition if already attached
         case LASERGUN:
            if (this.equipment instanceof LaserGun) {
               ((LaserGun)this.equipment).addAmmo(10);
            } else {
            this.equipment = new LaserGun(this); 
         }
            break;
         // Add 1 health if healthpack is collected
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

   // Remove equipment if run out of ammunition, otherwise use
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

   // Destroy player if health is 0, otherwise decrease health
   public void decreaseHealth(int damage) {
      if (this.health - damage <= 0) {
         target.destroy();
         this.destroy();
      } else {
         this.health -= damage;
      }
   }
}
