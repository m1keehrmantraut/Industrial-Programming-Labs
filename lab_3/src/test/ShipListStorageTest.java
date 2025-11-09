package test;

import model.Ship;
import storage.ShipListStorage;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ShipListStorageTest {

    @Test
    void addAndFindShip() {
        ShipListStorage storage = new ShipListStorage();
        Ship ship = new Ship(1, "Titanic", "Cruise", 50000, 30, new Date(), 2000000);
        storage.add(ship);
        assertEquals(ship, storage.findById(1));
    }

    @Test
    void updateShip() {
        ShipListStorage storage = new ShipListStorage();
        Ship ship = new Ship(1, "Titanic", "Cruise", 50000, 30, new Date(), 2000000);
        storage.add(ship);
        Ship updated = new Ship(1, "Titanic II", "Cruise", 60000, 32, new Date(), 2500000);
        storage.update(1, updated);
        assertEquals("Titanic II", storage.findById(1).getName());
    }

    @Test
    void deleteShip() {
        ShipListStorage storage = new ShipListStorage();
        Ship ship = new Ship(1, "Titanic", "Cruise", 50000, 30, new Date(), 2000000);
        storage.add(ship);
        storage.delete(1);
        assertNull(storage.findById(1));
    }
}
