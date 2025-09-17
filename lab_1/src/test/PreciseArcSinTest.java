package test;
import main.PreciseArcSin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class PreciseArcSinTest {

    @Test
    public void testComputeSeriesValidInput() {
        PreciseArcSin calculator = new PreciseArcSin(new BigDecimal("0.5"), 6, 20);
        BigDecimal result = calculator.computeSeries();
        double expected = Math.asin(0.5);

        assertEquals(expected, result.doubleValue(), 1e-6);
    }

    @Test
    public void testComputeSeriesZero() {
        PreciseArcSin calculator = new PreciseArcSin(BigDecimal.ZERO, 8, 15);
        BigDecimal result = calculator.computeSeries();

        assertEquals(0.0, result.doubleValue(), 1e-15);
    }

    @Test
    public void testInvalidXValueBigDecimal() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PreciseArcSin(new BigDecimal("1.1"), 5, 10).computeSeries();
        });
    }
}