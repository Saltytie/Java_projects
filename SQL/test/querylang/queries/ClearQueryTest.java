package querylang.queries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import querylang.db.Database;
import querylang.db.User;
import querylang.result.ClearQueryResult;

import static org.junit.jupiter.api.Assertions.*;

class ClearQueryTest {
    private Database database;
    private User user;
    private ClearQuery clearQuery;
    @BeforeEach
    void setUp() {
        database = new Database();
        clearQuery = new ClearQuery();

    }

    @Test
    void execute_shouldClearDatabase() {

        user = new User(0, "k", "ds", "dss", 12);
        database.add(user);
        assertEquals(1, database.size());
        ClearQueryResult clearQueryResult = clearQuery.execute(database);
        assertEquals(0, database.size());
    }
}