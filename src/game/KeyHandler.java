package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{
    public boolean leftPressed, downPressed, rightPressed, escPressed;
    private int leftKey = KeyEvent.VK_A;
    private int rightKey = KeyEvent.VK_D;
    private int downKey = KeyEvent.VK_S;

    public KeyHandler() {
        leftPressed = false;
        downPressed = false;
        rightPressed = false;
        escPressed = false;
    }

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

    public void setKeyBindings(int leftKey, int rightKey, int downKey) {
        this.leftPressed = false;
        this.downPressed = false;
        this.rightPressed = false;

        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.downKey = downKey;
    }
}