package tools.lexer;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public record Rule(OneGroupPattern oneGroupPattern, Function<Result<String>, Lexer<?>> flatMapper) {
    public Rule {
        requireNonNull(oneGroupPattern);
        requireNonNull(flatMapper);
    }
}
