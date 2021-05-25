package tools.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class LexerBuilder<T> {

    private String regexes = "";
    private final List<Function<Result<String>, Lexer<T>>> flatMappers = new ArrayList<>();

    public LexerBuilder<T> add(OneGroupPattern oneGroupPattern, Function<Result<String>, Lexer<T>> flatMapper) {
        requireNonNull(oneGroupPattern);

        final var regex = oneGroupPattern.regex();
        regexes = regexes.isEmpty() ? regex : regexes + " | " + regex;
        this.flatMappers.add(flatMapper);
        return this;
    }

    public String regexes() {
        return regexes;
    }

    public List<Function<Result<String>, Lexer<T>>> flatMappers() {
        return flatMappers;
    }
}
