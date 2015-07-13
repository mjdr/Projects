package main;

public class Vector2i 
{
	public int x , y;
	
	public Vector2i(int x , int y) 
	{
		this.x = x;
		this.y = y;
	}
	public Vector2i(Vector2i v) 
	{
		this(v.x, v.y);
	}
	public void set(Vector2i v)
	{
		x = v.x;
		y = v.y;
	}
	public Vector2i add(Vector2i v)
	{
		return new Vector2i(x + v.x, y + v.y);
	}
	public Vector2i multiplay(float m)
	{
		return new Vector2i((int)(x * m), (int)(y * m));
	}
	public Vector2i minus(Vector2i v)
	{
		return new Vector2i(x - v.x , y - v.y);
	}
	@Override
	public String toString() 
	{
		return "x:"+x+";y:"+y;
	}
	
}
