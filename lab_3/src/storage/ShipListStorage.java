package storage;

import model.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ShipListStorage extends AbstractStorage<Ship> {
    private final List<Ship> ships = new ArrayList<>();

    public void add(Ship obj) {
        ships.add(obj);
    }

    public void update(int id, Ship newObj) {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).getId() == id) {
                ships.set(i, newObj);
                return;
            }
        }
    }

    public void delete(int id) {
        ships.removeIf(s -> s.getId() == id);
    }

    public Ship findById(int id) {
        for (Ship s : ships)
            if (s.getId() == id) return s;
        return null;
    }

    public List<Ship> getAllList() {
        return ships;
    }

    public Map<Integer, Ship> getAllMap() {
        Map<Integer, Ship> map = new HashMap<>();
        for (Ship s : ships) map.put(s.getId(), s);
        return map;
    }
}
