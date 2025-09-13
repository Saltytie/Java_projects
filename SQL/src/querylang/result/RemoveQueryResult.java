package querylang.result;

public class RemoveQueryResult implements QueryResult {
    private final int id;
    private final boolean success;

    public RemoveQueryResult(int id, boolean success) {
        this.id = id;
        this.success = success;
    }

    @Override
    public String message() {
        // TODO: Реализовать удаление
        if (success) {
            return "User with " + id + " was removed successfully.";
        }
        return "User not founded.";
    }
}
