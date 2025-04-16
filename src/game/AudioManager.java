package game;

import city.cs.engine.SoundClip;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioManager {
    private static AudioManager instance;
    
    private Map<String, SoundClip> musicTracks;
    
    private String currentMusic;
    
    // Volume from 0.0 - 1.0
    private float volume;
    

    private AudioManager() {
        musicTracks = new HashMap<>();
        volume = 0.5f;
    }
    
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    

    public void loadMusic(String name, String filePath) {
        try {
            if (!musicTracks.containsKey(name)) {
                SoundClip music = new SoundClip(filePath);
                musicTracks.put(name, music);
                
                setVolume(music);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading music file: " + filePath);
            e.printStackTrace();
        }
    }
    

    public void playMusic(String name) {
        stopMusic();
        
        SoundClip music = musicTracks.get(name);
        if (music != null) {
            music.loop();
            currentMusic = name;
        }
    }
    

    public void stopMusic() {
        if (currentMusic != null) {
            SoundClip music = musicTracks.get(currentMusic);
            if (music != null) {
                music.stop();
            }
            currentMusic = null;
        }
    }
    

    public void setMasterVolume(float volume) {
        if (volume < 0.0f) volume = 0.0f;
        if (volume > 1.0f) volume = 1.0f;
        
        this.volume = volume;
        
        for (SoundClip clip : musicTracks.values()) {
            setVolume(clip);
        }
    }
    
    private void setVolume(SoundClip clip) {
        if (clip != null) {
            try {
                double actualVolume = Math.pow(volume, 2)* 2;
                clip.setVolume(actualVolume);
            } catch (Exception e) {
                System.out.println("Failed to set volume: " + e.getMessage());
            }
        }
    }
    
    public float getVolume() {
        return volume;
    }
}