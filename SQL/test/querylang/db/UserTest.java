package querylang.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user1;
    private User user2;
    private User user3;
    private Database database;
    @BeforeEach
    void setUp() {
        database = new Database();
        user1 = new User(32, "Kirill", "Fisher", "Volgograd", 312);
        user2 = new User(32, "Kirill", "Fisher", "Volgograd", 312);
        user3 = new User(31, "d", "ds", "d232", 123);
    }
    @Test
    void userEqualsUser() {
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }

    @Test
    void testGetter() {
        database.add(user1);
        assertEquals("Volgograd", user1.city());
        assertEquals(1, database.size());
    }

}