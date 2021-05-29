package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ResultShould {

    private static final String VALUE = "foo";
    private static final int START_INDEX = 4;
    private static final int END_INDEX = 7;
    private static final Object NULL_VALUE = null;
    private static final Function<Object, Object> NULL_MAPPER = null;

    @Test
    void be_created_with_a_value_the_start_index_and_the_end_index_that_matches() {
        final var result = new Result<>(VALUE, START_INDEX, END_INDEX);

        assertAll(
                () -> assertThat(result.value()).isEqualTo(VALUE),
                () -> assertThat(result.startIndex()).isEqualTo(START_INDEX),
                () -> assertThat(result.endIndex()).isEqualTo(END_INDEX)
        );
    }

    @Test
    void be_created_with_a_non_null_value() {
        assertThatThrownBy(() -> new Result<>(NULL_VALUE, START_INDEX, END_INDEX))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void be_created_with_a_posititve_or_zero_start_index() {
        assertThatThrownBy(() -> new Result<>(VALUE, -1, END_INDEX))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start index -1 must be positive or zero");
    }

    @Test
    void be_created_with_an_end_index_is_greater_or_equal_to_start_index() {
        assertThatThrownBy(() -> new Result<>(VALUE, START_INDEX, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End index 0 must be greater or equal than start index " + START_INDEX);
    }

    @Test
    void map_the_result_value() {
        final var integerResult = new Result<>(123, START_INDEX, END_INDEX);

        final Optional<Result<Object>> result = integerResult.map((Function<Object, String>) Object::toString);

        assertAll(
                () -> assertThat(result.map(Result::value)).hasValue("123"),
                () -> assertThat(result.map(Result::startIndex)).hasValue(START_INDEX),
                () -> assertThat(result.map(Result::endIndex)).hasValue(END_INDEX)
        );
    }

    @Test
    void not_map_with_a_null_mapper() {
        final var result = new Result<>(123, START_INDEX, END_INDEX);

        assertThatThrownBy(() -> result.map(NULL_MAPPER)).isInstanceOf(NullPointerException.class);
    }
}