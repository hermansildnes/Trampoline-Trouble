package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameProgress implements Serializable{
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
        try {
            File saveDir = new File("saves");
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String filename = "saves/save_" + dateFormat.format(new Date()) + ".sav";
            
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(levelProgress);
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
                this.levelProgress = loadedProgress;

                if (listeners != null) {
                    for (int i = 1; i <= NUM_LEVELS; i++) {
                        for (ProgressListener listener : listeners) {
                            listener.onProgressUpdated(i, levelProgress[i-1]);
                        }
                    }
                }
            return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading from file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int getNumberOfLevels() {
        return NUM_LEVELS;
    }
}
