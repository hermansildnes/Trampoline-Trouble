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

public class Game implements StepListener{
    private MenuManager menuManager;
    private Level world;
    private GameView view;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private PlayerController playerController;
    private int leftKey = KeyEvent.VK_A;
    private int rightKey = KeyEvent.VK_D;
    private int downKey = KeyEvent.VK_S;
    private boolean isPaused = false;
    private int currentLevel;

    public Game(int levelNumber, MenuManager menuManager, int leftKey, int rightKey, int downKey) {
        this.menuManager = menuManager;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.downKey = downKey;
        this.currentLevel = levelNumber;
        loadLevel(levelNumber);
    }

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
    @Override
    public void postStep(StepEvent e) {
    }

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
            default:
                world = new Level1(this);
        }

        // Create game view
        view = new GameView(world, 800, 600);
        
        // Set up input handlers
        keyHandler = new KeyHandler();
        keyHandler.setKeyBindings(leftKey, rightKey, downKey);
        mouseHandler = new MouseHandler(view);
        playerController = new PlayerController(world.getPlayer(), keyHandler, mouseHandler);
        
        // Add listeners
        world.addStepListener(playerController);
        world.addStepListener(this);
        view.addKeyListener(keyHandler);
        view.addMouseMotionListener(mouseHandler);
        view.addMouseListener(mouseHandler);;
        
        world.start();
    }

    public GameView getGameView() {
        return view;
    }

    public void exitToMenu() {
        if (world != null) {
            world.stop();
        }
        menuManager.returnToMenu();
    }

    public static void main(String[] args) {
        new MenuManager();
    }

    public void updateKeyBindings(int leftKey, int rightKey, int downKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.downKey = downKey;

        if (keyHandler != null) {
            keyHandler.setKeyBindings(leftKey, rightKey, downKey);
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void pauseGame() {
        System.out.println("Game paused");
        if (!isPaused) {
            isPaused = true;
            if (world != null) {
                world.stop();
            }
            menuManager.showPanel("Pause Menu");
        }
    }
    
    public void resumeGame() {
        System.out.println("Game resumed");
        if (isPaused) {
            isPaused = false;
            if (world != null) {
                world.start();
            }
            view.requestFocus();
        }
    }

    public void gameOver() {
        isPaused = true;
        if (world != null) {
            world.stop();
        }

        float progress = world.getWaveController().getProgress();
        menuManager.showGameOverPanel(progress);
    }


}