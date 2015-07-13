package main;

public interface BasicShader 
{
	public Vector3i vertexShader(Vector3f vertex);
	public int fragmentShader(Vector3f point , Vector3f normal);
}
