package game;

import java.awt.event.KeyEvent;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.GUI.MenuManager;
import game.player.PlayerController;
import game.worlds.Level;
import game.worlds.Level1;
import game.worlds.Level2;
import game.worlds.Level3;
import game.worlds.Level4;


/** The main class that manages the game state, levels/worlds, view and input.
 * Acts as the entry point for the game.
 * @author Herman Sildnes
 * @version 1.0.0
  */
public class Game implements StepListener{
    private final MenuManager menuManager;
    private final AudioManager audioManager;
    private Level world;
    private GameView view;
    private KeyHandler keyHandler;
    private int leftKey = KeyEvent.VK_A;
    private int rightKey = KeyEvent.VK_D;
    private int downKey = KeyEvent.VK_S;
    private boolean isPaused = false;
    private final int currentLevel;


    /** Constructs a new Game instance, initialises and loads a specified level
     * 
     * @param levelNumber The level number to load
     * @param menuManager An instance of the MenuManager to handle menu interactions
     * @param audioManager An instance of the AudioManager to handle music and sound effects
     * @param leftKey The key code for the left movement key
     * @param rightKey The key code for the right movement key
     * @param downKey The key code for the down movement key
     */
    public Game(int levelNumber, MenuManager menuManager, AudioManager audioManager, int leftKey, int rightKey, int downKey) {
        this.menuManager = menuManager;
        this.audioManager = audioManager;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.downKey = downKey;
        this.currentLevel = levelNumber;
        loadLevel(levelNumber);
    }

    /** Called before each physics step. Used here to handle pausing the game. Implementation of the StepListener
     * @param e The StepEvent object with information about the current step
     */
    @Override
    public void preStep(StepEvent e) {
        if (keyHandler.escPressed) {
            keyHandler.escPressed = false;
            if (isPaused) {
                resumeGame();
            } else {
                pauseGame();
            }
        }
    }

    /** Called after each physics step. Currently does nothing. Implementation of the StepListener
     * @param e The StepEvent object with information about the current step
     */
    @Override
    public void postStep(StepEvent e) {
    }


    /** Loads the specified level, and setus up the world, view, and input handlers 
     * @param levelNumber The level number to load
    */
    private void loadLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                world = new Level1(this);
                break;
            case 2:
                world = new Level2(this);
                break;
            case 3:
                world = new Level3(this);
                break;
            case 4:
                world = new Level4(this);
                break;
            default:
                world = new Level1(this);
        }

        // Create game view
        view = new GameView(world, 800, 600);
        
        // Set up input handlers
        keyHandler = new KeyHandler();
        keyHandler.setKeyBindings(leftKey, rightKey, downKey);
        MouseHandler mouseHandler = new MouseHandler(view);
        PlayerController playerController = new PlayerController(world.getPlayer(), keyHandler, mouseHandler);
        
        // Add listeners
        world.addStepListener(playerController);
        world.addStepListener(this);
        view.addKeyListener(keyHandler);
        view.addMouseMotionListener(mouseHandler);
        view.addMouseListener(mouseHandler);;
        
        world.start();
    }

    /** Gets the current GameView instance
     * @return The current GameView instance
     */
    public GameView getGameView() {
        return view;
    }

    /** Stops the world and returns to the main menu */
    public void exitToMenu() {
        if (world != null) {
            world.stop();
        }
        menuManager.returnToMenu();
    }

    /** Entry point for the game. Creates the MenuManager to start the UID
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new MenuManager();
    }


    /** Updates the keybindings used to control the player
     * @param leftKey The key code for the left movement key
     * @param rightKey The key code for the right movement key
     * @param downKey The key code for the down movement key
     */
    public void updateKeyBindings(int leftKey, int rightKey, int downKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.downKey = downKey;

        if (keyHandler != null) {
            keyHandler.setKeyBindings(leftKey, rightKey, downKey);
        }
    }

    /** Gets the current level number
     * @return The current level number
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /** Pauses the current game and switches panel to the pause menu */
    public void pauseGame() {
        if (!isPaused) {
            isPaused = true;
            if (world != null) {
                world.stop();
            }
            menuManager.showPanel("Pause Menu");
        }
    }
    
    /** Resumes the game state */
    public void resumeGame() {
        if (isPaused) {
            isPaused = false;
            if (world != null) {
                world.start();
            }
            view.requestFocus();
        }
    }

    /** Handles the game over state. Stops the world simulation, passes the progress to the MenuManager
     * and switches to the game over panel
     */
    public void gameOver() {
        isPaused = true;
        if (world != null) {
            world.stop();
        }

        float progress = world.getWaveController().getProgress();
        menuManager.showGameOverPanel(progress);
    }


    /** Handles the victory state. Stops the world simulation and the music, plays a victory sound
     * and switches to the victory panel
     */
    public void victory() {
        audioManager.playSoundEffect("victory");
        isPaused = true;
        if (world != null) {
            world.stop();
        }
        audioManager.stopMusic();
        menuManager.showVictoryPanel();
    }

    /** Gets the current AudioManager instance
     * @return The current AudioManager instance
     */
    public AudioManager getAudioManager() {
        return audioManager;
    }

    /** Gets the current MenuManager instance
     * @return The current MenuManager instance
     */
    public MenuManager getMenuManager() {
        return menuManager;
    }
}