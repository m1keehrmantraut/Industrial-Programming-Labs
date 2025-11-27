package io;

import model.Ship;
import java.util.List;

public class LoggingShipIO extends IODecorator<Ship> {

    public LoggingShipIO(AbstractIO<Ship> wrappee) {
        super(wrappee);
    }

    @Override
    public List<Ship> read(String filename) {
        System.out.println("[LOG] Чтение кораблей из файла: " + filename);
        List<Ship> result = super.read(filename);
        System.out.println("[LOG] Прочитано объектов: " + result.size());
        return result;
    }

    @Override
    public void write(String filename, List<Ship> objects) {
        System.out.println("[LOG] Запись кораблей в файл: " + filename +
                ", количество объектов: " + objects.size());
        super.write(filename, objects);
        System.out.println("[LOG] Запись завершена");
    }
}
