package com.monsterra.monster;

import com.monsterra.model.monster.*;
import com.monsterra.service.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectionTest {

    private Collection collection;
    private Species flametail;

    @BeforeEach
    void setUp() {
        collection = new Collection();
        flametail = SpeciesRegistry.getSpecies("flametail");
    }

    @Test
    void newCollection_isEmpty() {
        assertTrue(collection.isEmpty());
        assertEquals(0, collection.size());
        assertFalse(collection.isFull());
    }

    @Test
    void add_monsterToCollection() {
        Monster m = new Monster(flametail, 1);
        collection.add(m);
        assertEquals(1, collection.size());
        assertTrue(collection.contains(m));
    }

    @Test
    void add_duplicateThrows() {
        Monster m = new Monster(flametail, 1);
        collection.add(m);
        assertThrows(IllegalArgumentException.class, () -> collection.add(m));
    }

    @Test
    void add_nullThrows() {
        assertThrows(IllegalArgumentException.class, () -> collection.add(null));
    }

    @Test
    void remove_monsterFromCollection() {
        Monster m = new Monster(flametail, 1);
        collection.add(m);
        assertTrue(collection.remove(m));
        assertFalse(collection.contains(m));
        assertEquals(0, collection.size());
    }

    @Test
    void findById_findsMonster() {
        Monster m = new Monster(flametail, 1);
        collection.add(m);
        assertEquals(m, collection.findById(m.getId()));
        assertNull(collection.findById("nonexistent"));
    }

    @Test
    void findBySpeciesId_findsAllOfSpecies() {
        collection.add(new Monster(flametail, 1));
        collection.add(new Monster(flametail, 5));
        collection.add(new Monster(SpeciesRegistry.getSpecies("aquashell"), 1));
        assertEquals(2, collection.findBySpeciesId("flametail").size());
        assertEquals(1, collection.findBySpeciesId("aquashell").size());
    }

    @Test
    void findByNickname_findsByNickname() {
        Monster m1 = new Monster(flametail, 1);
        m1.setNickname("Blaze");
        Monster m2 = new Monster(flametail, 5);
        collection.add(m1);
        collection.add(m2);
        assertEquals(1, collection.findByNickname("Blaze").size());
        assertEquals(0, collection.findByNickname("Unknown").size());
    }

    @Test
    void getBattleReady_onlyAliveMonsters() {
        Monster m1 = new Monster(flametail, 1);
        Monster m2 = new Monster(flametail, 1);
        m2.takeDamage(100);
        collection.add(m1);
        collection.add(m2);
        assertEquals(1, collection.getBattleReady().size());
    }

    @Test
    void healAll_restoresAll() {
        Monster m = new Monster(flametail, 1);
        m.takeDamage(10);
        collection.add(m);
        collection.healAll();
        assertEquals(m.getStats().getMaxHP(), m.getCurrentHP());
    }
}
