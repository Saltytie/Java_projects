package querylang.parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import querylang.db.Database;
import querylang.db.User;
import querylang.queries.InsertQuery;
import querylang.queries.Query;
import querylang.queries.RemoveQuery;

import static org.junit.jupiter.api.Assertions.*;

class ParsingResultTest {
    private ParsingResult<Query> removeAns, insertAns;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(0, "Vova", "Popov", "Moscow", 12);
    }

    @Test
    void insertResult()  {
        insertAns = ParsingResult.of(new InsertQuery(user));
        assertTrue(insertAns.isPresent());
    }
    @Test
    void removeResult() {
        insertAns = ParsingResult.of(new InsertQuery(user));
        removeAns = ParsingResult.of(new RemoveQuery(0));
        assertTrue(removeAns.isPresent());
        removeAns = ParsingResult.of(new RemoveQuery(0)); // хуйня
        assertTrue(removeAns.isPresent());

    }
}