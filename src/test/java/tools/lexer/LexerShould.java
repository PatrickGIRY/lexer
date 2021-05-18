package tools.lexer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LexerShould {

    private static final String ANY_STRING = "Any string";

    @Test
    void have_an_empty_parsing_result_when_there_is_no_rule_defined() {
        final var lexer = Lexer.create();

        final var result = lexer.tryParse(ANY_STRING);

        assertThat(result).isEmpty();
    }

    @Test
    @SuppressWarnings("unused")
    void be_typed() {
        final Lexer<String> stringLexer = Lexer.create();
        final Lexer<Integer> integerLexer = Lexer.create();
    }

    @Test
    @SuppressWarnings("unused")
    void have_typed_result() {
        Optional<Lexer.Result<String>> stringResult = Lexer.<String>create().tryParse("foo");
        Optional<Lexer.Result<Integer>> integerResult = Lexer.<Integer>create().tryParse("123");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "1468"})
    void return_result_when_there_a_rule_with_a_one_group_regex_that_matches_the_parsed_text(String text) {
        var lexer = Lexer.create(Lexer.rule("([0-9]+)"));

        var result = lexer.tryParse(text);

        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.map(Lexer.Result::value)).hasValue(text),
                () -> assertThat(result.map(Lexer.Result::startIndex)).hasValue(0),
                () -> assertThat(result.map(Lexer.Result::endIndex)).hasValue(text.length())
        );
    }

    @Test
    void return_empty_when_there_is_no_rule_with_a_one_group_regex_that_amtches_the_parsed_text() {
        var lexer = Lexer.create(Lexer.rule("([0-9]+)"));

        var result = lexer.tryParse("foo");

        assertThat(result).isEmpty();
    }

}
