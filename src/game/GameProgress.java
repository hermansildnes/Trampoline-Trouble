package game;

import java.util.ArrayList;
import java.util.List;

public class GameProgress {
    private static final int NUM_LEVELS = 3;
    private float[] levelProgress;
    private List<ProgressListener> listeners = new ArrayList<>();

    public interface ProgressListener {
        void onProgressUpdated(int levelNumber, float progress);
    }
    public GameProgress() {
        levelProgress = new float[NUM_LEVELS];
        for (int i = 0; i < NUM_LEVELS; i++) {
            levelProgress[i] = 0.0f;
        }
    }

    public boolean updateLevelProgress(int levelNumber, float progress) {
        if (progress > levelProgress[levelNumber - 1]) {
            levelProgress[levelNumber - 1] = progress;
            
            for (ProgressListener listener : listeners) {
                listener.onProgressUpdated(levelNumber, progress);
            
            }
            return true;    
        }
        return false;
    }

    public float getLevelProgress(int levelNumber) {
        return levelProgress[levelNumber - 1];
    }

    public void addListener(ProgressListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(ProgressListener listener) {
        listeners.remove(listener);
    }

    public void saveToFile() {
    }

    public void loadFromFile() {
    }

    public int getNumberOfLevels() {
        return NUM_LEVELS;
    }
}
