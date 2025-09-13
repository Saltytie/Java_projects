package querylang.result;

public class InsertQueryResult implements QueryResult {
    private final int id;

    public InsertQueryResult(int id) {
        this.id = id;
    }

    @Override
    public String message() {
        // TODO: Реализовать вставку
        return "User " + id + " was successfully added.";
    }
}
