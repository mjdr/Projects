package p3d;

public class Vector4 
{
	public float x,y,z,w;

	public Vector4(float x, float y, float z, float w) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	
	public Vector4(Vector3 v , float w) 
	{
		x = v.x;
		y = v.y;
		z = v.z;
		this.w = w;
	}
	public Vector4() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public String toString() 
	{
		return String.format("[x: %f;y: %f;z: %f;w: wf;]", x , y , z , w);
	}
	
}
