package main;

public class Vector3i 
{
	public int x , y , z;
	
	public Vector3i(int x , int y , int z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3i(Vector3i v) 
	{
		this(v.x, v.y , v.z);
	}
	public void set(Vector3i v)
	{
		x = v.x;
		y = v.y;
		z = v.z;
	}
	public Vector3i add(Vector3i v)
	{
		return new Vector3i(x + v.x, y + v.y , z + v.z);
	}
	public Vector3i multiplay(float m)
	{
		return new Vector3i((int)(x * m), (int)(y * m) , (int)(z * m));
	}
	public Vector3i minus(Vector3i v)
	{
		return new Vector3i(x - v.x , y - v.y , z - v.z);
	}
	@Override
	public String toString() 
	{
		return "x:"+x+";y:"+y+";z:"+z;
	}
	public static void swap(Vector3i v1 , Vector3i v2)
	{
		int t = v1.x;
		v1.x = v2.x;
		v2.x = t;
		
		t = v1.y;
		v1.y = v2.y;
		v2.y = t;
		
		t = v1.z;
		v1.z = v2.z;
		v2.z = t;
	}
	
}
