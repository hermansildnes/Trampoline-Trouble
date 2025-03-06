package game.player.equipments;

import java.awt.Image;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import game.enemy.Enemy;
import game.player.Player;


public abstract class Equipment {
    protected Image icon;
    protected Player player;
    protected ArrayList<Enemy> enemies;
    protected int ammunition;

    public Equipment(Player player) {
        this.player = player;
        this.enemies = player.getWorld().getEnemies();
    }

    public Image getIcon() {
        return icon;
    }

    public int getAmmunition() {
        return ammunition;
    }
    
    public void use(Vec2 mousePosition) {
    }


}
