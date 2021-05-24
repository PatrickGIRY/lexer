package tools.lexer;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RulesBuilderShould {

    public static final OneGroupPattern ONE_GROUP_PATTERN = new OneGroupPattern(Pattern.compile("(.*)"));

    @Test
    void add_a_new_rule() {
        final var rulesBuilder = new RulesBuilder<>();
        final var rule = new Rule<>(ONE_GROUP_PATTERN, __ -> Lexer.empty());

        final var newRulesBuilder = rulesBuilder.add(rule);

        assertAll(
                () -> assertThat(newRulesBuilder).isNotNull(),
                () -> assertThat(newRulesBuilder.rules()).contains(rule)
        );
    }

    @Test @SuppressWarnings("unused")
    void be_typed() {
        final RulesBuilder<String> stringRulesBuilder = new RulesBuilder<>();
        final RulesBuilder<Integer> integerRulesBuilder = new RulesBuilder<>();
    }
}
