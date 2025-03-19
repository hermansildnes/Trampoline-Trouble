package game;

import game.GUI.MenuManager;
import game.player.PlayerController;
import game.worlds.Level;
import game.worlds.Level1;
import game.worlds.Level2;
import game.worlds.Level3;

import javax.swing.JFrame;

public class Game {

    public Game() {

        // //Level world = new Level1();
        // Level world = new Level3();
        // //Level world = new Level3();
        // GameView view = new GameView(world, 800, 600);


        // KeyHandler keyHandler = new KeyHandler();
        // MouseHandler mouseHandler = new MouseHandler(view);
        // PlayerController playerController = new PlayerController(world.getPlayer(), keyHandler, mouseHandler);
        // world.addStepListener(playerController);
        // view.addKeyListener(keyHandler);
        // view.addMouseMotionListener(mouseHandler);
        // view.addMouseListener(mouseHandler);

        // final JFrame frame = new JFrame("City Game");
        // MenuManager startMenu = new MenuManager(frame);
        // frame.add(view);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setLocationByPlatform(true);
        // frame.setResizable(false);
        // frame.pack();
        // frame.setVisible(true);

        // view.setFocusable(true);
        // view.requestFocusInWindow();

        // world.start();
        MenuManager menuManager = new MenuManager();
    }
    public static void main(String[] args) {

        new Game();
    }
}
