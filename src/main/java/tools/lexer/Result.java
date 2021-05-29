package tools.lexer;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public record Result<T>(T value, int startIndex, int endIndex) {
    public Result {
        requireNonNull(value);
        requirePositiveOrZero(startIndex);
        requireGreaterOrEqual(endIndex, startIndex);
    }

    private void requireGreaterOrEqual(int endIndex, int startIndex) {
        if (endIndex < startIndex) {
            throw new IllegalArgumentException("End index " + endIndex
                    + " must be greater or equal than start index " + startIndex);
        }
    }

    private void requirePositiveOrZero(int startIndex) {
        if (startIndex < 0) {
            throw new IllegalArgumentException("Start index " + startIndex + " must be positive or zero");
        }
    }

    public <R> Optional<Result<R>> map(Function<? super T, ? extends R> mapper) {
        requireNonNull(mapper);
        final R newValue = mapper.apply(value());
        return newValue != null ? Optional.of(new Result<>(newValue, startIndex(), endIndex())) : Optional.empty();
    }
}
