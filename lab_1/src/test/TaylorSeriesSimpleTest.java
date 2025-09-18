package test;

import main.TaylorSeriesSimple;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaylorSeriesSimpleTest {

    @Test
    public void testCalculateSeriesValidInput() {
        TaylorSeriesSimple calculator = new TaylorSeriesSimple(0.1, 6);
        double result = calculator.calculate();
        double expected = 1 / Math.pow(1.1, 3);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testCalculateSeriesZero() {
        TaylorSeriesSimple calculator = new TaylorSeriesSimple(0.0, 8);
        double result = calculator.calculate();
        assertEquals(1.0, result, 1e-10);
    }

    @Test
    public void testCalculateSeriesNegative() {
        TaylorSeriesSimple calculator = new TaylorSeriesSimple(-0.2, 7);
        double result = calculator.calculate();
        double expected = 1 / Math.pow(0.8, 3);
        assertEquals(expected, result, 1e-7);
    }

    @Test
    public void testInvalidXValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TaylorSeriesSimple(1.5, 5).calculate();
        });
    }
}