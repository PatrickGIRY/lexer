package tools.lexer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RuleShould {
    private static final OneGroupPattern NULL_ONE_GROUP_PATTERN = null;

    @Test
    void be_created_with_a_non_null_one_group_pattern() {
        assertThatThrownBy(() -> new Rule(NULL_ONE_GROUP_PATTERN));
    }
}
