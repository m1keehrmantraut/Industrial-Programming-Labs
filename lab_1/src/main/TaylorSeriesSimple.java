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
        if (Math.abs(x) >= 1) {
            throw new IllegalArgumentException("|x| должен быть < 1");
        }

        double sum = 1.0;
        double term = 1.0;
        int n = 1;

        while (Math.abs(term) >= epsilon) {
            term = Math.pow(-1, n) * (n + 1) * (n + 2) / 2.0 * Math.pow(x, n);
            sum += term;
            n++;
        }
        return sum;
    }

    public double getExactValue() {
        return 1 / Math.pow(1 + x, 3);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите x (|x| < 1): ");
        double x = scanner.nextDouble();

        System.out.print("Введите точность k: ");
        int k = scanner.nextInt();

        if (Math.abs(x) >= 1) {
            System.out.println("Ошибка: |x| должен быть < 1");
            return;
        }

        TaylorSeriesSimple calculator = new TaylorSeriesSimple(x, k);
        double approximate = calculator.calculate();
        double exact = calculator.getExactValue();

        Formatter formatter = new Formatter();
        formatter.format("Приближенное значение: %+0" + (k + 8) + "." + (k + 1) + "f\n", approximate);
        formatter.format("Точное значение:      %+0" + (k + 8) + "." + (k + 1) + "f\n", exact);
        formatter.format("Погрешность:          %+0" + (k + 8) + "." + (k + 1) + "f\n", Math.abs(approximate - exact));

        int intPart = (int) Math.abs(approximate);
        formatter.format("Целая часть (8): %o\n", intPart);
        formatter.format("Целая часть (16): %X\n", intPart);

        System.out.println(formatter.toString());
        scanner.close();
    }
}