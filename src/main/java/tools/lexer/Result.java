package tools.lexer;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public record Result<T>(T value, int startIndex, int endIndex) {
    public Result {
        requireNonNull(value);
        requirePositiveOrZero(startIndex);
    }

    private void requirePositiveOrZero(int startIndex) {
        if (startIndex < 0) {
            throw new IllegalArgumentException("Start index " + startIndex + " must be positive or zero");
        }
    }

    public <R> Result<R> map(Function<T, R> mapper) {
        return null;
    }
}
