package game;

import java.util.HashMap;
import java.util.Map;

import city.cs.engine.BodyImage;
import city.cs.engine.Shape;
import city.cs.engine.Walker;
import city.cs.engine.World;
/*  
 * Generic class for animatable characters that allows 
 * for loading a set of different animationStates and 
 * triggering of animations based on different events
*/
public abstract class Animatable extends Walker{
    
    protected String assetPath;
    protected BodyImage neutralRight;
    protected BodyImage neutralLeft;
    protected String direction = "right";
    protected float size;

    private static final int  FRAMES_PER_ANIMATION = 4;
    // Allowed animationStates
    public enum AnimationState {
      NEUTRAL,
      JUMP,
      DEATH,
      FALL,
      ATTACK,
      DAMAGE
   }
   // Map from direction facing to corresponding array of animation sprites
   private final Map<String, Map<AnimationState, BodyImage[]>> directionAnimations;
   private AnimationState currentState = AnimationState.NEUTRAL;
   private boolean isAnimating = false;
   private int currentFrame = 0;
   private int frameCounter = 0;


    public Animatable(World world, Shape shape, String assetPath, float size) {
        super(world, shape);
        this.assetPath = assetPath;
        this.size = size;
        directionAnimations = new HashMap<>();
        directionAnimations.put("left", new HashMap<>());
        directionAnimations.put("right", new HashMap<>());
        neutralRight = new BodyImage(assetPath + "right/neutral.png", size);
        neutralLeft = new BodyImage(assetPath + "left/neutral.png", size);
        this.addImage(neutralRight);
    }

    public Animatable(World world, Shape shape, String assetPath) {
      this(world, shape, assetPath, 4);
    }

    // Allows for custom animations for different entities given name of animation and a frameCount
    protected void addAnimation(AnimationState state, String prefix, int frameCount) {
        directionAnimations.get("right").put(state, loadAnimation("right", prefix, frameCount));
        directionAnimations.get("left").put(state, loadAnimation("left", prefix, frameCount));
    }

   // Helper function to load the files for an animation into an array 
   // that can be mapped to from the desired animationState
    private BodyImage[] loadAnimation(String direction, String prefix, int frameCount) {
        BodyImage[] frames = new BodyImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
           frames[i] = new BodyImage(assetPath +  direction + "/" + prefix + i + ".png", size);
        }
        return frames;
     }

     public void startAnimation(AnimationState state) {
         this.removeAllImages();
         currentState = state;
         isAnimating = true;
         currentFrame = 0;
         frameCounter = 0;
         updateAnimation();
     }

     // Update the entity with the correct sprite depending on the current 
     // frame of the animation and the direction it should face.
     private void updateAnimation() {
         this.removeAllImages();
         if (currentState == AnimationState.NEUTRAL || !isAnimating) {
            this.addImage(direction.equals("right") ? neutralRight : neutralLeft);
            return;
         }
  
         Map<AnimationState, BodyImage[]> animations = directionAnimations.get(direction);
         BodyImage[] currentAnimation = animations.get(currentState);
   
         if (currentFrame < currentAnimation.length) {
            this.addImage(currentAnimation[currentFrame]);
         } else {
            // Special handling for death animation - don't reset to neutral
            if (currentState == AnimationState.DEATH) {
               // Keep showing the last frame of death animation
               if (currentAnimation.length > 0) {
                     this.addImage(currentAnimation[currentAnimation.length - 1]);
               }
               // Don't set isAnimating to false for death animations
            } else {
               // For non-death animations, return to neutral state
               this.addImage(direction.equals("right") ? neutralRight : neutralLeft);
               isAnimating = false;
               }
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

     public void stopAnimation() {
         isAnimating = false;
     }

     public void setFacingLeft(boolean facingLeft) {
        direction = facingLeft ? "left" : "right";
        updateAnimation();
     }

}
