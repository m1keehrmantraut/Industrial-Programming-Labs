package main;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Formatter;

public class TaylorSeriesBig {
    private BigDecimal x;
    private BigDecimal epsilon;
    private MathContext context;

    public TaylorSeriesBig(BigDecimal x, int k, int precision) {
        this.x = x;
        this.epsilon = BigDecimal.ONE.divide(
                BigDecimal.TEN.pow(k), new MathContext(k + 5));
        this.context = new MathContext(precision, RoundingMode.HALF_UP);
    }

    public BigDecimal calculate() {
        if (x.abs().compareTo(BigDecimal.ONE) >= 0) {
            throw new IllegalArgumentException("|x| должен быть < 1");
        }

        BigDecimal sum = BigDecimal.ONE;
        int n = 1;
        int iteration = 0;

        while (iteration < 1000) {
            BigDecimal sign = (n % 2 == 1) ? new BigDecimal("-1") : BigDecimal.ONE;
            BigDecimal coefficient = new BigDecimal((n + 1) * (n + 2)).divide(new BigDecimal("2"), context);
            BigDecimal xPower = x.pow(n, context);

            BigDecimal term = sign.multiply(coefficient, context).multiply(xPower, context);

            if (term.abs().compareTo(epsilon) < 0) {
                break;
            }

            sum = sum.add(term, context);
            n++;
            iteration++;
        }

        return sum;
    }

    public BigDecimal getExactValue() {
        BigDecimal onePlusX = BigDecimal.ONE.add(x);
        BigDecimal denominator = onePlusX.pow(3);
        return BigDecimal.ONE.divide(denominator, context);
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Введите x (|x| < 1): ");
            BigDecimal x = new BigDecimal(reader.readLine());

            System.out.print("Введите точность k: ");
            int k = Integer.parseInt(reader.readLine());

            if (x.abs().compareTo(BigDecimal.ONE) >= 0) {
                System.out.println("Ошибка: |x| должен быть < 1");
                return;
            }

            TaylorSeriesBig calculator = new TaylorSeriesBig(x, k, k + 15);
            BigDecimal approximate = calculator.calculate();
            BigDecimal exact = calculator.getExactValue();

            Formatter formatter = new Formatter();
            formatter.format("Приближенное значение: %+0" + (k + 10) + "." + (k + 1) + "f\n", approximate);
            formatter.format("Точное значение:      %+0" + (k + 10) + "." + (k + 1) + "f\n", exact);
            formatter.format("Погрешность:          %+0" + (k + 10) + "." + (k + 1) + "f\n",
                    approximate.subtract(exact).abs());

            BigInteger intPart = approximate.abs().toBigInteger();
            formatter.format("Целая часть (8): %#o\n", intPart);
            formatter.format("Целая часть (16): %#X\n", intPart);

            System.out.println(formatter.toString());

        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа");
        }
    }
}