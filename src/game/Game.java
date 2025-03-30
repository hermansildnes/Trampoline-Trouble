package game;

import game.GUI.MenuManager;
import game.player.PlayerController;
import game.worlds.Level;
import game.worlds.Level1;
import game.worlds.Level2;
import game.worlds.Level3;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game {
    private MenuManager menuManager;
    private Level world;
    private GameView view;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private PlayerController playerController;

    private ArrayList<GameStateListener> gameStateListeners = new ArrayList<>();

    public Game(int levelNumber, MenuManager menuManager) {
        this.menuManager = menuManager;
        loadLevel(levelNumber);
    }

    private void loadLevel(int levelNumber) {
        // Create the appropriate level
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
        mouseHandler = new MouseHandler(view);
        playerController = new PlayerController(world.getPlayer(), keyHandler, mouseHandler);
        
        // Add listeners
        world.addStepListener(playerController);
        view.addKeyListener(keyHandler);
        view.addMouseMotionListener(mouseHandler);
        view.addMouseListener(mouseHandler);
        
        // Start the world physics
        world.start();
    }

    public GameView getGameView() {
        return view;
    }

    // Method to exit to menu
    public void exitToMenu() {
        if (world != null) {
            world.stop(); // Stop the world's physics engine
        }
        menuManager.returnToMenu();
    }

    public static void main(String[] args) {
        new MenuManager();
    }

}