package tools.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LexerBuilder<T> {

    private String regexes = "";
    private final List<Function<Result<String>, Lexer<T>>> flatMappers = new ArrayList<>();

    public LexerBuilder<T> add(OneGroupPattern oneGroupPattern, Function<Result<String>, Lexer<T>> fletMapper) {
        regexes = oneGroupPattern.pattern().pattern();
        this.flatMappers.add(fletMapper);
        return this;
    }

    public String regexes() {
        return regexes;
    }

    public List<Function<Result<String>, Lexer<T>>> flatMappers() {
        return flatMappers;
    }
}
