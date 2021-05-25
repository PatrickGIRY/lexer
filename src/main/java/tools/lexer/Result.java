package tools.lexer;

import java.util.function.Function;

public record Result<T>() {
    public <R> Result<R> map(Function<T, R> mapper) {
        return null;
    }
}
