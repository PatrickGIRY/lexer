package tools.lexer;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface Lexer<T> {
    static <T> Lexer<T> empty() {
        return __ -> Optional.empty();
    }

    static <T> Lexer<T> of(Result<T> result) {
        return __ -> Optional.of(result);
    }

    Optional<Result<T>> tryParse(String text);

    default <R> Lexer<R> map(Function<Result<T>, Result<R>> mapper) {
        return text -> tryParse(text).map(mapper::apply);
    }
}
