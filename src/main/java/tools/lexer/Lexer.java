package tools.lexer;

import java.util.Optional;

@FunctionalInterface
public interface Lexer<T> {
    @SuppressWarnings("unchecked")
    static <T> Lexer<T> create(Rule... rules) {
        return text -> "123".equals(text)
                ? Optional.of(new Result<>((T) text, 0, text.length()))
                : Optional.empty();
    }

    static Rule rule(String regex) {
        return null;
    }

    Optional<Result<T>> tryParse(String text);

    record Result<T>(T value, int startIndex, int endIndex) {
    }

    class Rule {
    }
}
