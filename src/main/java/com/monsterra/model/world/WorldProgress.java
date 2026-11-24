package com.monsterra.model.world;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Tracks player exploration progress across regions, areas, and major boss milestones.
 */
public class WorldProgress {
    private String currentAreaId;
    private final Set<String> unlockedAreas;
    private final Set<String> defeatedBosses;
    private int storyStep;

    public WorldProgress(String initialAreaId) {
        this.currentAreaId = initialAreaId != null ? initialAreaId : "area_starter";
        this.unlockedAreas = new HashSet<>();
        this.unlockedAreas.add(this.currentAreaId);
        this.defeatedBosses = new HashSet<>();
        this.storyStep = 0;
    }

    public WorldProgress(String currentAreaId, Set<String> unlockedAreas, Set<String> defeatedBosses, int storyStep) {
        this.currentAreaId = currentAreaId != null ? currentAreaId : "area_starter";
        this.unlockedAreas = unlockedAreas != null ? new HashSet<>(unlockedAreas) : new HashSet<>();
        this.unlockedAreas.add(this.currentAreaId);
        this.defeatedBosses = defeatedBosses != null ? new HashSet<>(defeatedBosses) : new HashSet<>();
        this.storyStep = Math.max(0, storyStep);
    }

    public String getCurrentAreaId() {
        return currentAreaId;
    }

    public void setCurrentAreaId(String currentAreaId) {
        if (currentAreaId != null && !currentAreaId.isBlank()) {
            this.currentAreaId = currentAreaId;
            this.unlockedAreas.add(currentAreaId);
        }
    }

    public Set<String> getUnlockedAreas() {
        return Collections.unmodifiableSet(unlockedAreas);
    }

    public boolean unlockArea(String areaId) {
        if (areaId != null && !areaId.isBlank()) {
            return unlockedAreas.add(areaId);
        }
        return false;
    }

    public boolean isAreaUnlocked(String areaId) {
        return areaId != null && unlockedAreas.contains(areaId);
    }

    public Set<String> getDefeatedBosses() {
        return Collections.unmodifiableSet(defeatedBosses);
    }

    public boolean recordBossDefeated(String bossId) {
        if (bossId != null && !bossId.isBlank()) {
            return defeatedBosses.add(bossId);
        }
        return false;
    }

    public boolean isBossDefeated(String bossId) {
        return bossId != null && defeatedBosses.contains(bossId);
    }

    public int getStoryStep() {
        return storyStep;
    }

    public void advanceStory() {
        this.storyStep++;
    }

    public void setStoryStep(int storyStep) {
        this.storyStep = Math.max(0, storyStep);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldProgress that = (WorldProgress) o;
        return storyStep == that.storyStep &&
                Objects.equals(currentAreaId, that.currentAreaId) &&
                Objects.equals(unlockedAreas, that.unlockedAreas) &&
                Objects.equals(defeatedBosses, that.defeatedBosses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentAreaId, unlockedAreas, defeatedBosses, storyStep);
    }

    @Override
    public String toString() {
        return "WorldProgress{" +
                "currentAreaId='" + currentAreaId + '\'' +
                ", unlockedAreas=" + unlockedAreas.size() +
                ", defeatedBosses=" + defeatedBosses.size() +
                ", storyStep=" + storyStep +
                '}';
    }
}
