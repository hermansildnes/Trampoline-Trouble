package game.worlds;

import java.util.HashMap;
import java.util.Map;

import game.enemy.EnemyType;

public class WaveConfig {
    private final Map<EnemyType, Integer> enemyCounts = new HashMap<>();

    public WaveConfig add(EnemyType type, int count) {
        enemyCounts.put(type, count);
        return this;
    }

    public Map<EnemyType, Integer> getEnemyCounts() {
        return enemyCounts;
    }

    public int getTotalCount() {
        int total = 0;
        for (int count : enemyCounts.values()) {
            total += count;
        }
        return total;
    }
}
