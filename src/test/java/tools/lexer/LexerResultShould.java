package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LexerResultShould {

    public static final Function<String, Object> NULL_MAPPER = null;

    @Test
    void apply_mapper_result_value() {
        final var text = "1234";
        final var stringResult = new Lexer.Result<>(text, 0, 4);

        final var integerResult = stringResult.map(Integer::valueOf);

        assertAll(
                () -> assertThat(integerResult).isNotNull(),
                () -> assertThat(integerResult.value()).isEqualTo(1234),
                () -> assertThat(integerResult.startIndex()).isEqualTo(0),
                () -> assertThat(integerResult.endIndex()).isEqualTo(text.length())
        );
    }

    @Test
    void can_accept_a_mapper_with_super_type_as_input() {
        final var integerResult = new Lexer.Result<>(4567, 0, 4);

        final var stringResult = integerResult.map((Function<Object, String>) Object::toString);

        final var text = "4567";
        assertAll(
                () -> assertThat(stringResult).isNotNull(),
                () -> assertThat(stringResult.value()).isEqualTo(text),
                () -> assertThat(stringResult.startIndex()).isEqualTo(0),
                () -> assertThat(stringResult.endIndex()).isEqualTo(text.length())
        );
    }

    @Test
    void can_accept_a_mapper_with_extended_type_as_output() {
        final var text = "4577";
        final Lexer.Result<String> stringResult = new Lexer.Result<>(text, 0, 4);

        final Lexer.Result<Object> objectResult = stringResult.map((Function<String, Integer>) Integer::valueOf);

        assertAll(
                () -> assertThat(objectResult).isNotNull(),
                () -> assertThat(objectResult.value()).isEqualTo(4577),
                () -> assertThat(objectResult.startIndex()).isEqualTo(0),
                () -> assertThat(objectResult.endIndex()).isEqualTo(text.length())
        );
    }

    @Test
    void not_accept_a_null_mapper() {
        final var text = "1234";
        final var stringResult = new Lexer.Result<>(text, 0, 4);

        assertThatThrownBy(() -> stringResult.map(NULL_MAPPER)).isInstanceOf(NullPointerException.class);
    }
}
