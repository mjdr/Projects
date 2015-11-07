package mathComplier;

public class Token 
{
	String value;
	boolean isNumber;
	boolean isVariable;

	public Token(String value) 
	{
		this.value = value;
		isNumber = value.matches("\\d+(\\.\\d+)?([eE]\\d+)?");
	}
	public Token createVariable(String name)
	{
		Token token = new Token(value);
		token.isVariable = true;
		return token;
	}
	
}
