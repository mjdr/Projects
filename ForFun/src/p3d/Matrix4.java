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
		
		float sum;
		for(int x = 0;x < 4;x++)
			for(int y = 0;y < 4;y++)
			{
				sum = 0;
				for(int k = 0;k < 4;k++)
					sum += data[x][k] * m.data[k][y];
				mtx.data[x][y] = sum;
			}
		return mtx;
	}

	public Vector4 mult(Vector4 v)
	{
		
		Vector4 vec = new Vector4();

		vec.x = v.x * data[0][0] + v.y * data[1][0] + v.z * data[2][0] + v.w * data[3][0];
		vec.y = v.x * data[0][1] + v.y * data[1][1] + v.z * data[2][1] + v.w * data[3][1];
		vec.z = v.x * data[0][2] + v.y * data[1][2] + v.z * data[2][2] + v.w * data[3][2];
		vec.w = v.x * data[0][3] + v.y * data[1][3] + v.z * data[2][3] + v.w * data[3][3];
		
		return vec;
	}
	
	@Override
	public String toString() 
	{
		String res= "";
		for(int y = 0;y < 4;y++)
		{
			for(int x = 0;x < 4;x++)
			{
				res += " " + data[x][y];
			}
			res += "\n";
		}
		return res;
	}
}