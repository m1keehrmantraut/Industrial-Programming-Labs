package io;

import model.Ship;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TxtFileIO extends AbstractIO<Ship> {
    private final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    public List<Ship> read(String filename) {
        List<Ship> ships = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 7) {
                    System.out.println("Ошибка в строке " + lineNum + ": неверное количество полей");
                    lineNum++;
                    continue;
                }
                try {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String type = parts[2];
                    double tonnage = Double.parseDouble(parts[3]);
                    double speed = Double.parseDouble(parts[4]);
                    Date date = df.parse(parts[5]);
                    double price = Double.parseDouble(parts[6]);
                    if (id <= 0 || tonnage <= 0 || speed <= 0 || price <= 0 || name.isEmpty() || type.isEmpty()) {
                        System.out.println("Ошибка в строке " + lineNum + ": некорректные данные");
                    } else {
                        ships.add(new Ship(id, name, type, tonnage, speed, date, price));
                    }
                } catch (NumberFormatException | ParseException e) {
                    System.out.println("Ошибка в строке " + lineNum + ": " + e.getMessage());
                }
                lineNum++;
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        return ships;
    }

    public void write(String filename, List<Ship> ships) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Ship s : ships) {
                bw.write(s.getId() + ";" + s.getName() + ";" + s.getType() + ";" +
                        s.getTonnage() + ";" + s.getSpeed() + ";" +
                        df.format(s.getProductionDate()) + ";" + s.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}
