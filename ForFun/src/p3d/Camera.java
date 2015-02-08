package p3d;

public class Camera
{
	protected Vector3 position;
	protected Vector3 scale;
	
	protected Matrix4 positionMatrix;
	protected Matrix4 scaleMatrix;
	
	public Matrix4 projectionMatrix;
	
	
	public Camera() 
	{
		position = new Vector3();
		scale = new Vector3(1,1,1);
		
		
		positionMatrix = new Matrix4();
		scaleMatrix = new Matrix4();
		
		init();
	}
	
	private void init() 
	{
		positionMatrix.set(0, 0, 1);
		positionMatrix.set(1, 1, 1);
		positionMatrix.set(2, 2, 1);
		positionMatrix.set(3, 3, 1);
		
		scaleMatrix.set(3, 3, 1);
		
		update();
	}

	public void update()
	{

		positionMatrix.set(3, 0, position.x);
		positionMatrix.set(3, 1, position.y);
		positionMatrix.set(3, 2, position.z);

		scaleMatrix.set(0, 0, scale.x);
		scaleMatrix.set(1, 1, scale.y);
		scaleMatrix.set(2, 2, scale.z);
		projectionMatrix = scaleMatrix.mult(positionMatrix);
	}
}
