package game;

import java.util.HashMap;
import java.util.Map;

import city.cs.engine.BodyImage;
import city.cs.engine.Shape;
import city.cs.engine.Walker;
import city.cs.engine.World;

public abstract class Animatable extends Walker{
    
    protected String assetPath;
    protected BodyImage neutralRight;
    protected BodyImage neutralLeft;
    protected String direction = "right";

    private static final int  FRAMES_PER_ANIMATION = 4;
    
    public enum AnimationState {
      NEUTRAL,
      JUMP,
      DEATH,
      FALL,
      ATTACK
   }
   
   private Map<String, Map<AnimationState, BodyImage[]>> directionAnimations;
   private AnimationState currentState = AnimationState.NEUTRAL;
   private boolean isAnimating = false;
   private int currentFrame = 0;
   private int frameCounter = 0;


    public Animatable(World world, Shape shape, String assetPath) {
        super(world, shape);
        this.assetPath = assetPath;
        directionAnimations = new HashMap<>();
        directionAnimations.put("left", new HashMap<>());
        directionAnimations.put("right", new HashMap<>());
        neutralRight = new BodyImage(assetPath + "right/neutral.png", 4);
        neutralLeft = new BodyImage(assetPath + "left/neutral.png", 4);
        this.addImage(neutralRight);
    }

    protected void addAnimation(AnimationState state, String prefix, int frameCount) {
        directionAnimations.get("right").put(state, loadAnimation("right", prefix, frameCount));
        directionAnimations.get("left").put(state, loadAnimation("left", prefix, frameCount));
    }

    private BodyImage[] loadAnimation(String direction, String prefix, int frameCount) {
        BodyImage[] frames = new BodyImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
           frames[i] = new BodyImage(assetPath +  direction + "/" + prefix + i + ".png", 4);
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
           isAnimating = false;
           this.addImage(direction.equals("right") ? neutralRight : neutralLeft);
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

     public void setFacingLeft(boolean facingLeft) {
        direction = facingLeft ? "left" : "right";
        updateAnimation();
     }

}
