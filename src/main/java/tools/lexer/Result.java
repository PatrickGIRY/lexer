package tools.lexer;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public record Result<T>(T value, int startIndex, int endIndex) {
    public Result {
        requireNonNull(value);
    }

    public <R> Result<R> map(Function<T, R> mapper) {
        return null;
    }
}
