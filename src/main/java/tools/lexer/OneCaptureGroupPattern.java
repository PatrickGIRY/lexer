package tools.lexer;

import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public record OneCaptureGroupPattern(Pattern pattern) {
    public OneCaptureGroupPattern {
        requireNonNull(pattern);
        requireOneCaptureGroup(pattern);
    }

    private static void requireOneCaptureGroup(Pattern pattern) {
        if (pattern.matcher("").groupCount() != 1) {
            throw new IllegalArgumentException(pattern + " has not one captured group");
        }
    }

    public String regex() {
        return pattern.pattern();
    }
}
