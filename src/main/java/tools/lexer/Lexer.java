package tools.lexer;

import java.util.Optional;
import java.util.function.Function;

public record Lexer<T>(
        Function<String, Optional<Result<T>>> tryParse) {

    public static <T> Lexer<T> empty() {
        return new Lexer<>(__ -> Optional.empty());
    }

    public static <T> Lexer<T> of(Result<T> result) {
        return new Lexer<>(__ -> Optional.of(result));
    }

    public Optional<Result<T>> tryParse(String text) {
        return tryParse.apply(text);
    }
}
