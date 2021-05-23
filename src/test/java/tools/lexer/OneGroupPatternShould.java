package tools.lexer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OneGroupPatternShould {

    @ParameterizedTest
    @ValueSource(strings = {"foo", "(foo)(bar)"})
    void be_created_only_with_one_captured_group(String regex) {
        final var pattern = Pattern.compile(regex);
        assertThatThrownBy(() -> new OneGroupPattern(pattern))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(pattern + " has not one captured group");
    }
}
