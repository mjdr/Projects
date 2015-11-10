package lexer;

public interface LexerListener {
	Token parse(String str);
}
