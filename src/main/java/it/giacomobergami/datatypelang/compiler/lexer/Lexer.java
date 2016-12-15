package it.giacomobergami.datatypelang.compiler.lexer;

import it.giacomobergami.datatypelang.compiler.parser.grammar.input.OnInput;
import it.giacomobergami.datatypelang.compiler.parser.grammar.terms.Varepsilon;
import it.giacomobergami.datatypelang.compiler.parser.grammar.stack.Token;
import it.giacomobergami.datatypelang.utils.Streams;
import it.giacomobergami.datatypelang.utils.regex.Match;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer {

    private final Map<String,String> enumClazz;
    public Lexer(Map<String,String> eclazz) {
        this.enumClazz = eclazz;
    }

    public TerminalIterator lex(String input) {
        // The tokens to return
        ArrayList<Token> tokens = new ArrayList<>();

        Pattern tokenPatterns = Pattern.compile(Streams.toStream(enumClazz.entrySet()).map(
                x-> String.format("(?<%s>%s)", x.getKey(), x.getValue())
        ).collect(Collectors.joining("|")));

        return new TerminalIterator(Streams.toStream(Match.patterns(tokenPatterns).with(input)).map(matcher -> {
            for (String tokenType : enumClazz.keySet()) {
                String match = matcher.fromGroupsGet(tokenType);
                if (match!=null) {
                    return new Token(tokenType,match);
                }
            }
            return new Varepsilon();
        }).filter(OnInput::nonEmpty));
    }


}
