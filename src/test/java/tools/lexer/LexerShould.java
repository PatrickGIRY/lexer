package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LexerShould {

    private static final String ANY_STRING = "Any string";

    @Test
    void have_an_empty_parsing_result_when_there_is_no_rule_defined() {
        final var lexer = Lexer.create();

        final var result = lexer.tryParse(ANY_STRING);

        assertThat(result).isEmpty();
    }

    @Test @SuppressWarnings("unused")
    void be_typed() {
        final Lexer<String> stringLexer = Lexer.create();
        final Lexer<Integer> integerLexer = Lexer.create();
    }

    @Test @SuppressWarnings("unused")
    void have_typed_result() {
        Optional<Lexer.Result<String>> stringResult = Lexer.<String>create().tryParse("foo");
        Optional<Lexer.Result<Integer>> integerResult = Lexer.<Integer>create().tryParse("123");
    }
}
