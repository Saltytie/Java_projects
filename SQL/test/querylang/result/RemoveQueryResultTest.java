package querylang.result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import querylang.db.Database;
import querylang.db.User;
import querylang.queries.RemoveQuery;

import static org.junit.jupiter.api.Assertions.*;

class RemoveQueryResultTest {
    private Database database;
    private RemoveQuery removeQuery;

    @BeforeEach
    void setUp() {
        database = new Database();
        removeQuery = new RemoveQuery(0);
    }

    @Test
    void message_shouldShowMessage() {
        database.add(new User(0, "ada", "da", "da", 2));
        assertEquals(1, database.size());
        RemoveQueryResult removeQueryResult = removeQuery.execute(database);
        assertEquals(0, database.size());
        assertTrue(removeQueryResult.message().contains("User with " + 0 + " was removed successfully."));
    }
}