package game.worlds;

import java.util.ArrayList;

import city.cs.engine.World;
import game.Game;
import game.environment.Trampoline;
import game.player.Player;

/* Generic level specifying helper funtions and necessarry variables */
public abstract class Level extends World{
    protected int levelNumber;
    protected Player player;
    protected ArrayList<Trampoline> trampolines;
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


    public ArrayList<Trampoline> getTrampolines() {
        return this.trampolines;
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
