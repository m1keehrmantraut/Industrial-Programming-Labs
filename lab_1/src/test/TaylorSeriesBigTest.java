package test;

import main.TaylorSeriesBig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class TaylorSeriesBigTest {

    @Test
    public void testCalculateValidInput() {
        TaylorSeriesBig calculator = new TaylorSeriesBig(new BigDecimal("0.1"), 6, 20);
        BigDecimal result = calculator.calculate();
        double expected = 1 / Math.pow(1.1, 3);
        assertEquals(expected, result.doubleValue(), 1e-6);
    }

    @Test
    public void testCalculateZero() {
        TaylorSeriesBig calculator = new TaylorSeriesBig(BigDecimal.ZERO, 8, 15);
        BigDecimal result = calculator.calculate();
        assertEquals(1.0, result.doubleValue(), 1e-15);
    }

    @Test
    public void testInvalidXValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TaylorSeriesBig(new BigDecimal("1.1"), 5, 10).calculate();
        });
    }
}