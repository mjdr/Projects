package parse;

public class Token {
	
	public enum Type {
		NUMBER,
		PLUS, MINUS,
		STAR, SLACH,
		LPARENT , RPARENT,
		POW ,
		COMMA , ID , END
		
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
		return type.name() + (text == null? "" : " -> "+text);
	}
	
}
