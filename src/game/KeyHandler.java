package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input events for the game. Reports the state of specific keys (left, right, down, escape).
 * @author Herman Sildnes
 * @version 1.0.0
 */
public class KeyHandler implements KeyListener{
    public boolean leftPressed, downPressed, rightPressed, escPressed;
    private int leftKey = KeyEvent.VK_A;
    private int rightKey = KeyEvent.VK_D;
    private int downKey = KeyEvent.VK_S;

    /**
     * Constructs the KeyHandler and sets the default values.
     */
    public KeyHandler() {
        leftPressed = false;
        downPressed = false;
        rightPressed = false;
        escPressed = false;
    }

    /**
     * Updates the state of the keys when they are pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if  (keyCode == leftKey) {
            leftPressed = true;
        }
        if (keyCode == downKey) {
            downPressed = true;
        }
        if (keyCode == rightKey) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            escPressed = true;
        }
    }

    /**
     * Updates the state of the keys when they are released.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if  (keyCode == leftKey) {
            leftPressed = false;
        }
        if (keyCode == downKey) {
            downPressed = false;
        }
        if (keyCode == rightKey) {
            rightPressed = false;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Updates the key codes associated with the left, right and down keys.
     * 
     * @param leftKey The key code for the left movement key
     * @param rightKey The key code for the right movement key
     * @param downKey  The key code for the down movement key
     */
    public void setKeyBindings(int leftKey, int rightKey, int downKey) {
        this.leftPressed = false;
        this.downPressed = false;
        this.rightPressed = false;

        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.downKey = downKey;
    }
}