package utils;

import model.Ship;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShipSorter {
    public static void sortByName(List<Ship> ships) {
        Collections.sort(ships, Comparator.comparing(Ship::getName));
    }

    public static void sortByPrice(List<Ship> ships) {
        Collections.sort(ships, Comparator.comparingDouble(Ship::getPrice));
    }

    public static void sortByTonnage(List<Ship> ships) {
        ships.sort((s1, s2) -> Double.compare(s1.getTonnage(), s2.getTonnage()));
    }

    public static void sortBySpeed(List<Ship> ships) {
        ships.sort(Comparator.comparingDouble(Ship::getSpeed));
    }
}
