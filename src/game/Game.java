package game;

import game.player.PlayerController;
import game.worlds.Level1;

import javax.swing.JFrame;

public class Game {

    public Game() {

        Level1 world = new Level1();
        GameView view = new GameView(world, 800, 600);


        KeyHandler keyHandler = new KeyHandler();
        MouseHandler mouseHandler = new MouseHandler(view);
        PlayerController playerController = new PlayerController(world.getPlayer(), keyHandler, mouseHandler);
        world.addStepListener(playerController);
        view.addKeyListener(keyHandler);
        view.addMouseMotionListener(mouseHandler);
        view.addMouseListener(mouseHandler);

        final JFrame frame = new JFrame("City Game");
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        view.setFocusable(true);
        view.requestFocusInWindow();

         //JFrame debugView = new DebugViewer(world, 800, 600);

        world.start();
    }
    public static void main(String[] args) {

        new Game();
    }
}
