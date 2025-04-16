package game.worlds;

import java.util.ArrayList;

import city.cs.engine.World;
import game.Game;
import game.enemy.Enemy;
import game.environment.Trampoline;
import game.environment.collectibles.Collectible;
import game.player.Player;

/* Generic level specifying helper funtions and necessarry variables */
public abstract class Level extends World{
    protected int levelNumber;
    protected Player player;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Trampoline> trampolines;
    protected ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
    protected WaveController waveController;
    protected Game game;
    
    public Level(int levelNumber, Game game) {
        super();
        this.game = game;
        this.levelNumber = levelNumber;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    public void removeEnemy(Enemy enemy) {
        if (enemies.contains(enemy)) {
            enemies.remove(enemy);
        }
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

    public int getLevelNumber() {
        return this.levelNumber;
    }

    public Game getGame() {
        return game;
    }

}
