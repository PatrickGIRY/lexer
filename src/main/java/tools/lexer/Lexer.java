package tools.lexer;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

@FunctionalInterface
public interface Lexer<T> {

    @SuppressWarnings("unchecked")
    static <T> Lexer<T> create(Rule<? extends T>... rules) {
        requireNonNull(rules);
        final var patternOfRules = patternOfRules(rules);
        return text -> patternOfRules.matcher(text).matches()
                ? Optional.of(new Result<>((T) text, 0, text.length()))
                : Optional.empty();
    }

    private static <T> Pattern patternOfRules(Rule<? extends T>[] rules) {
        return Stream.of(rules)
                .map(Rule::oneGroupPattern)
                .map(OneGroupPattern::pattern)
                .map(Pattern::pattern)
                .collect(collectingAndThen(joining("|"), Pattern::compile));
    }

    static <R> Rule<R> rule(String regex) {
        return Lexer.rule(regex, Lexer::of);
    }

    private static <R> Lexer<R> of(Result<String> r) {
        @SuppressWarnings("unchecked") final Function<String, R> mapper = v -> (R) v;
        return __ -> Optional.of(r.map(mapper));
    }

    static <R> Rule<R> rule(String regex, Function<Result<String>, Lexer<R>> mapper) {
        return new Rule<>(oneGroupPattern(regex), mapper);
    }

    static OneGroupPattern oneGroupPattern(String regex) {
        return new OneGroupPattern(Pattern.compile(regex));
    }

    static <T> Lexer<T> empty() {
        return __ -> Optional.empty();
    }

    static <T> Lexer<T> of(T value, int valueSize) {
        return __ -> Optional.of(new Result<>(value, 0, valueSize));
    }

    Optional<Result<T>> tryParse(String text);

    record Result<T>(T value, int startIndex, int endIndex) {
        public <R> Result<R> map(Function<? super T, ? extends R> mapper) {
            requireNonNull(mapper);
            return new Result<>(mapper.apply(value()), startIndex(), endIndex());
        }
    }

    record Rule<T>(OneGroupPattern oneGroupPattern, Function<Result<String>, Lexer<T>> mapper) {
        public Rule {
            requireNonNull(oneGroupPattern);
        }
    }

    record OneGroupPattern(Pattern pattern) {
        public OneGroupPattern {
            requireNonNull(pattern);
            requireOneGroupPattern(pattern);
        }

        private void requireOneGroupPattern(Pattern pattern) {
            if (pattern.matcher("").groupCount() != 1) {
                throw new IllegalArgumentException(pattern + " has not one captured group");
            }
        }
    }
}
