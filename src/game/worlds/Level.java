package game.worlds;

import java.util.ArrayList;

import city.cs.engine.World;
import game.environment.Trampoline;
import game.player.Player;

public abstract class Level extends World{
    protected Player player;
    protected ArrayList<Trampoline> trampolines;
    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Trampoline> getTrampolines() {
        return this.trampolines;
    }

    public Level() {
        super();
    }
}
