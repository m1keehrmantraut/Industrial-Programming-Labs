package main;

import io.TxtFileIO;
import model.Ship;
import storage.ShipListStorage;
import storage.ShipMapStorage;
import java.util.List;

public class DataInitializer {
    private final ShipListStorage listStorage = new ShipListStorage();
    private final ShipMapStorage mapStorage = new ShipMapStorage();
    private final TxtFileIO fileIO = new TxtFileIO();

    public void loadData(String filename) {
        List<Ship> ships = fileIO.read(filename);
        for (Ship s : ships) {
            listStorage.add(s);
            mapStorage.add(s);
        }
    }

    public void saveData(String filename) {
        fileIO.write(filename, listStorage.getAllList());
    }

    public ShipListStorage getListStorage() {
        return listStorage;
    }

    public ShipMapStorage getMapStorage() {
        return mapStorage;
    }
}
