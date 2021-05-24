package tools.lexer;

import java.util.ArrayList;
import java.util.List;

public class RulesBuilder<T> {
    private final List<Rule<T>> rules;

    public RulesBuilder() {
        rules = new ArrayList<>();
    }

    public RulesBuilder<T> add(Rule<T> rule) {
        this.rules.add(rule);
        return this;
    }

    public List<Rule<T>> rules() {
        return rules;
    }
}
