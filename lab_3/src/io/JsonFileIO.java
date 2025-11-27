package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Ship;

import java.io.*;
import java.util.List;

public class JsonFileIO extends AbstractIO<Ship> {
    private final Gson gson;

    public JsonFileIO() {
        gson = new GsonBuilder()
                .setDateFormat("dd.MM.yyyy")
                .create();
    }

    @Override
    public List<Ship> read(String filename) {
        try (Reader reader = new FileReader(filename)) {
            return gson.fromJson(reader, new TypeToken<List<Ship>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public void write(String filename, List<Ship> ships) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(ships, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
