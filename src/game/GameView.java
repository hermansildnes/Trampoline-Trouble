package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import city.cs.engine.UserView;
import game.environment.collectibles.Collectible;
import game.worlds.Level;
import game.worlds.Level1;

public class GameView extends UserView {
    private Level world;
    private Image background;
    private Image[] healthImages;

    public GameView(Level world, int width, int height) {
        super(world, width, height);
        this.world = world;
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
        AffineTransform transform = g.getTransform();
        transform.scale((float)getWidth()/background.getWidth(this), (float)getHeight()/background.getHeight(this));
        g.drawImage(background, transform, this);
    }
    
    @Override
    protected void paintForeground(Graphics2D g) {
        g.drawRect(10, 10, 100, 100);

        if (world.getPlayer().hasEquipment()) {
            g.drawImage(world.getPlayer().getEquipment().getIcon(), (100/4), (100/4)+25, this);
        }
        AffineTransform transform = g.getTransform();
        transform.scale(2, 2);
        transform.translate(5+(100/4)-(healthImages[world.getPlayer().getHealth()-1].getWidth(this))/2,5);
        g.drawImage(healthImages[world.getPlayer().getHealth() - 1], transform, this);
    }
}
