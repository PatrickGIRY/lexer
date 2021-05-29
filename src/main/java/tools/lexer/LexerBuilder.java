package tools.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class LexerBuilder<T> {

    private String regexes = "";
    private final List<Function<Result<String>, ? extends Lexer<? extends T>>> flatMappers = new ArrayList<>();

    public LexerBuilder<T> add(OneCaptureGroupPattern oneCaptureGroupPattern,
                               Function<Result<String>, ? extends Lexer<? extends T>> flatMapper) {
        requireNonNull(oneCaptureGroupPattern);
        requireNonNull(flatMapper);

        final var regex = oneCaptureGroupPattern.regex();
        regexes = regexes.isEmpty() ? regex : regexes + "|" + regex;
        this.flatMappers.add(flatMapper);

        return this;
    }

    public String regexes() {
        return regexes;
    }

    public List<Function<Result<String>,? extends Lexer<? extends T>>> flatMappers() {
        return flatMappers;
    }

    public Lexer<T> build() {
        return new FastLexer<>(Pattern.compile(regexes), List.copyOf(flatMappers));
    }

    private record FastLexer<T>(Pattern pattern,
                                List<Function<Result<String>, ? extends Lexer<? extends T>>> flatMappers)
            implements Lexer<T> {

        @Override
        public Optional<Result<T>> tryParse(String text) {
            return Optional.of(pattern.matcher(text))
                    .filter(Matcher::matches)
                    .flatMap(matcher -> IntStream.range(0, matcher.groupCount())
                            .boxed()
                            .flatMap(groupIndex -> Stream.ofNullable(matcher.group(groupIndex + 1))
                                    .map(group -> new Result<>(group,
                                            matcher.start(groupIndex + 1),
                                            matcher.end(groupIndex + 1)))
                                    .map(applyFlatMapper(groupIndex)))
                            .findFirst()
                            .flatMap(lexer -> lexer.tryParse(text)));
        }

        @SuppressWarnings("unchecked")
        private Function<Result<String>, Lexer<T>> applyFlatMapper(Integer groupIndex) {
            return result -> (Lexer<T>) flatMappers.get(groupIndex).apply(result);
        }

        @Override
        public <R> Lexer<R> map(Function<T, R> mapper) {
            return Lexer.super.map(mapper);
        }
    }
}
