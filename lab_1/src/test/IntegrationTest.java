package test;

import main.MathSeries;
import main.TaylorSeriesBig;
import main.TaylorSeriesSimple;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class IntegrationTest {

    @Test
    public void testBothImplementationsGiveSimilarResults() {
        double x = 0.2;
        int k = 6;

        TaylorSeriesSimple simple = new TaylorSeriesSimple(x, k);
        double simpleResult = simple.calculate();

        TaylorSeriesBig precise = new TaylorSeriesBig(new BigDecimal(x), k, 20);
        BigDecimal preciseResult = precise.calculate();

        assertEquals(simpleResult, preciseResult.doubleValue(), 1e-5);
    }

    @Test
    public void testComparisonWithExactValue() {
        double[] testValues = {0.1, 0.2, 0.3, -0.1, -0.2};

        for (double x : testValues) {
            TaylorSeriesSimple calculator = new TaylorSeriesSimple(x, 8);
            double result = calculator.calculate();
            double expected = 1 / Math.pow(1 + x, 3);

            assertEquals(expected, result, 1e-7);
        }
    }

    @Test
    public void testBigDecimalPrecision() {
        BigDecimal x = new BigDecimal("0.123");
        int k = 10;

        TaylorSeriesBig calculator = new TaylorSeriesBig(x, k, k + 15);
        BigDecimal result = calculator.calculate();

        BigDecimal onePlusX = BigDecimal.ONE.add(x);
        BigDecimal exactValue = BigDecimal.ONE.divide(onePlusX.pow(3), new MathContext(k + 5));

        BigDecimal error = result.subtract(exactValue).abs();
        BigDecimal maxError = new BigDecimal("1e-10");

        assertTrue(error.compareTo(maxError) < 0);
    }
}