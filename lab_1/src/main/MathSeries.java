package main;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class MathSeries {

    public static double arcsinApprox(double x, double epsilon) {
        if (Math.abs(x) >= 1) {
            throw new IllegalArgumentException("|x| должен быть < 1");
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

    public static BigDecimal arcsinBig(BigDecimal x, BigDecimal epsilon, MathContext mc) {
        if (x.abs().compareTo(BigDecimal.ONE) >= 0) {
            throw new IllegalArgumentException("|x| должен быть < 1");
        }

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term = x;
        BigInteger n = BigInteger.ONE;
        int maxIterations = 1000;

        for (int i = 0; i < maxIterations && term.abs().compareTo(epsilon) >= 0; i++) {
            sum = sum.add(term, mc);

            BigInteger current = n;
            BigInteger next1 = n.add(BigInteger.ONE);
            BigInteger next2 = n.add(BigInteger.TWO);

            BigDecimal numerator = term.multiply(x.pow(2), mc)
                    .multiply(new BigDecimal(current.pow(2)), mc);

            BigDecimal denominator = new BigDecimal(next1.multiply(next2));

            term = numerator.divide(denominator, mc);
            n = n.add(BigInteger.TWO);
        }

        return sum;
    }
}