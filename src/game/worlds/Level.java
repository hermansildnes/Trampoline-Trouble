package game.worlds;

import java.util.ArrayList;

import city.cs.engine.World;
import game.environment.Trampoline;
import game.environment.collectibles.Collectible;
import game.player.Player;
import game.player.Target;

public abstract class Level extends World{
    protected Player player;
    protected Target target;
    protected ArrayList<Trampoline> trampolines;
    protected ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
    
    public Player getPlayer() {
        return this.player;
    }

    public Target getTarget() {
        return this.target;
    }

    public ArrayList<Trampoline> getTrampolines() {
        return this.trampolines;
    }

    public ArrayList<Collectible> getCollectibles() {
        return this.collectibles;
    }
}
