package test;
import main.TaylorSeriesSimple;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaylorSeriesSimpleTest {

    @Test
    public void testCalculateSeriesValidInput() {
        TaylorSeriesSimple calculator = new TaylorSeriesSimple(0.5, 6);
        double result = calculator.calculate();
        double expected = Math.asin(0.5);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testCalculateSeriesZero() {
        TaylorSeriesSimple calculator = new TaylorSeriesSimple(0.0, 8);
        double result = calculator.calculate();
        assertEquals(0.0, result, 1e-10);
    }

    @Test
    public void testInvalidXValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TaylorSeriesSimple(1.5, 5).calculate();
        });
    }

    @Test
    public void testPrecisionParameter() {
        TaylorSeriesSimple lowPrecision = new TaylorSeriesSimple(0.5, 3);
        TaylorSeriesSimple highPrecision = new TaylorSeriesSimple(0.5, 8);

        double lowResult = lowPrecision.calculate();
        double highResult = highPrecision.calculate();

        assertTrue(Math.abs(highResult - Math.asin(0.5)) < Math.abs(lowResult - Math.asin(0.5)));
    }
}