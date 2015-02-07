package p3d;

public class Matrix4 
{
	private float[][] data;
	public Matrix4() 
	{
		data = new float[4][4];
	}
	
	public float get(int i , int j)
	{
		return data[i][j];
	}
	public void set(int i , int  j , float value)
	{
		data[i][j] = value;
	}
	
	public Matrix4 mult(Matrix4 m)
	{
		Matrix4 mtx = new Matrix4();
		
		mtx.data[0][0] = 
				data[0][0] * m.data[0][0] +
				data[0][1] * m.data[1][0] +
				data[0][2] * m.data[2][0] +
				data[0][3] * m.data[3][0];
		mtx.data[1][0] = 
				data[0][0] * m.data[1][0] +
				data[0][1] * m.data[1][1] +
				data[0][2] * m.data[1][2] +
				data[0][3] * m.data[1][3];
		
		
		
		return mtx;
	}
}