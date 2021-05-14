package tools.lexer;

import java.util.Optional;

@FunctionalInterface
public interface Lexer<T> {
    static <T> Lexer<T> create() {
        return text -> Optional.empty();
    }

    Optional<?> tryParse(String text);


}
