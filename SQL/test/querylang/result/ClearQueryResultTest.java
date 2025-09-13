package querylang.result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import querylang.db.Database;
import querylang.db.User;
import querylang.queries.ClearQuery;

import static org.junit.jupiter.api.Assertions.*;

class ClearQueryResultTest {
    private Database database;
    private ClearQuery clearQuery;
    private User user;

    @BeforeEach
    void setUp() {
        database = new Database();
        clearQuery = new ClearQuery();

    }
    @Test
    void message_shouldShowMessage() {
        user = new User(0, "k", "ds", "dss", 12);
        database.add(user);
        assertEquals(1, database.size());
        ClearQueryResult clearQueryResult = clearQuery.execute(database);
        assertEquals(0, database.size());
        assertTrue(clearQueryResult.message().contains(1 + " users were removed successfully"));
    }
}