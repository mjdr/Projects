package p3d;

public class Vector3 
{
	public float x,y,z;

	public Vector3(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() 
	{
		return String.format("[x: %f;y: %f;z: %f]", x , y , z);
	}
	
}
