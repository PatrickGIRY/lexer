package tools.lexer;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public record Rule<T>(OneGroupPattern oneGroupPattern, Function<Result<String>, Lexer<T>> flatMapper) {
    public Rule {
        requireNonNull(oneGroupPattern);
        requireNonNull(flatMapper);
    }
}
