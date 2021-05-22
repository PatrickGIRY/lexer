package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LexerRuleShould {

    public static final Function<Lexer.Result<String>, Lexer<String>> MAPPER = r -> __ -> Optional.of(r);
    private static final Lexer.OneGroupPattern NULL_ONE_GROUP_PATTERN = null;

    @Test
    void not_created_with_a_null_one_group_pattern() {
        assertThatThrownBy(() -> new Lexer.Rule<>(NULL_ONE_GROUP_PATTERN, MAPPER)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void built_from_regex() {
        final var regex = "^# (.+)$";

        var rule = Lexer.rule(regex);

        assertAll(
                () -> assertThat(rule).isNotNull(),
                () -> assertThat(rule.oneGroupPattern()).isNotNull()
        );
    }

    @Test
    void built_from_regex_and_mapper() {
        final var regex = "^# (.+)$";

        var rule = Lexer.rule(regex, MAPPER);

        assertAll(
                () -> assertThat(rule).isNotNull(),
                () -> assertThat(rule.oneGroupPattern()).isNotNull(),
                () -> assertThat(rule.mapper()).isNotNull()
        );
    }
}
