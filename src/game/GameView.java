package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import city.cs.engine.UserView;
import game.worlds.Level1;

public class GameView extends UserView {
    private Image background;
    private Image targetImage;

    public GameView(Level1 world, int width, int height) {
        super(world, width, height);
        background = new ImageIcon("data/assets/backgrounds/background_glacial_mountains_lightened.png").getImage();
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        AffineTransform transform = g.getTransform();
        transform.scale((float)getWidth()/background.getWidth(this), (float)getHeight()/background.getHeight(this));
        g.drawImage(background, transform, this);
    }
    
}
