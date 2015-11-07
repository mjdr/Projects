package parse;

public class ParseException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public ParseException(String info) {
		super(info);
	}
	
}
