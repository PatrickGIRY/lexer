package tools.lexer;

import java.util.Optional;

public interface Lexer {
    static Lexer empty() {
        return __ -> Optional.empty();
    }

    Optional<?> tryParse(String text);
}
