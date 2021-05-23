package tools.lexer;

import static java.util.Objects.requireNonNull;

public record Rule(OneGroupPattern oneGroupPattern) {
    public Rule {
        requireNonNull(oneGroupPattern);
    }
}
