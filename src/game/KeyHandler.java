package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;

public class KeyHandler implements KeyListener{
    public boolean wPressed, aPressed, sPressed, dPressed, escPressed;

    public KeyHandler() {
        wPressed = false;
        aPressed = false;
        sPressed = false;
        dPressed = false;
        escPressed = false;
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
        if (keyCode == KeyEvent.VK_ESCAPE) {
            escPressed = true;
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