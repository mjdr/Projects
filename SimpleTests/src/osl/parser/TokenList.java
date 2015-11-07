package osl.parser;

import java.util.List;

import osl.lexser.Token;

public class TokenList 
{
	
	private List<Token> tokens;
	private int index;
	
	public TokenList(List<Token> tokens) 
	{
		this.tokens = tokens;
		index = 0;
	}
	
	public Token peekToken(int ind)
	{
		if(index + ind >= tokens.size())
			throw new RuntimeException("Unexpected end of file!");
		return tokens.get(index + ind);
	}
	public Token peekToken()
	{
		return peekToken(0);
	}
	public Token readToken()
	{
		if(index >= tokens.size())
			throw new RuntimeException("Unexpected end of file!");
		return tokens.get(index++);
	}
	
}
