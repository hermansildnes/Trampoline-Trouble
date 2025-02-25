package game;

import city.cs.engine.*;
import game.player.PlayerController;
import game.worlds.Level1;

import javax.swing.JFrame;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Game {
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private PlayerController playerController;

    public Game() {

        Level1 world = new Level1();
        GameView view = new GameView(world, 800, 600);

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler(view);
        playerController = new PlayerController(world.getPlayer(), keyHandler, mouseHandler);
        world.addStepListener(playerController);
        view.addKeyListener(keyHandler);
        view.addMouseMotionListener(mouseHandler);
        view.addMouseListener(mouseHandler);

        //optional: draw a 1-metre grid over the view
        //view.setGridResolution(1);

        final JFrame frame = new JFrame("City Game");
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        view.setFocusable(true);
        view.requestFocusInWindow();

        //optional: uncomment this to make a debugging view
         //JFrame debugView = new DebugViewer(world, 800, 600);

        world.start();
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}
