package tools.lexer;

import java.util.Optional;

public interface Lexer<T> {
    static <T> Lexer<T> empty() {
        return __ -> Optional.empty();
    }

    Optional<T> tryParse(String text);
}
