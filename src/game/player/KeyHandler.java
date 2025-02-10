package game.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean wPressed, aPressed, sPressed, dPressed;

    public KeyHandler() {
        wPressed = false;
        aPressed = false;
        sPressed = false;
        dPressed = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            wPressed = true;
        }
        if  (keyCode == KeyEvent.VK_A) {
            aPressed = true;
        }
        if (keyCode == KeyEvent.VK_S) {
            sPressed = true;
        }
        if (keyCode == KeyEvent.VK_D) {
            dPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            wPressed = false;
        }
        if  (keyCode == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (keyCode == KeyEvent.VK_S) {
            sPressed = false;
        }
        if (keyCode == KeyEvent.VK_D) {
            dPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}