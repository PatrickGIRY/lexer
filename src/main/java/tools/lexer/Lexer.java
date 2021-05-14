package tools.lexer;

import java.util.Optional;

@FunctionalInterface
public interface Lexer {
    static Builder builder() {
        return new Builder();
    }

    Optional<?> tryParse(String text);

    class Builder {
        public Lexer build() {
            return text -> Optional.empty();
        }
    }
}
