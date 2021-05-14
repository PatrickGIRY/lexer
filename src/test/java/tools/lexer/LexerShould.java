package tools.lexer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LexerShould {

    private static final String ANY_STRING = "Any string";

    @Test
    void have_an_empty_parsing_result_when_there_is_no_rule_defined() {
        final var lexer = Lexer.create();

        final var result = lexer.tryParse(ANY_STRING);

        assertThat(result).isEmpty();
    }
}
