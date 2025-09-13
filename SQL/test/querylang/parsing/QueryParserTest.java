package querylang.parsing;

import querylang.db.User;
import querylang.queries.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QueryParserTest {

    private final QueryParser parser = new QueryParser();

    @Test
    void parseClear_validCommand_returnsClearQuery() {
        ParsingResult<Query> result = parser.parse("CLEAR");
        assertTrue(result.isPresent());
        assertInstanceOf(ClearQuery.class, result.getValue());
    }

    @Test
    void parseClear_withArguments_returnsError() {
        ParsingResult<Query> result = parser.parse("CLEAR arg");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("'CLEAR' doesn't accept arguments"));
    }

    @Test
    void parseInsert_validCommand_returnsInsertQuery() {
        ParsingResult<Query> result = parser.parse("INSERT (John, Doe, New York, 30)");
        assertTrue(result.isPresent());
        InsertQuery query = (InsertQuery) result.getValue();
        User user = query.getUser();
        assertEquals("John", user.firstName());
        assertEquals("Doe", user.lastName());
        assertEquals("New York", user.city());
        assertEquals(30, user.age());
    }

    @Test
    void parseInsert_missingArguments_returnsError() {
        ParsingResult<Query> result = parser.parse("INSERT");
        assertFalse(result.isPresent());
        assertTrue(result.getErrorMessage().contains("requires arguments"));
    }

    @Test
    void parseInsert_missingBrackets_returnsError() {
        ParsingResult<Query> result = parser.parse("INSERT John, Doe, New York, 30");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("requires brackets"));
    }

    @Test
    void parseInsert_invalidFormat_returnsError() {
        ParsingResult<Query> result = parser.parse("INSERT (John, Doe, New York)");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("not composed according to the form"));
    }

    @Test
    void parseInsert_emptyArgument_returnsError() {
        ParsingResult<Query> result = parser.parse("INSERT (John, , New York, 30)");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("One of the arguments is empty"));
    }

    @Test
    void parseInsert_invalidAge_returnsError() {
        ParsingResult<Query> result = parser.parse("INSERT (John, Doe, New York, thirty)");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("Age must be a number"));
    }

    @Test
    void parseRemove_validCommand_returnsRemoveQuery() {
        ParsingResult<Query> result = parser.parse("REMOVE 123");
        assertTrue(result.isPresent());
        RemoveQuery query = (RemoveQuery) result.getValue();
        assertEquals(123, query.getId());
    }

    @Test
    void parseRemove_missingArguments_returnsError() {
        ParsingResult<Query> result = parser.parse("REMOVE");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("requires arguments"));
    }

    @Test
    void parseRemove_nonNumericArgument_returnsError() {
        ParsingResult<Query> result = parser.parse("REMOVE abc");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("requires one numeric argument"));
    }


    @Test
    void parseUnknownCommand_returnsError() {
        ParsingResult<Query> result = parser.parse("UPDATE something");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("Unexpected query name"));
    }
    @Test
    void parseSelectAllFields() {
        ParsingResult<Query> result = parser.parse("SELECT *");
        assertTrue(result.isPresent());
        SelectQuery query = (SelectQuery) result.getValue();
        assertEquals(5, query.getGetters().size()); // id, firstName, lastName, city, age
    }

    @Test
    void parseSelectSpecificFields() {
        ParsingResult<Query> result = parser.parse("SELECT (firstName, age)");
        assertTrue(result.isPresent());
        SelectQuery query = (SelectQuery) result.getValue();
        assertEquals(2, query.getGetters().size());
    }

    @Test
    void parseSelectWithInvalidField_shouldFail() {
        ParsingResult<Query> result = parser.parse("SELECT (invalidField)");
        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("Invalid field name"));
    }

    @Test
    void parseSelectWithFilter() {
        ParsingResult<Query> result = parser.parse("SELECT (firstName) FILTER(city, Moscow)");
        assertTrue(result.isPresent());
        SelectQuery query = (SelectQuery) result.getValue();
        assertNotNull(query.getPredicate());
    }
    @Test
    void parseSelectWithOrder() {
        ParsingResult<Query> result = parser.parse("SELECT * ORDER(age, DESC)");
        assertTrue(result.isPresent());
        SelectQuery query = (SelectQuery) result.getValue();
        assertNotNull(query.getComparator());
    }

    @Test
    void parseSelectWithFilterAndOrder() {
        ParsingResult<Query> result = parser.parse(
                "SELECT (id, firstName) FILTER(age, 30) ORDER(firstName, ASC)"
        );
        assertTrue(result.isPresent());
    }

}