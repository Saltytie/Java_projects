package querylang.result;

import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryResult implements QueryResult {
    private final List<List<String>> selectedValues;

    public SelectQueryResult(List<List<String>> selectedValues) {
        this.selectedValues = List.copyOf(selectedValues);
    }

    @Override
    public String message() {
        // TODO: Реализовать выбор
        return selectedValues.stream()
                .map(row -> row.stream()
                        .map(value -> value != null ? value : "")
                        .collect(Collectors.joining(", ")))
                .collect(Collectors.joining("\n"));
    }
}