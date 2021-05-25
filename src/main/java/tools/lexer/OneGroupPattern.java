package tools.lexer;

import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public record OneGroupPattern(Pattern pattern) {
    public OneGroupPattern {
        requireNonNull(pattern);
        requireOneCapturedGroup(pattern);
    }

    private static void requireOneCapturedGroup(Pattern pattern) {
        if (pattern.matcher("").groupCount() != 1) {
            throw new IllegalArgumentException(pattern + " has not one captured group");
        }
    }

    public String regex() {
        return pattern.pattern();
    }
}
