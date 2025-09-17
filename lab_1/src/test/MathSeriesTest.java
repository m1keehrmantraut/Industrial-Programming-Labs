package test;
import main.MathSeries;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class MathSeriesTest {

    @Test
    public void testArcsinApproxBasic() {
        double result = MathSeries.arcsinApprox(0.5, 1e-6);
        double expected = Math.asin(0.5);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testArcsinBigBasic() {
        MathContext mc = new MathContext(15);
        BigDecimal result = MathSeries.arcsinBig(
                new BigDecimal("0.5"),
                new BigDecimal("1e-6"),
                mc
        );

        double expected = Math.asin(0.5);
        assertEquals(expected, result.doubleValue(), 1e-6);
    }

    @Test
    public void testInvalidInputBothMethods() {
        assertThrows(IllegalArgumentException.class, () -> {
            MathSeries.arcsinApprox(1.5, 1e-6);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            MathContext mc = new MathContext(15);
            MathSeries.arcsinBig(new BigDecimal("2.0"), new BigDecimal("1e-6"), mc);
        });
    }
}