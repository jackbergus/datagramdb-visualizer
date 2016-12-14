package it.giacomobergami.datatypelang.compiler.parser.grammar.stack;

import it.giacomobergami.datatypelang.compiler.parser.grammar.TableColumnEntry;
import it.giacomobergami.datatypelang.compiler.parser.grammar.input.OnInput;
import it.giacomobergami.datatypelang.compiler.parser.grammar.terms.GrammarTerm;
import it.giacomobergami.datatypelang.compiler.parser.grammar.terms.Terminal;
import it.giacomobergami.datatypelang.utils.funcs.Opt;

/**
 * Created by vasistas on 11/12/16.
 */
public class Token<T extends Enum> implements OnStack<T>, OnInput<T> {

    public final T type;
    public String data;
    public final Terminal<T> term;

    public Token(T type, String data) {
        this.type = type;
        this.data = data;
        this.term = new Terminal<T>(type);
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", type.name(), data);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public TableColumnEntry<T> asTableColumnValue() {
        return term;
    }

    @Override
    public GrammarTerm<T> asGrammarTerm() {
        return term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token<?> token = (Token<?>) o;

        if (type != null ? !type.equals(token.type) : token.type != null) return false;
        if (data != null ? !data.equals(token.data) : token.data != null) return false;
        return term != null ? term.equals(token.term) : token.term == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (term != null ? term.hashCode() : 0);
        return result;
    }
}