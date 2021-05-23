package tools.lexer;

import java.util.regex.Pattern;

public record OneGroupPattern(Pattern pattern) {
    public OneGroupPattern {
        requireOneCapturedGroup(pattern);
    }

    private static void requireOneCapturedGroup(Pattern pattern) {
        if (pattern.matcher("").groupCount() != 1) {
            throw new IllegalArgumentException(pattern + " has not one captured group");
        }
    }
}
