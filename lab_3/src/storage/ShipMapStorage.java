package storage;

import model.Ship;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeMap;

public class ShipMapStorage extends AbstractStorage<Ship> {
    private final Map<Integer, Ship> ships = new TreeMap<>();

    public void add(Ship obj) {
        ships.put(obj.getId(), obj);
    }

    public void update(int id, Ship newObj) {
        ships.put(id, newObj);
    }

    public void delete(int id) {
        ships.remove(id);
    }

    public Ship findById(int id) {
        return ships.get(id);
    }

    public List<Ship> getAllList() {
        return new ArrayList<>(ships.values());
    }

    public Map<Integer, Ship> getAllMap() {
        return ships;
    }
}
