package lexer;

public class Token {
	
	public enum Type{
		Number,
		HexNumber,
		BinaryNumber,
		Star,
		Plus,
		Minus,
		Slash,
		Percent,
		LParent,
		RParent,
		LCParent,
		RCParent,
		ExclamationMark,
		ID,
		Equal,
		Semicolon,
		Comma,
		String,
		ECHO,
		IF,
		ELSE,
		LT,
		GT,
		WHILE,
		TRUE,
		FALSE,
		USE,AS,
		DEF,
		NULL,
		AMPAMP,
		AMP,
		Vbar,
		VbarVbar,
		End
	}
	
	private Type type;
	private String text;
	
	public Token(Type type, String text) {
		this.type = type;
		this.text = text;
	}
	public Token(Type type) {
		this.type = type;
	}
	
	
	
	
	public Type getType() {
		return type;
	}
	public String getText() {
		return text;
	}
	@Override
	public String toString() {
		return type.name() + (text != null? "  "+text:"");
	}

}
