package game.player.equipments;

import java.awt.Image;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.player.Player;

/*
 * Abstract class for equipment that the player can use. Specifies helper functions and variables.
 */
public abstract class Equipment {
    protected Image icon;
    protected Player player;
    protected int ammunition;
    protected ArrayList<Enemy> enemies;

    public Equipment(Player player) {
        this.player = player;
        this.enemies = new ArrayList<Enemy>();
        updateEnemies();
    }

    public Image getIcon() {
        return icon;
    }

    public int getAmmunition() {
        return ammunition;
    }

    protected void updateEnemies() {
        if (player.getWorld().getWaveController() != null) {
            enemies = player.getWorld().getWaveController().getEnemies();
        }
    }
    
    public abstract void use(Vec2 mousePosition);


}
