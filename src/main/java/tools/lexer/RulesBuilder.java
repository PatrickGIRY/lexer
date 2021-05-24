package tools.lexer;

import java.util.ArrayList;
import java.util.List;

public class RulesBuilder {
    private List<Rule> rules;

    public RulesBuilder() {
        rules = new ArrayList<>();
    }

    public RulesBuilder add(Rule rule) {
        this.rules.add(rule);
        return this;
    }

    public List<Rule> rules() {
        return rules;
    }
}
