package querylang.queries;

import querylang.db.Database;
import querylang.result.RemoveQueryResult;

public class RemoveQuery implements Query {
    private final int id;

    public RemoveQuery(int id) {
        this.id = id;
    }

    @Override
    public RemoveQueryResult execute(Database database) {
        // TODO: Реализовать удаление
        return new RemoveQueryResult(id, database.remove(id));
    }
    public int getId() {
        return id;
    }
}
