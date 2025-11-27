package menu;

import main.DataInitializer;
import model.Ship;
import io.AbstractIO;
import io.TxtFileIO;
import io.JsonFileIO;
import io.XmlFileIO;
import io.LoggingShipIO;
import utils.CryptoUtils;
import utils.ArchiveUtils;

import javax.crypto.SecretKey;
import java.util.*;
import java.text.SimpleDateFormat;

public class Menu {
    private final DataInitializer data = new DataInitializer();
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    private final AbstractIO<Ship> txtIO = new LoggingShipIO(new TxtFileIO());
    private final AbstractIO<Ship> jsonIO = new LoggingShipIO(new JsonFileIO());
    private final AbstractIO<Ship> xmlIO = new LoggingShipIO(new XmlFileIO());

    public void start() {
        data.loadData("ships.txt");
        boolean running = true;
        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Показать все корабли");
            System.out.println("2. Добавить корабль");
            System.out.println("3. Обновить корабль");
            System.out.println("4. Удалить корабль");
            System.out.println("5. Сохранить в файл (TXT/JSON/XML)");
            System.out.println("6. Загрузить из файла (TXT/JSON/XML)");
            System.out.println("7. Сортировка по полю");
            System.out.println("8. Шифрование/дешифрование имени корабля");
            System.out.println("9. Архивация файла");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showAll();
                case "2" -> addShip();
                case "3" -> updateShip();
                case "4" -> deleteShip();
                case "5" -> saveMenu();
                case "6" -> loadMenu();
                case "7" -> sortMenu();
                case "8" -> cryptoMenu();
                case "9" -> archiveMenu();
                case "0" -> running = false;
                default -> System.out.println("Неверный ввод");
            }
        }
    }

    private void showAll() {
        var list = data.getListStorage().getAllList();
        if (list.isEmpty()) {
            System.out.println("Нет данных");
            return;
        }
        list.forEach(System.out::println);
    }

    private void addShip() {
        try {
            Ship.Builder builder = new Ship.Builder();

            System.out.print("ID: ");
            builder.id(Integer.parseInt(scanner.nextLine()));

            System.out.print("Название: ");
            builder.name(scanner.nextLine());

            System.out.print("Тип: ");
            builder.type(scanner.nextLine());

            System.out.print("Водоизмещение (тонны): ");
            builder.tonnage(Double.parseDouble(scanner.nextLine()));

            System.out.print("Скорость (узлы): ");
            builder.speed(Double.parseDouble(scanner.nextLine()));

            System.out.print("Дата производства (dd.MM.yyyy): ");
            builder.productionDate(df.parse(scanner.nextLine()));

            System.out.print("Цена: ");
            builder.price(Double.parseDouble(scanner.nextLine()));

            Ship s = builder.build();
            data.getListStorage().add(s);
            data.getMapStorage().add(s);
            System.out.println("Корабль добавлен.");
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }

    private void updateShip() {
        try {
            System.out.print("Введите ID корабля для обновления: ");
            int id = Integer.parseInt(scanner.nextLine());
            Ship existing = data.getListStorage().findById(id);
            if (existing == null) {
                System.out.println("Корабль не найден.");
                return;
            }

            Ship.Builder builder = new Ship.Builder().id(id);

            System.out.print("Новое название: ");
            builder.name(scanner.nextLine());

            System.out.print("Новый тип: ");
            builder.type(scanner.nextLine());

            System.out.print("Новое водоизмещение: ");
            builder.tonnage(Double.parseDouble(scanner.nextLine()));

            System.out.print("Новая скорость: ");
            builder.speed(Double.parseDouble(scanner.nextLine()));

            System.out.print("Новая дата производства (dd.MM.yyyy): ");
            builder.productionDate(df.parse(scanner.nextLine()));

            System.out.print("Новая цена: ");
            builder.price(Double.parseDouble(scanner.nextLine()));

            Ship updated = builder.build();
            data.getListStorage().update(id, updated);
            data.getMapStorage().update(id, updated);
            System.out.println("Корабль обновлён.");
        } catch (Exception e) {
            System.out.println("Ошибка обновления: " + e.getMessage());
        }
    }

    private void deleteShip() {
        try {
            System.out.print("Введите ID корабля для удаления: ");
            int id = Integer.parseInt(scanner.nextLine());
            data.getListStorage().delete(id);
            data.getMapStorage().delete(id);
            System.out.println("Корабль удалён.");
        } catch (Exception e) {
            System.out.println("Ошибка удаления: " + e.getMessage());
        }
    }

    private void saveMenu() {
        System.out.print("Выберите формат (txt/json/xml): ");
        String fmt = scanner.nextLine().toLowerCase();
        switch (fmt) {
            case "txt" -> txtIO.write("ships.txt", data.getListStorage().getAllList());
            case "json" -> jsonIO.write("ships.json", data.getListStorage().getAllList());
            case "xml" -> xmlIO.write("ships.xml", data.getListStorage().getAllList());
            default -> System.out.println("Неверный формат");
        }
    }

    private void loadMenu() {
        System.out.print("Выберите формат (txt/json/xml): ");
        String fmt = scanner.nextLine().toLowerCase();
        List<Ship> list;
        switch (fmt) {
            case "txt" -> list = txtIO.read("ships.txt");
            case "json" -> list = jsonIO.read("ships.json");
            case "xml" -> list = xmlIO.read("ships.xml");
            default -> {
                System.out.println("Неверный формат");
                return;
            }
        }
        for (Ship s : list) {
            if (data.getListStorage().findById(s.getId()) == null) {
                data.getListStorage().add(s);
                data.getMapStorage().add(s);
            } else {
                System.out.println("Корабль с ID " + s.getId() + " уже есть, пропускаем.");
            }
        }
    }


    private void sortMenu() {
        System.out.print("Выберите поле для сортировки (id/name/type/tonnage/speed/price): ");
        String field = scanner.nextLine().toLowerCase();
        List<Ship> list = data.getListStorage().getAllList();
        list.sort((s1, s2) -> switch (field) {
            case "id" -> Integer.compare(s1.getId(), s2.getId());
            case "name" -> s1.getName().compareTo(s2.getName());
            case "type" -> s1.getType().compareTo(s2.getType());
            case "tonnage" -> Double.compare(s1.getTonnage(), s2.getTonnage());
            case "speed" -> Double.compare(s1.getSpeed(), s2.getSpeed());
            case "price" -> Double.compare(s1.getPrice(), s2.getPrice());
            default -> 0;
        });
        System.out.println("Сортировка выполнена.");
    }

    private void cryptoMenu() {
        try {
            System.out.print("Введите ID корабля для шифрования имени: ");
            int id = Integer.parseInt(scanner.nextLine());
            Ship s = data.getListStorage().findById(id);
            if (s == null) {
                System.out.println("Корабль не найден");
                return;
            }
            SecretKey key = CryptoUtils.generateKey();
            String encrypted = CryptoUtils.encrypt(s.getName(), key);
            System.out.println("Зашифрованное имя: " + encrypted);
            String decrypted = CryptoUtils.decrypt(encrypted, key);
            System.out.println("Дешифрованное имя: " + decrypted);
        } catch (Exception e) {
            System.out.println("Ошибка шифрования: " + e.getMessage());
        }
    }

    private void archiveMenu() {
        try {
            System.out.print("Введите имя файла для архивации: ");
            String file = scanner.nextLine();
            System.out.print("Введите имя zip-файла: ");
            String zip = scanner.nextLine();
            ArchiveUtils.zipFile(file, zip);
            System.out.println("Файл заархивирован в " + zip);
        } catch (Exception e) {
            System.out.println("Ошибка архивации: " + e.getMessage());
        }
    }
}
