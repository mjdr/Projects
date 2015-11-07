package mathComplier;

import java.util.List;
import java.util.Stack;

public class Main 
{
	public Main() 
	{
		TokenParser tokenParser = new TokenParser("(\\d+(\\.\\d+)?([eE]\\d+)?|[\\(\\)\\+\\-\\*\\/\\^])");
		
		
		List<Token>tokens = tokenParser.parse("23*7-33*11+22");
		
		
		
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
