package tools.lexer;

import java.util.Optional;

@FunctionalInterface
public interface Lexer {
    static Lexer create() {
        return text -> Optional.empty();
    }

    Optional<?> tryParse(String text);


}
