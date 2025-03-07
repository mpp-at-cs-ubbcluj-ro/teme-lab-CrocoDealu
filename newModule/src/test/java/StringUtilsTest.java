import org.example.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

    @Test
    void testPadString() {
        String result = StringUtils.padString("Hello", 8, '*');
        assertEquals("Hello***", result);
    }

    @Test
    void testToUpperCaseList() {
        List<String> words = List.of("hello", "world");
        List<String> result = StringUtils.toUpperCaseList(words);
    }
}
