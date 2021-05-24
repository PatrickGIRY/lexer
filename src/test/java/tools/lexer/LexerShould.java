package tools.lexer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LexerShould {
    @Test
    void create_a_lexer_that_always_return_an_empty_result() {
        final var lexer = Lexer.empty();

        final var result = lexer.tryParse("foo");

        assertThat(result).isEmpty();
    }
}
