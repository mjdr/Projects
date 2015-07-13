package main;

public class Vector3f 
{
	public float x , y , z;
	
	
	public Vector3f(float x , float y , float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3f(Vector3i v) 
	{
		this(v.x, v.y, v.z);
	}
	public static Vector3f linerInterp(Vector3f v0 , Vector3f v1 , float k)
	{
		return new Vector3f
				(
					v0.x * (1 - k) + v1.x * k,
					v0.y * (1 - k) + v1.y * k,
					v0.z * (1 - k) + v1.z * k
				);
	}
	public static Vector3f linerInterp(Vector3i v0 , Vector3i v1 , float k)
	{
		return new Vector3f
				(
					v0.x + (v1.x - v0.x) * k,
					v0.y + (v1.y - v0.y) * k,
					v0.z + (v1.z - v0.z) * k
				);
	}
	public static Vector3i linerInterpInt(Vector3i v0 , Vector3i v1 , float k)
	{
		return new Vector3i
				(
					(int)(v0.x + (v1.x - v0.x) * k),
					(int)(v0.y + (v1.y - v0.y) * k),
					(int)(v0.z + (v1.z - v0.z) * k)
				);
	}
	public static int[] linerInterpIntArr(Vector3i v0 , Vector3i v1 , float k)
	{
		return new int[]
				{
					(int)(v0.x + (v1.x - v0.x) * k),
					(int)(v0.y + (v1.y - v0.y) * k),
					(int)(v0.z + (v1.z - v0.z) * k)
				};
	}
	public void set(Vector3f v)
	{
		x = v.x;
		y = v.y;
		z = v.z;
	}
	public Vector2f toVec2f(String key)
	{
		return new Vector2f(getComponent(key.charAt(0)),getComponent(key.charAt(1)));
	}
	public Vector3i toVec3i(String key)
	{
		return new Vector3i(
				(int)getComponent(key.charAt(0)),
				(int)getComponent(key.charAt(1)),
				(int)getComponent(key.charAt(2)));
	}
	public Vector3i toVec3i()
	{
		return new Vector3i
				(
					(int)x,
					(int)y,
					(int)z
				);
	}
	
	public Vector3f add(Vector3f v)
	{
		return new Vector3f(x + v.x, y + v.y, z + v.z);
	}
	public Vector3f minus(Vector3f v)
	{
		return new Vector3f(x - v.x, y - v.y, z - v.z);
	}
	public Vector3f devideComponents(Vector3f v)
	{
		return new Vector3f(x/v.x , y/v.y , z/v.z);
	}
	public float getComponent(char c)
	{
		char c1 = Character.toLowerCase(c);
		
		switch (c1) 
		{
			case 'x': return x;
			case 'y': return y;
			case 'z': return z;
		}
		throw new IllegalArgumentException("vector had't component \'" + c +"\'");
		
	}
	
	public float abs()
	{
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	public Vector3f multiplay(float f)
	{
		return new Vector3f(x * f, y * f, z * f);
	}
	public Vector3f multiplay(Vector3f v)
	{
		return new Vector3f(
				
				y*v.z-z*v.y,
				z*v.x-x*v.z,
				x*v.y-y*v.x
				);
	}
	public Vector3f normalize()
	{
		return multiplay(1F/abs());
	}
	public Vector3f normalizeThis()
	{
		float f = 1F/abs();
		x *= f;
		y *= f;
		z *= f;
		return this;
	}
	public float dotProduct(Vector3f v)
	{
		return x * v.x + y * v.y + z * v.z;
	}
	@Override
	public String toString() 
	{
		return "x:"+x+";y:"+y+";z:"+z;
	}
}
