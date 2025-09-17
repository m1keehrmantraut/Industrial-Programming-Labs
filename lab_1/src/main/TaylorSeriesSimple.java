package main;

import java.util.Scanner;
import java.util.Formatter;

public class TaylorSeriesSimple {
    private double x;
    private double epsilon;

    public TaylorSeriesSimple(double x, int k) {
        this.x = x;
        this.epsilon = Math.pow(10, -k);
    }

    public double calculate() {
        if (Math.abs(x) > 1) {
            throw new IllegalArgumentException("x должен быть в интервале (-1, 1)");
        }

        double sum = 0;
        double term = x;
        int n = 1;

        while (Math.abs(term) >= epsilon) {
            sum += term;
            n += 2;
            term = term * x * x * (n - 2) * (n - 2) / ((n - 1) * n);
        }

        return sum;
    }

    public double getExactValue() {
        return Math.asin(x);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите x (-1 < x < 1): ");
        double x = scanner.nextDouble();

        System.out.print("Введите k (натуральное число): ");
        int k = scanner.nextInt();

        if (Math.abs(x) >= 1) {
            System.out.println("Ошибка: x должен быть в интервале (-1, 1)");
            return;
        }

        if (k <= 0) {
            System.out.println("Ошибка: k должно быть натуральным числом");
            return;
        }

        TaylorSeriesSimple series = new TaylorSeriesSimple(x, k);
        double approximate = series.calculate();
        double exact = series.getExactValue();

        Formatter formatter = new Formatter();

        formatter.format("Приближенное значение: %+0" + (k + 8) + "." + (k + 1) + "f\n", approximate);
        formatter.format("Точное значение:      %+0" + (k + 8) + "." + (k + 1) + "f\n", exact);
        formatter.format("Погрешность:          %+0" + (k + 8) + "." + (k + 1) + "f\n", Math.abs(approximate - exact));

        int intPart = (int) Math.abs(approximate);
        formatter.format("Целая часть (8): %#o\n", intPart);
        formatter.format("Целая часть (16): %#X\n", intPart);

        System.out.println(formatter.toString());
        scanner.close();
    }
}