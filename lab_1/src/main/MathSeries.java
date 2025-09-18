package main;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathSeries {

    public static double calculateSeries(double x, double epsilon) {
        if (Math.abs(x) >= 1) {
            throw new IllegalArgumentException("|x| должен быть < 1");
        }

        double sum = 1.0;
        int n = 1;

        while (true) {
            double term = Math.pow(-1, n) * (n + 1) * (n + 2) / 2.0 * Math.pow(x, n);
            if (Math.abs(term) < epsilon) {
                break;
            }
            sum += term;
            n++;
        }

        return sum;
    }

    public static BigDecimal calculateSeriesBig(BigDecimal x, BigDecimal epsilon, MathContext mc) {
        if (x.abs().compareTo(BigDecimal.ONE) >= 0) {
            throw new IllegalArgumentException("|x| должен быть < 1");
        }

        BigDecimal sum = BigDecimal.ONE;
        BigDecimal term = new BigDecimal("-3").multiply(x, mc);
        int n = 1;
        int maxIterations = 1000;

        for (int i = 0; i < maxIterations && term.abs().compareTo(epsilon) >= 0; i++) {
            sum = sum.add(term, mc);
            n++;

            BigDecimal numerator = new BigDecimal(-(n + 2)).multiply(x);
            BigDecimal denominator = new BigDecimal(n + 1);
            term = term.multiply(numerator).divide(denominator, mc);
        }

        return sum;
    }

    public static double getExactValue(double x) {
        return 1 / Math.pow(1 + x, 3);
    }

    public static BigDecimal getExactValueBig(BigDecimal x, MathContext mc) {
        BigDecimal onePlusX = BigDecimal.ONE.add(x);
        BigDecimal denominator = onePlusX.pow(3);
        return BigDecimal.ONE.divide(denominator, mc);
    }
}