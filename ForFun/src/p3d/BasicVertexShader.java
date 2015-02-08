package p3d;

import java.util.Map;

public class BasicVertexShader extends VertexShader
{

	@Override
	public Map<String, Object> run(Map<String, Object> params, Vector3 output) 
	{
		
		Vector3 vertex = (Vector3)params.get("vertex");
		Matrix4 projection = (Matrix4)params.get("projectionMatrix");
		
		
		Vector4 r = projection.mult(new Vector4(vertex , 1));
		output.x = r.x;
		output.y = r.y;
		output.z = r.z;
		return null;
	}

}
