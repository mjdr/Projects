package osl.lexser;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer 
{
	String regExp = "(([a-zA-Z]([a-zA-Z0-9])*)|(\\\"[^\\\"]*\\\")|(\\d+(\\.\\d+)?)|((&&)|(\\|\\|)|(\\=\\=))|[\\(\\)<>\\{\\}\\[\\]\\,\\.;\\=\\*\\+\\/\\-])";
	
	
	public Vector<Token> parse(String source)
	{
		
		Pattern p = Pattern.compile(regExp);
		Matcher matcher = p.matcher(source);
		Vector<Token>tokens = new Vector<Token>();
		while(matcher.find())
			tokens.add(new Token(matcher.start(), matcher.group(0)));
		return tokens;
		
	}
	
	
}
