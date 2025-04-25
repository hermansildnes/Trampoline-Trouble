package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameProgress implements Serializable {
    private static final int NUM_LEVELS = 4;
    
    private float[] levelProgress;
    private boolean[] levelUnlocked;

    public interface ProgressListener {
        void onProgressUpdated(int levelNumber, float progress);
    }
    
    public GameProgress() {
        levelProgress = new float[NUM_LEVELS];
        levelUnlocked = new boolean[NUM_LEVELS];

        levelUnlocked[0] = true;
        
    }

    public boolean updateLevelProgress(int levelNumber, float progress) {
        if (levelNumber < 1 || levelNumber > NUM_LEVELS) {
            return false;
        }

        if (progress > levelProgress[levelNumber - 1]) {
            levelProgress[levelNumber - 1] = progress;
            
            if (progress >= 0.999f && levelNumber < NUM_LEVELS) {
                levelUnlocked[levelNumber] = true;
            
            }
            return true;    
        }
        return false;
    }

    public void unlockAllLevels() {
        for (int i = 0; i < NUM_LEVELS; i++) {
            levelUnlocked[i] = true;
        }
    }

    public float getLevelProgress(int levelNumber) {
        if (levelNumber < 1 || levelNumber > NUM_LEVELS) {
            return 0;
        }
        return levelProgress[levelNumber - 1];
    }

    public boolean isLevelUnlocked(int levelNumber) {
        if (levelNumber < 1 || levelNumber > NUM_LEVELS) {
            return false;
        }
        return levelUnlocked[levelNumber - 1];
    }

    public int getNumberOfLevels() {
        return NUM_LEVELS;
    }

    public void saveToFile() {
        try {
            File saveDir = new File("saves");
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String filename = "saves/save_" + dateFormat.format(new Date()) + ".sav";
            
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(levelProgress);
                out.writeObject(levelUnlocked);
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean loadFromFile() {
        JFileChooser fileChooser = new JFileChooser("saves");
        fileChooser.setDialogTitle("Load Saved Game Progress");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Save Files (*.sav)", "sav"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return loadFromFile(selectedFile);

        }
        return false;
    }

    public boolean loadFromFile(File file) {
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                float[] loadedProgress = (float[]) in.readObject();
                boolean[] loadedUnlocked = (boolean[]) in.readObject();
                
                this.levelProgress = loadedProgress;
                this.levelUnlocked = loadedUnlocked;
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading from file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
