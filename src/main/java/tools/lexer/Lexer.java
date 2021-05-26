package tools.lexer;

import java.util.Optional;

@FunctionalInterface
public interface Lexer<T> {
    static <T> Lexer<T> empty() {
        return __ -> Optional.empty();
    }

    static <T> Lexer<T> of(Result<T> result) {
        return __ -> Optional.of(result);
    }

    Optional<Result<T>> tryParse(String text);
}
