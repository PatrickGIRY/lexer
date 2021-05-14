package tools.lexer;

import java.util.Optional;

@FunctionalInterface
public interface Lexer<T> {
    static <T> Lexer<T> create() {
        return text -> Optional.empty();
    }

    Optional<Result<T>> tryParse(String text);

    record Result<T>() {
    }
}
