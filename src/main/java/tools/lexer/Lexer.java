package tools.lexer;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface Lexer<T> {
    static <T> Lexer<T> empty() {
        return __ -> Optional.empty();
    }

    static <T>  Function<Result<String>, Lexer<T>> mapping(Function<String, T> mapper) {
        return result -> __ -> result.map(mapper);
    }

    Optional<Result<T>> tryParse(String text);

    default <R> Lexer<R> map(Function<T, R> mapper) {
        return text -> tryParse(text).flatMap(r -> r.map(mapper));
    }
}
