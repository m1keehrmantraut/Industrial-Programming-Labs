package test;
import main.PreciseArcSin;
import main.TaylorSeriesSimple;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class IntegrationTest {

    @Test
    public void testBothImplementationsGiveSimilarResults() {
        double x = 0.3;
        int k = 6;

        TaylorSeriesSimple simple = new TaylorSeriesSimple(x, k);
        double simpleResult = simple.calculate();

        PreciseArcSin precise = new PreciseArcSin(new BigDecimal(x), k, 20);
        BigDecimal preciseResult = precise.computeSeries();

        assertEquals(simpleResult, preciseResult.doubleValue(), 1e-6);
    }
}