package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RuleShould {
    private static final OneGroupPattern NULL_ONE_GROUP_PATTERN = null;
    private static final Function<Result<String>, Lexer<?>> FLAT_MAPPER = __ -> Lexer.empty();
    private static final Function<Result<String>, Lexer<?>> NULL_FLAT_MAPPER = null;
    public static final OneGroupPattern ONE_GROUP_PATTERN = new OneGroupPattern(Pattern.compile("(.*)"));

    @Test
    void be_created_with_a_non_null_one_group_pattern() {
        assertThatThrownBy(() -> new Rule(NULL_ONE_GROUP_PATTERN, FLAT_MAPPER))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void be_created_by_a_non_null_flet_mapper() {
        assertThatThrownBy(() -> new Rule(ONE_GROUP_PATTERN, NULL_FLAT_MAPPER))
                .isInstanceOf(NullPointerException.class);
    }
}
