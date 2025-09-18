package test;

import main.MathSeries;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class MathSeriesTest {

    @Test
    public void testCalculateSeriesBasic() {
        double result = MathSeries.calculateSeries(0.1, 1e-6);
        double expected = 1 / Math.pow(1.1, 3);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            MathSeries.calculateSeries(1.5, 1e-6);
        });
    }
}