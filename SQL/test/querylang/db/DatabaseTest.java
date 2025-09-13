package querylang.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database database;
    private User user;

    @BeforeEach
    void setUp() {
        database = new Database();
        user = new User(52, "Jake", "Vorobey", "Kursk", 25);
    }

    @Test
    void addUser() {
        database.add(user);
        assertEquals(1, database.size());
        assertEquals(25, database.getAll().getFirst().age());
    }

    @Test
    void removeUser() {
        database.add(user);
        assertTrue(database.remove(0));
        assertFalse(database.remove(1));
    }

}