package parse;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.*;

public class Lexer {
	
	private static final String OPERATORS = "+-*/(),^";
	
	private String source;
	private int index = 0;

	public Lexer(String source) {
		this.source = source;
	}
	
	public List<Token> parse() {
		
		List<Token>tokens = new ArrayList<Token>();
		
		while(index < source.length())
		{
			while(peek()==' ' || peek()=='\n')
				read();
			if(index < source.length())
				tokens.add(readToken());
		}
		return tokens;
	}
	
	
	private Token readToken(){
		
		if(isDigit(peek())){
			return readNumber();
		}
		else if(isLetter(peek())){
			return readID();
		}
		else if(OPERATORS.indexOf(peek())!= -1){
			return readOperator();
		}
		else{
			throw new ParseException("Invalid character " + peek());
		}
	}
	
	
	
	
	private Token readID() {
		
		if(!isLetter(peek()))
			throw new ParseException("Invalid ID index = " + index);
		
		StringBuffer sb = new StringBuffer();
		
		while(isLetterOrDigit(peek()))
			sb.append(read());
		
		return new Token(Token.Type.ID, sb.toString());
		
	}

	private Token readOperator() 
	{
		if(OPERATORS.indexOf(peek()) == -1)
			throw new ParseException("Invalid operator " + peek());
		
		switch (read()) {
			case '+': return new Token(Token.Type.PLUS);
			case '-': return new Token(Token.Type.MINUS);
			case '*': return new Token(Token.Type.STAR);
			case '/': return new Token(Token.Type.SLACH);
			case '(': return new Token(Token.Type.LPARENT);
			case ')': return new Token(Token.Type.RPARENT);
			case ',': return new Token(Token.Type.COMMA);
			case '^': return new Token(Token.Type.POW);

		}
		
		return null;
	}

	private Token readNumber() {
		
		if(!isDigit(peek()))
			throw new ParseException("Invalid number index = " + index);
		
		StringBuffer sb = new StringBuffer();
		boolean dot = false;
		
		while(isDigit(peek()) || peek() == '.'){
			if(peek() == '.')
				if(dot) break;
				else {
					dot = true;
					sb.append(read());
				}
			else
				sb.append(read());
		}
		
		return new Token(Token.Type.NUMBER, sb.toString());
		
	}

	public char peek(int idx)
	{
		if(idx + index < source.length())
			return source.charAt(idx + index);
		return '\0';
	}
	
	public char peek(){
		return peek(0);
	}
	
	public char read(){
		if(index < source.length())
			return source.charAt(index++);
		throw new ParseException("Unexpected EOF");
		
	}
}
