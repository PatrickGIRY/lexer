package tools.lexer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LexerOneGroupPatternShould {

    private static final Pattern NULL_PATTERN = null;

    @Test
    void not_created_wiyh_a_null_pattern() {
        assertThatThrownBy(() -> new Lexer.OneGroupPattern(NULL_PATTERN)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"foo", "(foo)(bar)"})
    void not_created_with_not_a_one_group_pattern(String invalidRegec) {
        final var pattern = Pattern.compile(invalidRegec);

        assertThatThrownBy(() -> new Lexer.OneGroupPattern(pattern))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(pattern + " has not one captured group");
    }

    @Test
    void built_from_regex() {
        final var oneGroupPattern = Lexer.oneGroupPattern("(foo)");

        assertAll(
                () -> assertThat(oneGroupPattern).isNotNull(),
                () -> assertThat(oneGroupPattern.pattern()).isNotNull()
        );
    }
}
