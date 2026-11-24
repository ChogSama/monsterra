package com.monsterra.model.world;

import java.util.Objects;

/**
 * Player-configurable runtime and audio/visual settings.
 */
public class GameSettings {
    private double musicVolume;
    private double sfxVolume;
    private String textSpeed;
    private boolean autoSaveEnabled;

    public GameSettings() {
        this(0.8, 0.8, "NORMAL", true);
    }

    public GameSettings(double musicVolume, double sfxVolume, String textSpeed, boolean autoSaveEnabled) {
        this.musicVolume = Math.clamp(musicVolume, 0.0, 1.0);
        this.sfxVolume = Math.clamp(sfxVolume, 0.0, 1.0);
        this.textSpeed = textSpeed != null ? textSpeed : "NORMAL";
        this.autoSaveEnabled = autoSaveEnabled;
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = Math.clamp(musicVolume, 0.0, 1.0);
    }

    public double getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(double sfxVolume) {
        this.sfxVolume = Math.clamp(sfxVolume, 0.0, 1.0);
    }

    public String getTextSpeed() {
        return textSpeed;
    }

    public void setTextSpeed(String textSpeed) {
        if (textSpeed != null && !textSpeed.isBlank()) {
            this.textSpeed = textSpeed;
        }
    }

    public boolean isAutoSaveEnabled() {
        return autoSaveEnabled;
    }

    public void setAutoSaveEnabled(boolean autoSaveEnabled) {
        this.autoSaveEnabled = autoSaveEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSettings that = (GameSettings) o;
        return Double.compare(that.musicVolume, musicVolume) == 0 &&
                Double.compare(that.sfxVolume, sfxVolume) == 0 &&
                autoSaveEnabled == that.autoSaveEnabled &&
                Objects.equals(textSpeed, that.textSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(musicVolume, sfxVolume, textSpeed, autoSaveEnabled);
    }

    @Override
    public String toString() {
        return "GameSettings{" +
                "musicVolume=" + musicVolume +
                ", sfxVolume=" + sfxVolume +
                ", textSpeed='" + textSpeed + '\'' +
                ", autoSave=" + autoSaveEnabled +
                '}';
    }
}
