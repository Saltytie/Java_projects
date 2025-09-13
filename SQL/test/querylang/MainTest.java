package querylang;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testMainWithInsertCommand() {
        String input = "INSERT (John, Doe, New York, 30)\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});


        String output = outputStream.toString();
        assertTrue(output.contains("User 0 was successfully added"));
    }

    @Test
    void testMainWithClearCommand() {
        String input = "CLEAR\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("users were removed successfully"));
    }

    @Test
    void testMainWithInvalidCommand() {
        String input = "INVALID_COMMAND\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("ERROR:"));
    }
}