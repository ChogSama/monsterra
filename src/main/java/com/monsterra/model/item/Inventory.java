package com.monsterra.model.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Trainer's inventory holding items and their respective quantities.
 */
public class Inventory {
    private final Map<String, Integer> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public Inventory(Map<String, Integer> initialItems) {
        this.items = new HashMap<>();
        if (initialItems != null) {
            initialItems.forEach(this::addItem);
        }
    }

    public void addItem(String itemId, int quantity) {
        if (itemId == null || itemId.isBlank() || quantity <= 0) {
            return;
        }
        items.merge(itemId, quantity, Integer::sum);
    }

    public boolean removeItem(String itemId, int quantity) {
        if (itemId == null || quantity <= 0) {
            return false;
        }
        int currentCount = items.getOrDefault(itemId, 0);
        if (currentCount < quantity) {
            return false;
        }
        if (currentCount == quantity) {
            items.remove(itemId);
        } else {
            items.put(itemId, currentCount - quantity);
        }
        return true;
    }

    public boolean hasItem(String itemId, int quantity) {
        if (itemId == null || quantity <= 0) {
            return false;
        }
        return items.getOrDefault(itemId, 0) >= quantity;
    }

    public boolean hasItem(String itemId) {
        return hasItem(itemId, 1);
    }

    public int getItemCount(String itemId) {
        if (itemId == null) {
            return 0;
        }
        return items.getOrDefault(itemId, 0);
    }

    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(items, inventory.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "items=" + items +
                '}';
    }
}
