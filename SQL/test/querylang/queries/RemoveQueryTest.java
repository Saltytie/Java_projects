package querylang.queries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import querylang.db.Database;
import querylang.db.User;
import querylang.result.RemoveQueryResult;

import static org.junit.jupiter.api.Assertions.*;

class RemoveQueryTest {
    private Database database;
    private RemoveQuery removeQuery;

    @BeforeEach
    void setUp() {
        database = new Database();
        removeQuery = new RemoveQuery(0);
    }

    @Test
    void execute_removeUser() {
        database.add(new User(0, "ada", "da", "da", 2));
        assertEquals(1, database.size());
        RemoveQueryResult removeQueryResult = removeQuery.execute(database);
        assertEquals(0, database.size());
    }

}