package querylang.queries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import querylang.db.Database;
import querylang.db.User;
import querylang.result.InsertQueryResult;

import static org.junit.jupiter.api.Assertions.*;

class InsertQueryTest {
    private Database database;
    private InsertQuery insertQuery;
    @BeforeEach
    void setUp() {
        database = new Database();
        insertQuery = new InsertQuery(new User(0, "ada", "klfs", "fd", 23));
    }
    @Test
    void execute_shouldInsertUser() {
        assertEquals(0, database.size());
        InsertQueryResult insertQueryResult = insertQuery.execute(database);
        assertEquals(1, database.size());
    }

}