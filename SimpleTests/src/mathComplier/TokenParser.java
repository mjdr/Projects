package mathComplier;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParser 
{
	Pattern pattern;
	
	public TokenParser(String regExp) 
	{
		pattern = Pattern.compile(regExp);
	}
	
	
	public List<Token>parse(String string)
	{
		Matcher matcher = pattern.matcher(string);
		List<Token>tokens = new ArrayList<>();
		while(matcher.find())
			tokens.add(new Token(matcher.group()));
		return tokens;
		
	}
	
}
