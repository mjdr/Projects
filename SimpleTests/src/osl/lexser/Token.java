package osl.lexser;

public class Token 
{
	int index;
	String value;
	
	public Token(int index , String value) 
	{
		this.index = index;
		this.value = value;
	}
	public Token(String value) 
	{
		this(0,value);
	}
	
	@Override
	public String toString() 
	{
		return value;
	}
	
}
