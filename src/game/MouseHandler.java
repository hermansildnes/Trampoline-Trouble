package game;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.jbox2d.common.Vec2;

import java.awt.event.MouseEvent;

/**
 * Handles mouse input events for the game. Reports the mouse position and whether the mouse was clicked.
 * @author Herman Sildnes
 * @version 1.0.0
 */
public class MouseHandler implements MouseListener, MouseMotionListener {
    public Vec2 mousePosition;
    private final GameView view;
    public boolean mouseClicked;

    /** 
     * Constructs the MouseHandler and sets the default values.
     * 
     * @param view The GameView instance used to convert screen coordinates to world coordinates.
     */
    public MouseHandler(GameView view) {
        mousePosition = new Vec2(0,0);
        this.view = view;
        mouseClicked = false;
    }
    
    /**
     * Sets mouseClicked to true when the mouse is pressed.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked = true;
    }

    /**
     * Returns the mouse position in world coordinates.
     * 
     * @return The mouse position in world coordinates.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = view.viewToWorld(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }





}
