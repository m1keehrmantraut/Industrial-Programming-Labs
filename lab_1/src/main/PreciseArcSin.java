package main;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Formatter;

public class PreciseArcSin {
    private BigDecimal x;
    private BigDecimal tolerance;
    private MathContext context;

    public PreciseArcSin(BigDecimal x, int k, int digits) {
        this.x = x;
        this.tolerance = BigDecimal.ONE.divide(
                BigDecimal.TEN.pow(k), new MathContext(k + 5));
        this.context = new MathContext(digits, RoundingMode.HALF_UP);
    }

    public BigDecimal computeSeries() {
        if (x.abs().compareTo(BigDecimal.ONE) >= 0) {
            throw new IllegalArgumentException("x должен быть в интервале (-1, 1)");
        }

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal currentTerm = x;
        BigInteger counter = BigInteger.ONE;
        int steps = 0;

        while (currentTerm.abs().compareTo(tolerance) >= 0 && steps < 500) {
            sum = sum.add(currentTerm, context);

            BigInteger current = counter;
            BigInteger next1 = counter.add(BigInteger.ONE);
            BigInteger next2 = counter.add(BigInteger.TWO);

            BigDecimal numerator = currentTerm.multiply(x.pow(2), context)
                    .multiply(new BigDecimal(current.pow(2)), context);

            BigDecimal denominator = new BigDecimal(next1.multiply(next2));

            currentTerm = numerator.divide(denominator, context);
            counter = counter.add(BigInteger.TWO);
            steps++;
        }

        return sum;
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Введите x (-1 < x < 1): ");
            BigDecimal xValue = new BigDecimal(reader.readLine());

            System.out.print("Введите точность k: ");
            int k = Integer.parseInt(reader.readLine());

            if (xValue.abs().compareTo(BigDecimal.ONE) >= 0) {
                System.out.println("Ошибка: x должен быть в интервале (-1, 1)");
                return;
            }

            PreciseArcSin calculator = new PreciseArcSin(xValue, k, k + 15);
            BigDecimal result = calculator.computeSeries();

            Formatter output = new Formatter();

            output.format("Результат: %+0" + (k + 10) + "." + (k + 1) + "f\n", result);

            BigInteger integerPart = result.abs().toBigInteger();
            output.format("Целая часть (8): %#o\n", integerPart);
            output.format("Целая часть (16): %#X\n", integerPart);

            System.out.println(output);

        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа");
        }
    }
}