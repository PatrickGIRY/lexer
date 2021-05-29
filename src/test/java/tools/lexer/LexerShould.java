package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.regex.Pattern;

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
    void created_with_a_rule_that_parse_and_transform_the_given_text() {
        final var lexer =  new LexerBuilder<>()
                .add(new OneCaptureGroupPattern(Pattern.compile("([0-9]+)")), Lexer.mapping(Integer::parseInt))
                .build();

        final var result = lexer.tryParse("234");

        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.map(Result::value)).hasValue(234),
                () -> assertThat(result.map(Result::startIndex)).hasValue(0),
                () -> assertThat(result.map(Result::endIndex)).hasValue(3)
        );
    }

    @Test
    void created_with_two_rules_that_parse_and_transform_the_given_text() {
        final var lexer =  new LexerBuilder<>()
                .add(new OneCaptureGroupPattern(Pattern.compile("([0-9]+)")), r -> __ -> r.map(Integer::parseInt))
                .add(new OneCaptureGroupPattern(Pattern.compile("([1-9]+\\.[0-9]+)")), r -> __ -> r.map(Double::parseDouble))
                .build();

        final var result1 = lexer.tryParse("234");

        assertAll(
                () -> assertThat(result1).isNotNull(),
                () -> assertThat(result1.map(Result::value)).hasValue(234),
                () -> assertThat(result1.map(Result::startIndex)).hasValue(0),
                () -> assertThat(result1.map(Result::endIndex)).hasValue(3)
        );

        final var result2 = lexer.tryParse("453.023");

        assertAll(
                () -> assertThat(result2).isNotNull(),
                () -> assertThat(result2.map(Result::value)).hasValue(453.023),
                () -> assertThat(result2.map(Result::startIndex)).hasValue(0),
                () -> assertThat(result2.map(Result::endIndex)).hasValue(7)
        );
    }

    @Test
    void transform_the_parsing_result() {
        var lexer =  new LexerBuilder<Integer>()
                .add(new OneCaptureGroupPattern(Pattern.compile("([0-9]+)")), r -> __ -> r.map(Integer::parseInt))
                .build();
        lexer = lexer.map(x -> x * 2);

        final var result = lexer.tryParse("234");

        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.map(Result::value)).hasValue(468),
                () -> assertThat(result.map(Result::startIndex)).hasValue(0),
                () -> assertThat(result.map(Result::endIndex)).hasValue(3)
        );
    }
}
