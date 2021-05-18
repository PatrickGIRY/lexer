package tools.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

@FunctionalInterface
public interface Lexer<T> {
    @SuppressWarnings("unchecked")
    static <T> Lexer<T> create(Rule... rules) {
        return text -> rules.length > 0
                ? Optional.of(new Result<>((T) text, 0, text.length()))
                : Optional.empty();
    }

    static Rule rule(String regex) {
        return null;
    }

    static OneGroupPattern oneGroupPattern(String regex) {
        return new OneGroupPattern(Pattern.compile(regex));
    }

    Optional<Result<T>> tryParse(String text);

    record Result<T>(T value, int startIndex, int endIndex) {
    }

    class Rule {

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
