package tools.lexer;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface Lexer<T> {
    static <T> Lexer<T> empty() {
        return __ -> Optional.empty();
    }

    Optional<Result<T>> tryParse(String text);

    default <R> Lexer<R> map(Function<T, R> mapper) {
        return text -> tryParse(text).flatMap(r -> r.map(mapper));
    }
}
