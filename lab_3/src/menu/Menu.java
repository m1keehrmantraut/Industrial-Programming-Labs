package menu;

import main.DataInitializer;
import model.Ship;
import java.util.*;
import java.text.SimpleDateFormat;

public class Menu {
    private final DataInitializer data = new DataInitializer();
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    public void start() {
        data.loadData("ships.txt");
        boolean running = true;
        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Показать все корабли");
            System.out.println("2. Добавить корабль");
            System.out.println("3. Обновить корабль");
            System.out.println("4. Удалить корабль");
            System.out.println("5. Сохранить в файл");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showAll();
                case "2" -> addShip();
                case "3" -> updateShip();
                case "4" -> deleteShip();
                case "5" -> data.saveData("ships.txt");
                case "0" -> running = false;
                default -> System.out.println("Неверный ввод");
            }
        }
    }

    private void showAll() {
        var list = data.getListStorage().getAllList();
        if (list.isEmpty()) System.out.println("Нет данных");
        else list.forEach(System.out::println);
    }

    private void addShip() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Название: ");
            String name = scanner.nextLine();
            System.out.print("Тип: ");
            String type = scanner.nextLine();
            System.out.print("Водоизмещение (тонны): ");
            double tonnage = Double.parseDouble(scanner.nextLine());
            System.out.print("Скорость (узлы): ");
            double speed = Double.parseDouble(scanner.nextLine());
            System.out.print("Дата производства (dd.MM.yyyy): ");
            Date date = df.parse(scanner.nextLine());
            System.out.print("Цена: ");
            double price = Double.parseDouble(scanner.nextLine());
            Ship s = new Ship(id, name, type, tonnage, speed, date, price);
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
            System.out.print("Новое название: ");
            String name = scanner.nextLine();
            System.out.print("Новый тип: ");
            String type = scanner.nextLine();
            System.out.print("Новое водоизмещение: ");
            double tonnage = Double.parseDouble(scanner.nextLine());
            System.out.print("Новая скорость: ");
            double speed = Double.parseDouble(scanner.nextLine());
            System.out.print("Новая дата производства (dd.MM.yyyy): ");
            Date date = df.parse(scanner.nextLine());
            System.out.print("Новая цена: ");
            double price = Double.parseDouble(scanner.nextLine());
            Ship updated = new Ship(id, name, type, tonnage, speed, date, price);
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
}
