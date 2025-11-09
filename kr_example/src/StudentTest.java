import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    @Test
    void check_converter() {
        assertEquals(1000, Student.convert_grade(new Student("mike", 12, 10)));
    }

}
