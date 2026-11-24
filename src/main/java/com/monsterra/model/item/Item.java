package com.monsterra.model.item;

import java.util.Objects;

/**
 * Represents an item that can be carried in the inventory and used in or outside of battle.
 */
public class Item {
    private final String id;
    private final String name;
    private final String description;
    private final ItemType type;
    private final int effectValue;
    private final int price;

    public Item(String id, String name, String description, ItemType type, int effectValue, int price) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Item ID cannot be null or empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Item type cannot be null.");
        }

        this.id = id;
        this.name = name;
        this.description = description != null ? description : "";
        this.type = type;
        this.effectValue = effectValue;
        this.price = Math.max(0, price);
    }

    public Item(String id, String name, ItemType type, int effectValue) {
        this(id, name, "", type, effectValue, 0);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemType getType() {
        return type;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", effectValue=" + effectValue +
                '}';
    }
}
