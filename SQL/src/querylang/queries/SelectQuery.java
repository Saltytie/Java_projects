package querylang.queries;

import querylang.db.Database;
import querylang.db.User;
import querylang.result.QueryResult;
import querylang.result.SelectQueryResult;
import querylang.util.FieldGetter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SelectQuery implements Query {
    private final List<? extends FieldGetter> getters;
    private final Predicate<? super User> predicate;
    private final Comparator<? super User> comparator;

    public SelectQuery(
            List<? extends FieldGetter> getters,
            Predicate<? super User> predicate,
            Comparator<? super User> comparator
    ) {
        this.getters = getters;
        this.predicate = predicate;
        this.comparator = comparator;
    }

    @Override
    public SelectQueryResult execute(Database database) {
        // TODO: Реализовать выбор
        List<User> filtered = database.getAll().stream()
                .filter(predicate)
                .sorted(comparator)
                .toList();

        List<List<String>> results = new ArrayList<>();
        for (User user : filtered) {
            List<String> row = new ArrayList<>();
            for (FieldGetter getter : getters) {
                Object value = getter.getFieldValue(user);
                row.add(value != null ? value.toString() : "");
            }
            results.add(row);
        }

        return new SelectQueryResult(results);
    }
    public List<? extends FieldGetter> getGetters() {
        return getters;
    }
    public Predicate<? super User> getPredicate() {
        return predicate;
    }
    public Comparator<? super User> getComparator() {
        return comparator;
    }
}