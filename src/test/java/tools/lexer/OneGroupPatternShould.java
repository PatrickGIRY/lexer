package tools.lexer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OneGroupPatternShould {

    private static final Pattern NULL_PATTERN = null;

    @ParameterizedTest
    @ValueSource(strings = {"foo", "(foo)(bar)"})
    void be_created_only_with_one_captured_group(String regex) {
        final var pattern = Pattern.compile(regex);

        assertThatThrownBy(() -> new OneGroupPattern(pattern))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(pattern + " has not one captured group");
    }

    @Test
    void be_created_with_ony_non_null_pattern() {

        assertThatThrownBy(() -> new OneGroupPattern(NULL_PATTERN))
        .isInstanceOf(NullPointerException.class);
    }
}
