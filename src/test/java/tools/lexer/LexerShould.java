package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LexerShould {
    @Test
    void create_a_lexer_that_always_return_an_empty_result() {
        final var lexer = Lexer.empty();

        final var result = lexer.tryParse("foo");

        assertThat(result).isEmpty();
    }

    @Test @SuppressWarnings("unused")
    void create_an_empty_typed_lexer() {
        Lexer<String> emptyString = Lexer.empty();
        Lexer<Integer> emptyInteger = Lexer.empty();
    }

    @Test @SuppressWarnings("unused")
    void create_an_empty_lexer_with_tryParse_typed_result() {
        Optional<Result<String>> tokenString = Lexer.<String>empty().tryParse("foo");
        Optional<Result<Integer>> tokenInteger = Lexer.<Integer>empty().tryParse("123");
    }

    @Test
    void creaee_a_lexer_with_the_given_result() {
        final var givenResult = new Result<Object>();
        final var lexer = Lexer.of(givenResult);

        final var result = lexer.tryParse("foo");

        assertThat(result).hasValue(givenResult);
    }
}
