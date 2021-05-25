package tools.lexer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResultShould {

    private static final String VALUE = "foo";
    private static final int START_INDEX = 4;
    private static final int END_INDEX = 7;

    @Test
    void be_created_with_a_value_the_start_index_and_the_end_index_that_matches() {
        final var result = new Result<String>(VALUE, START_INDEX, END_INDEX);

        assertAll(
                () -> assertThat(result.value()).isEqualTo(VALUE),
                () -> assertThat(result.startIndex()).isEqualTo(START_INDEX),
                () -> assertThat(result.endIndex()).isEqualTo(END_INDEX)
        );
    }
}