package game;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.jbox2d.common.Vec2;

import java.awt.event.MouseEvent;

public class MouseHandler implements MouseListener, MouseMotionListener {
    public Vec2 mousePosition;
    private GameView view; 

    public MouseHandler(GameView view) {
        mousePosition = new Vec2(0,0);
        this.view = view;
    }

    @Override
    public void mousePressed(MouseEvent e) {
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

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = view.viewToWorld(e.getPoint());
    }




}
