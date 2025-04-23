package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Float;

import javax.swing.ImageIcon;

import org.jbox2d.common.Vec2;

import city.cs.engine.UserView;
import game.player.Player;
import game.player.equipments.LaserGun;
import game.worlds.Level;

public class GameView extends UserView {
    private final Level world;
    private final Image background;
    private final Image[] healthImages;
    private final Image layer2;
    private final Image layer3;

    private boolean screenShaking = false;
    private float shakeAmount = 0f;
    private long shakeStartTime = 0;
    private java.util.Random random = new java.util.Random();
    private Vec2 originalCameraPosition = null;

    public GameView(Level world, int width, int height) {
        super(world, width, height);
        this.world = world;
        // Load necessary files
        //background = new ImageIcon("data/assets/backgrounds/background_glacial_mountains_lightened.png").getImage();
        //background = new ImageIcon("data/assets/backgrounds/mountains-dusk.png").getImage();
        background = new ImageIcon("data/assets/backgrounds/level" + world.getLevelNumber() + "/layer1.png").getImage();
        layer2 = new ImageIcon("data/assets/backgrounds/level" + world.getLevelNumber() + "/layer2.png").getImage();
        layer3 = new ImageIcon("data/assets/backgrounds/level" + world.getLevelNumber() + "/layer3.png").getImage();
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
        g.drawImage(layer2, transform, this);
        g.drawImage(layer3, transform, this);
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

        // Draw wave status message
        String statusMessage = world.getWaveController().getStatusMessage();
        if (statusMessage != null) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.BLACK);
            g.drawString(statusMessage, getWidth()/2-g.getFontMetrics().stringWidth(statusMessage)/2, getHeight()/3);
        }

        Player player = world.getPlayer();
        if (player != null && player.getCurrentEquipment() instanceof LaserGun) {
            LaserGun lasergun = (LaserGun) player.getCurrentEquipment();
            if (lasergun.isLaserVisible()) {
        // Draw laser with glow effect
                Float playerPosition = worldToView(player.getPosition());
                Float laserEnd = worldToView(lasergun.getLaserEnd());
                
                // Draw outer glow (light red)
                g.setColor(new Color(255, 50, 50, 100));
                g.setStroke(new java.awt.BasicStroke(8.0f));
                g.drawLine((int) playerPosition.x, (int) playerPosition.y, (int) laserEnd.x, (int) laserEnd.y);
                
                // Draw middle glow (medium red)
                g.setColor(new Color(255, 40, 40, 150));
                g.setStroke(new java.awt.BasicStroke(5.0f));
                g.drawLine((int) playerPosition.x, (int) playerPosition.y, (int) laserEnd.x, (int) laserEnd.y);
                
                // Draw inner beam (bright red)
                g.setColor(new Color(255, 230, 230));
                g.setStroke(new java.awt.BasicStroke(2.0f));
                g.drawLine((int) playerPosition.x, (int) playerPosition.y, (int) laserEnd.x, (int) laserEnd.y);
            }
        }
    }




    public void addScreenShake(float amount) {
        if (!screenShaking) {
            screenShaking = true;
            shakeAmount = amount;
            shakeStartTime = System.currentTimeMillis();
            originalCameraPosition = getCentre(); // Store the original camera position
            
            new javax.swing.Timer(16, e -> {
                long elapsed = System.currentTimeMillis() - shakeStartTime;
                if (elapsed > 200) {
                    screenShaking = false;
                    setCentre(originalCameraPosition);
                    ((javax.swing.Timer)e.getSource()).stop();
                } else {
                    float factor = 1.0f - (elapsed / 200.0f);
                    float offsetX = (random.nextFloat() * 2 - 1) * shakeAmount * factor * 3;
                    float offsetY = (random.nextFloat() * 2 - 1) * shakeAmount * factor * 3;
                    setCentre(new Vec2(
                        originalCameraPosition.x + offsetX,
                        originalCameraPosition.y + offsetY
                    ));
                }
            }).start();
        }
    }
}
