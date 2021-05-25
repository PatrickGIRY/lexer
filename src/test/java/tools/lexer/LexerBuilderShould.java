package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LexerBuilderShould {

    private static final String ONE_GROUP_REGEX = "(.*)";
    private static final OneGroupPattern ONE_GROUP_PATTERN = new OneGroupPattern(Pattern.compile(ONE_GROUP_REGEX));
    private static final Function<Result<String>, Lexer<Object>> FLAT_MAPPER = __ -> Lexer.empty();

    @Test
    void add_one_new_rule() {
        final var lexerBuilder = new LexerBuilder<>();

        final var newLexerBuilder = lexerBuilder.add(ONE_GROUP_PATTERN, FLAT_MAPPER);

        assertAll(
                () -> assertThat(newLexerBuilder).isNotNull(),
                () -> assertThat(newLexerBuilder.regexes()).isEqualTo(ONE_GROUP_REGEX),
                () -> assertThat(newLexerBuilder.flatMappers()).contains(FLAT_MAPPER)
        );
    }

    @Test
    void add_two_new_rule() {
        final var lexerBuilder = new LexerBuilder<>();

        final var firstRegex = "([1-9]+\\.[0-9]+)";
        final Function<Result<String>, Lexer<Object>> flatMapper1 = r -> Lexer.of(r.map(Double::parseDouble));
        final var newLexerBuilder1 = lexerBuilder
                .add(new OneGroupPattern(Pattern.compile(firstRegex)), flatMapper1);

        final var secondRegex = "([1-9]+)";
        final Function<Result<String>, Lexer<Object>> flatMapper2 = r -> Lexer.of(r.map(Integer::parseInt));
        final var newLexerBuilder2 = newLexerBuilder1
                .add(new OneGroupPattern(Pattern.compile(secondRegex)), flatMapper2);

        assertAll(
                () -> assertThat(newLexerBuilder2).isNotNull(),
                () -> assertThat(newLexerBuilder2.regexes()).isEqualTo(firstRegex + " | " + secondRegex),
                () -> assertThat(newLexerBuilder2.flatMappers()).contains(flatMapper1, flatMapper2)
        );
    }

    @Test @SuppressWarnings("unused")
    void be_typed() {
        final LexerBuilder<String> stringRulesBuilder = new LexerBuilder<>();
        final LexerBuilder<Integer> integerRulesBuilder = new LexerBuilder<>();
    }
}
