package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import city.cs.engine.UserView;
import game.worlds.Level;

public class GameView extends UserView {
    private final Level world;
    private final Image background;
    private final Image[] healthImages;

    public GameView(Level world, int width, int height) {
        super(world, width, height);
        this.world = world;
        // Load necessary files
        background = new ImageIcon("data/assets/backgrounds/background_glacial_mountains_lightened.png").getImage();
        healthImages = new Image[]{
            new ImageIcon("data/assets/uibars/1hp.png").getImage(),
            new ImageIcon("data/assets/uibars/2hp.png").getImage(),
            new ImageIcon("data/assets/uibars/3hp.png").getImage(),
            new ImageIcon("data/assets/uibars/4hp.png").getImage(),
            new ImageIcon("data/assets/uibars/5hp.png").getImage(),
         };
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        // Set the background
        AffineTransform transform = g.getTransform();
        transform.scale((float)getWidth()/background.getWidth(this), (float)getHeight()/background.getHeight(this));
        g.drawImage(background, transform, this);
    }
    
    @Override
    protected void paintForeground(Graphics2D g) {
        g.drawRect(10, 10, 100, 100);

        if (world.getPlayer().hasEquipment()) {
            // Draw Equipment icon
            g.drawImage(world.getPlayer().getEquipment().getIcon(), (100/4), (100/4)+25, this);
        }
        // Draw Healthbar
        AffineTransform transform = g.getTransform();
        transform.scale(2, 2);
        transform.translate(5+(100/4)-(healthImages[world.getPlayer().getHealth()-1].getWidth(this))/2,5);
        g.drawImage(healthImages[world.getPlayer().getHealth() - 1], transform, this);

        // Draw progressbar
        int width = 250;
        g.drawRect(getWidth()/2-width/2, 10, width, 25);
        g.fillRect(getWidth()/2-width/2, 10, (int)(world.getWaveController().getProgress()*width), 25);
    }
}
