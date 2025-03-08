package game.worlds;

import java.util.ArrayList;

import city.cs.engine.World;
import game.enemy.Enemy;
import game.environment.Trampoline;
import game.environment.collectibles.Collectible;
import game.player.Player;

/* Generic level specifying helper funtions and necessarry variables */
public abstract class Level extends World{
    protected Player player;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Trampoline> trampolines;
    protected ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
    protected WaveController waveController;
    
    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    public ArrayList<Trampoline> getTrampolines() {
        return this.trampolines;
    }

    public ArrayList<Collectible> getCollectibles() {
        return this.collectibles;
    }
    public WaveController getWaveController() {
        return this.waveController;
    }

}
