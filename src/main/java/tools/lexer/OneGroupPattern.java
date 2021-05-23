package tools.lexer;

public record OneGroupPattern(java.util.regex.Pattern pattern) {
    public OneGroupPattern {
        if (pattern.matcher("").groupCount() != 1) {
            throw new IllegalArgumentException(pattern + " has not one captured group");
        }
    }
}
