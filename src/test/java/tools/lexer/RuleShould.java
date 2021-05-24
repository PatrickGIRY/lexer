package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RuleShould {
    private static final OneGroupPattern NULL_ONE_GROUP_PATTERN = null;
    public static final OneGroupPattern ONE_GROUP_PATTERN = new OneGroupPattern(Pattern.compile("(.*)"));
    public static final Function<Result<String>, Lexer<Object>> NULL_FLAT_MAPPER = null;

    @Test
    void be_created_with_a_non_null_one_group_pattern() {
        assertThatThrownBy(() -> new Rule<>(NULL_ONE_GROUP_PATTERN, __ -> Lexer.empty()))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void be_created_by_a_non_null_flat_mapper() {
        assertThatThrownBy(() -> new Rule<>(ONE_GROUP_PATTERN, NULL_FLAT_MAPPER))
                .isInstanceOf(NullPointerException.class);
    }

    @Test @SuppressWarnings("unused")
    void be_typed() {
        Rule<String> stringRule = new Rule<>(ONE_GROUP_PATTERN, __ -> Lexer.empty());
        Rule<Integer> integerRule = new Rule<>(ONE_GROUP_PATTERN, __ -> Lexer.empty());
    }
}
