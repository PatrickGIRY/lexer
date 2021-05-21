package tools.lexer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LexerRuleShould {

    private static final Lexer.OneGroupPattern NUL_ONE_GROUP_PATTERN = null;

    @Test
    void not_created_with_a_null_one_group_pattern() {
        assertThatThrownBy(() -> new Lexer.Rule(NUL_ONE_GROUP_PATTERN)).isInstanceOf(NullPointerException.class);
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
}
