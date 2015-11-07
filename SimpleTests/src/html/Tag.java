package html;

public class Tag 
{
	String name;
	boolean closed;
	int start , end;
	
	public Tag(String name, boolean closed , int start,int end) 
	{
		this.name = name;
		this.closed = closed;
		this.start = start;
		this.end = end;
	}
	
	@Override
	public String toString() 
	{
		return String.format("%s  closed:%s", name,closed);
	}
	
}
