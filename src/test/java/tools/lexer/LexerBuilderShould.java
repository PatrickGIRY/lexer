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
    void add_a_new_rule() {
        final var lexerBuilder = new LexerBuilder<>();

        final var newLexerBuilder = lexerBuilder.add(ONE_GROUP_PATTERN, FLAT_MAPPER);

        assertAll(
                () -> assertThat(newLexerBuilder).isNotNull(),
                () -> assertThat(newLexerBuilder.regexes()).isEqualTo(ONE_GROUP_REGEX),
                () -> assertThat(newLexerBuilder.flatMappers()).contains(FLAT_MAPPER)
        );
    }

    @Test @SuppressWarnings("unused")
    void be_typed() {
        final LexerBuilder<String> stringRulesBuilder = new LexerBuilder<>();
        final LexerBuilder<Integer> integerRulesBuilder = new LexerBuilder<>();
    }
}
