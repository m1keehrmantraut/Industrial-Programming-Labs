package unit_tests;

import main.StringProcessor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class StringProcessorTests {

    @Test
    public void testBasicStringProcessing() {
        String input = "привет,данные123;14:25;1223.0034;мир 5.67E-3...утро 23-59|конец456";
        String delimiters = ",;:.-|";

        StringProcessor processor = new StringProcessor(input, delimiters);
        String result = processor.process();

        assertNotNull(result);
        assertTrue(result.contains("Исходная строка:"));
        assertTrue(result.contains("Разделители:"));
    }

    @Test
    public void testNumberExtraction() {
        String input = "числа: 123.45, -67.89, 100, 0.5";
        String delimiters = ", ";

        StringProcessor processor = new StringProcessor(input, delimiters);
        List<Double> numbers = processor.extractNumbers();

        assertEquals(4, numbers.size());
        assertTrue(numbers.contains(123.45));
        assertTrue(numbers.contains(-67.89));
        assertTrue(numbers.contains(100.0));
        assertTrue(numbers.contains(0.5));
    }

    @Test
    public void testTimeFormatValidation() {
        StringProcessor processor = new StringProcessor("test", " ");

        assertTrue(processor.validateTimeLexeme("14-25"));
        assertTrue(processor.validateTimeLexeme("00-00"));
        assertTrue(processor.validateTimeLexeme("23-59"));

        assertFalse(processor.validateTimeLexeme("24-00"));
        assertFalse(processor.validateTimeLexeme("23-60"));
        assertFalse(processor.validateTimeLexeme("abc-12"));
        assertFalse(processor.validateTimeLexeme(null));
    }

    @Test
    public void testExponentialNotationExtraction() {
        String input = "научные числа: 1.23E4, -5.67e-3, 8.9E+10";
        String delimiters = ", ";

        StringProcessor processor = new StringProcessor(input, delimiters);
        List<String> exponentials = processor.extractExponential();

        assertEquals(3, exponentials.size());
        assertTrue(exponentials.contains("1.23E4"));
        assertTrue(exponentials.contains("-5.67e-3"));
        assertTrue(exponentials.contains("8.9E+10"));
    }

    @Test
    public void testEmptyAndEdgeCases() {
        StringProcessor processor = new StringProcessor("", ",;");
        String result = processor.process();

        assertTrue(result.contains("Исходная строка:"));
        assertTrue(result.contains("Разделители:"));

        assertTrue(result.contains("Подстроки: [") || result.contains("Подстроки: []"));
        assertTrue(result.contains("Вещественные числа: [") || result.contains("Вещественные числа: []"));
        assertTrue(result.contains("Время (ЧЧ-ММ): [") || result.contains("Время (ЧЧ-ММ): []"));
    }

    @Test
    public void testRemoveSmallestSubstring() {
        String input = "текст123 данные456 информация789";
        String delimiters = " ";

        StringProcessor processor = new StringProcessor(input, delimiters);
        String result = processor.removeSmallestSubstring();

        assertNotEquals(input, result);
        assertTrue(result.length() < input.length());
    }

    @Test
    public void testComplexRealWorldScenario() {
        String input = "привет,данные123;14:25;1223.0034;мир 5.67E-3...утро 23-59|конец456";
        String delimiters = ",;:.-|";

        StringProcessor processor = new StringProcessor(input, delimiters);
        String result = processor.process();

        assertTrue(result.contains("Исходная строка:"));
        assertTrue(result.contains("Разделители:"));
        assertTrue(result.contains("Подстроки:"));
        assertTrue(result.contains("Вещественные числа:"));
        assertTrue(result.contains("Время (ЧЧ-ММ):"));
        assertTrue(result.contains("Форматированные числа:") || result.contains("Форматированные числа:"));
        assertTrue(result.contains("Строка со случайным числом:"));
        assertTrue(result.contains("После удаления подстрок:"));
    }

    @Test
    public void testMultipleDelimiterSupport() {
        String input = "word1,word2;word3:word4.word5-word6|word7";
        String delimiters = ",;:.-|";

        StringProcessor processor = new StringProcessor(input, delimiters);
        String result = processor.process();

        assertTrue(result.contains("Подстроки:"));

        assertTrue(result.contains("word1") || result.contains("word2") ||
                result.contains("word3") || result.contains("word4") ||
                result.contains("word5") || result.contains("word6") ||
                result.contains("word7"));
    }

    @Test
    public void testNumberFormattingInOutput() {
        String input = "100 50.5 25.25";
        String delimiters = " ";

        StringProcessor processor = new StringProcessor(input, delimiters);
        String result = processor.process();

        assertTrue(result.contains("Форматированные числа:") || result.contains("Форматированные числа:"));

        assertTrue(result.contains("100") || result.contains("50.5") || result.contains("25.25"));
    }
}