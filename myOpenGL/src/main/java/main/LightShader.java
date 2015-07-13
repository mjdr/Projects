package main;

public class LightShader implements BasicShader
{

	Vector3f lightPoint = new Vector3f(100, 300, -20);
	
	public Vector3i vertexShader(Vector3f vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int fragmentShader(Vector3f point , Vector3f normal) 
	{
		Vector3f d = (lightPoint.minus(point)).normalize();
		Vector3f norm = normal.normalize();
		
		int factor = (int)(d.dotProduct(norm) * 255);
		factor = Math.abs(factor);
		
		return factor | (factor << 8) | (factor << 16);
	}

}
