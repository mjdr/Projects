package main;

public class Vector2f 
{
	public float x , y;
	
	public Vector2f(float x , float y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f add(Vector2f v)
	{
		return new Vector2f(x + v.x, y + v.y);
	}
	public Vector2f multiplay(float m)
	{
		return new Vector2f((int)(x * m), (int)(y * m));
	}
	@Override
	public String toString() 
	{
		return "x:"+x+";y:"+y;
	}
	public Vector2i toVec2i(){ return new Vector2i((int)x, (int)y); }
}
