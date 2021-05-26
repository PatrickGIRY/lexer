package tools.lexer;

import java.util.Optional;
import java.util.function.Function;

public class Lexer<T> {
    private final Function<String, Optional<Result<T>>> tryParse;

    private Lexer(Function<String, Optional<Result<T>>> tryParse) {
        this.tryParse = tryParse;
    }

    public static <T> Lexer<T> empty() {
        return new Lexer<>(__ -> Optional.empty());
    }

    public static <T> Lexer<T> of(Result<T> result) {
        return new Lexer<>(__ -> Optional.of(result));
    }

    public Optional<Result<T>> tryParse(String text) {
        return tryParse.apply(text);
    }
}
