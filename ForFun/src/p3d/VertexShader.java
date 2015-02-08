package p3d;

import java.util.Map;

public abstract class VertexShader 
{
	public abstract Map<String, Object> run(Map<String, Object>params , Vector3 output);
}
