package game;

import city.cs.engine.SoundClip;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioManager {    
    private final Map<String, SoundClip> musicTracks;
    private final Map<String, SoundClip> soundEffects;
    
    private String currentMusic;
    
    private float volume;
    private float sfxVolume;

    public AudioManager() {
        musicTracks = new HashMap<>();
        soundEffects = new HashMap<>();
        volume = 0.5f;
        sfxVolume = 0.7f;
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

    public void loadSoundEffect(String name, String filePath) {
        try {
            if (!soundEffects.containsKey(name)) {
                SoundClip soundEffect = new SoundClip(filePath);
                soundEffects.put(name, soundEffect);
                setVolume(soundEffect);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading sound effect file: " + filePath);
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

    public void playSoundEffect(String name) {
        SoundClip soundEffect = soundEffects.get(name);
        if (soundEffect != null) {
            soundEffect.stop();
            soundEffect.play();
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

    public void setSfxVolume(float volume) {
        if (volume < 0.0f) volume = 0.0f;
        if (volume > 1.0f) volume = 1.0f;
        
        this.sfxVolume = volume;
        
        for (SoundClip clip : soundEffects.values()) {
            setVolume(clip);
        }
    }


    
    public float getVolume() {
        return volume;
    }

    public float getSfxVolume() {
        return sfxVolume;
    }

    public boolean isMusicPlaying(String name) {
        return currentMusic != null && currentMusic.equals(name);
    }

    public String getCurrentMusic() {
        return currentMusic;
    }
}