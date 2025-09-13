package querylang.parsing;

import querylang.db.User;
import querylang.queries.*;
import querylang.util.FieldGetter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {

    // ЗАПРЕЩАЕТСЯ МЕНЯТЬ СИГНАТУРУ, ВОЗВРАЩАЕМЫЙ ТИП И МОДИФИКАТОРЫ ДАННОГО МЕТОДА!
    public ParsingResult<Query> parse(String line) {
        String[] parts = line.strip().split("\\s+", 2);
        String queryName = parts[0].strip().toUpperCase();
        String arguments = parts.length > 1 ? parts[1].strip() : "";
        return switch (queryName) {
            case "CLEAR" -> parseClear(arguments); // done
            // TODO: Реализовать оставшиеся команды
            case "INSERT" -> parseInsert(arguments); // done
            case "REMOVE" -> parseRemove(arguments); // done
            case "SELECT" -> parseSelect(arguments); // ?done?
            default -> ParsingResult.error("Unexpected query name '" + queryName + "'");
        };
    }

    private ParsingResult<Query> parseClear(String arguments) { // done
        if (!arguments.isEmpty()) {
            return ParsingResult.error("'CLEAR' doesn't accept arguments");
        }
        return ParsingResult.of(new ClearQuery());
    }
    private ParsingResult<Query> parseInsert(String arguments) { // done
        if (arguments.isEmpty()) {
            return ParsingResult.error("'INSERT' requires arguments.");
        }
        String content = "";
        if (!(arguments.startsWith("(") && arguments.endsWith(")"))) {
            return ParsingResult.error("Command 'INSERT' requires brackets on both sides.");
        }
        content = arguments.substring(1, arguments.length() - 1);
        String[] contentParts = content.strip().split(",\\s*");
        if (contentParts.length != 4) {
            return ParsingResult.error("The command is not composed according to the form");
        }
        for (String cur : contentParts) {
            if (cur.isEmpty()) {
                return ParsingResult.error("One of the arguments is empty");
            }
        }
        String name = contentParts[0].strip();
        String lastName = contentParts[1].strip();
        String city = contentParts[2].strip();
        try {
            Integer.parseInt(contentParts[3]);
        } catch (NumberFormatException e) {
            return ParsingResult.error("Age must be a number");
        }
        int age = Integer.parseInt(contentParts[3]);
        if (age <= 0) {
            return ParsingResult.error("Age must be a positive number");
        }
        User user = new User(0, name, lastName, city, age);
        return ParsingResult.of(new InsertQuery(user));
    }
    private ParsingResult<Query> parseRemove(String arguments) { // done
        if (arguments.isEmpty()) {
            return ParsingResult.error("'REMOVE' requires arguments.");
        }
        try {
            Integer.parseInt(arguments.strip());

        } catch (NumberFormatException e) {
            return ParsingResult.error("'REMOVE' requires one numeric argument.");
        }
        return ParsingResult.of(new RemoveQuery(Integer.parseInt(arguments)));
    }

    private static final List<String> VALID_FIELD = Arrays.asList(
            "id", "firstName", "lastName", "city", "age"
    );

    private ParsingResult<Query> parseSelect(String arguments) {
        if (arguments.isEmpty()) {
            return ParsingResult.error("'SELECT' requires arguments");
        }

        if (arguments.startsWith("*")) {
            List<FieldGetter> getters = Arrays.asList(
                    user -> String.valueOf(user.id()),
                    User::firstName,
                    User::lastName,
                    User::city,
                    user -> String.valueOf(user.age())
            );
            String rest = arguments.substring(1).strip();
            if (!rest.isEmpty() && !rest.startsWith("FILTER(") && !rest.startsWith("ORDER(")) {
                return ParsingResult.error("Invalid characters after SELECT *");
            }
            return parseSelectClauses(getters, rest);
        }

        if (!arguments.startsWith("(")) {
            return ParsingResult.error("SELECT must start with '*' or '('");
        }

        int endIndex = arguments.indexOf(')');
        if (endIndex == -1) {
            return ParsingResult.error("Unclosed parentheses in SELECT command");
        }

        String fieldsPart = arguments.substring(1, endIndex).strip();
        String rest = arguments.substring(endIndex + 1).strip();


        if (!rest.isEmpty() && !rest.startsWith("FILTER(") && !rest.startsWith("ORDER(")) {
            return ParsingResult.error("Invalid characters after field list");
        }


        if (!fieldsPart.isEmpty()) {
            String[] fieldNames = fieldsPart.split(",\\s*");
            for (String field : fieldNames) {
                if (field.trim().isEmpty()) {
                    return ParsingResult.error("Empty field name in SELECT");
                }
            }

            // проверяем на конструкцию ', '
            if (!fieldsPart.equals(fieldsPart.replaceAll(",\\s*", ", "))) {
                return ParsingResult.error("Fields must be separated by ', '");
            }
        }
        else {
            return ParsingResult.error("SELECT with empty field list");
        }

        List<FieldGetter> getters = new ArrayList<>();

        String[] fieldNames = fieldsPart.split(",\\s*");
        for (String fieldName : fieldNames) {
            String field = fieldName.strip();
            if (!VALID_FIELD.contains(field)) {
                return ParsingResult.error("Invalid field name: " + field);
            }
            getters.add(getFieldGetter(field));
        }
        return parseSelectClauses(getters, rest);
    }

    private FieldGetter getFieldGetter(String fieldName) {
        return switch (fieldName) {
            case "id" -> user -> String.valueOf(user.id());
            case "firstName" -> User::firstName;
            case "lastName" -> User::lastName;
            case "city" -> User::city;
            case "age" ->user -> String.valueOf(user.age());
            default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
        };
    }

    private ParsingResult<Query> parseSelectClauses(
            List<FieldGetter> getters,
            String clauses
    ) {
        Predicate<User> predicate = user -> true;
        Comparator<User> comparator = Comparator.comparingInt(User::id);
        boolean hasOrder = false;

        String remaining = clauses;
        while (!remaining.isEmpty()) {
            if (!remaining.startsWith("FILTER(") && !remaining.startsWith("ORDER(")) {
                return ParsingResult.error("Invalid clause, expected FILTER or ORDER");
            }

            int clauseStart = remaining.startsWith("FILTER(") ? 7 : 6;
            int clauseEnd = remaining.indexOf(')');
            if (clauseEnd == -1) {
                return ParsingResult.error("Unclosed clause in SELECT");
            }

            String clauseType = remaining.startsWith("FILTER(") ? "FILTER" : "ORDER";
            String content = remaining.substring(clauseStart, clauseEnd).strip();
            remaining = remaining.substring(clauseEnd + 1).strip();

            String[] parts = content.split(",\\s*", 2);
            if (parts.length != 2) {
                return ParsingResult.error("Invalid " + clauseType + " syntax");
            }

            String field = parts[0].strip();
            String value = parts[1].strip();

            if (!VALID_FIELD.contains(field)) {
                return ParsingResult.error("Invalid field in " + clauseType + ": " + field);
            }

            if (clauseType.equals("FILTER")) {
                Predicate<User> filter = createFilter(field, value);
                if (filter == null) {
                    return ParsingResult.error("Invalid value for field '" + field + "' in FILTER");
                }
                predicate = predicate.and(filter);
            } else {
                if (hasOrder) {
                    return ParsingResult.error("Only one ORDER clause allowed");
                }
                Comparator<User> comp = createComparator(field, value);
                if (comp == null) {
                    return ParsingResult.error("Invalid ORDER direction: " + value);
                }
                comparator = comp;
                hasOrder = true;
            }
        }

        return ParsingResult.of(new SelectQuery(getters, predicate, comparator));
    }

    private Predicate<User> createFilter(String field, String value) {
        switch (field) {
            case "id":
            case "age":
                try {
                    int intValue = Integer.parseInt(value);
                    if (field.equals("id")) {
                        return user -> user.id() == intValue;
                    } else {
                        return user -> user.age() == intValue;
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
            case "firstName":
                return user -> user.firstName().equals(value);
            case "lastName":
                return user -> user.lastName().equals(value);
            case "city":
                return user -> user.city().equals(value);
            default:
                return null;
        }
    }

    private Comparator<User> createComparator(String field, String direction) {

        Comparator<User> comparator = switch (field) {
            case "id" -> comparator = Comparator.comparingInt(User::id);
            case "age" -> comparator = Comparator.comparingInt(User::age);
            case "firstName" -> comparator = Comparator.comparing(User::firstName);
            case "lastName" -> comparator = Comparator.comparing(User::lastName);
            case "city" -> comparator = Comparator.comparing(User::city);
            default -> comparator = Comparator.comparingInt(User::id);
        };

        if (direction.equalsIgnoreCase("DESC")) {
            comparator = comparator.reversed();
        }

        return comparator.thenComparingInt(User::id);
    }
}
