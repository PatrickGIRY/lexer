package tools.lexer;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;

@FunctionalInterface
public interface Lexer<T> {

    @SuppressWarnings("unchecked")
    static <T> Lexer<T> create(Rule... rules) {
        final var patternOfRules = patternOfRules(rules);
        return text -> patternOfRules.matcher(text).matches()
                ? Optional.of(new Result<>((T)text, 0, text.length()))
                : Optional.empty();
    }

    private static Pattern patternOfRules(Rule[] rules) {
        return Stream.of(rules)
                .map(Rule::oneGroupPattern)
                .map(OneGroupPattern::pattern)
                .map(Pattern::pattern)
                .collect(collectingAndThen(joining("|"), Pattern::compile));
    }

    static Rule rule(String regex) {
        return new Rule(oneGroupPattern(regex));
    }

    static OneGroupPattern oneGroupPattern(String regex) {
        return new OneGroupPattern(Pattern.compile(regex));
    }

    Optional<Result<T>> tryParse(String text);

    record Result<T>(T value, int startIndex, int endIndex) {
    }

    record Rule(OneGroupPattern oneGroupPattern) {
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
