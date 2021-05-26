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
    private final List<Function<Result<String>, Lexer<T>>> flatMappers = new ArrayList<>();

    public LexerBuilder<T> add(OneGroupPattern oneGroupPattern, Function<Result<String>, Lexer<T>> flatMapper) {
        requireNonNull(oneGroupPattern);
        requireNonNull(flatMapper);

        final var regex = oneGroupPattern.regex();
        regexes = regexes.isEmpty() ? regex : regexes + " | " + regex;
        this.flatMappers.add(flatMapper);

        return this;
    }

    public String regexes() {
        return regexes;
    }

    public List<Function<Result<String>, Lexer<T>>> flatMappers() {
        return flatMappers;
    }

    public Lexer<T> build() {
        final var pattern = Pattern.compile(regexes);
        return text -> Optional.of(pattern.matcher(text))
                .filter(Matcher::matches)
                .flatMap(matcher -> IntStream.range(0, matcher.groupCount())
                        .boxed()
                        .flatMap(toResult(matcher))
                        .findFirst()
                        .flatMap(lexer -> lexer.tryParse(text)));
    }

    private Function<Integer, Stream<Lexer<T>>> toResult(Matcher matcher) {
        return groupIndex -> Stream.ofNullable(matcher.group(groupIndex + 1))
                .map(toStringResult(matcher, groupIndex))
                .map(applyFlatMapper(groupIndex));
    }

    private Function<String, Result<String>> toStringResult(Matcher matcher, int groupIndex) {
        return group -> new Result<>(group, matcher.start(groupIndex + 1), matcher.end(groupIndex + 1));
    }

    private Function<Result<String>, Lexer<T>> applyFlatMapper(int groupIndex) {
        return result -> flatMappers.get(groupIndex).apply(result);
    }
}
