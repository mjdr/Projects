package neironNetwork;

public class DataSet 
{
	protected int N;
	protected double[] values;
	public DataSet(double... values) 
	{
		double[] cpy = new double[N];
		System.arraycopy(values, 0, cpy, 0, N);
		this.values = cpy;
		N = values.length;
	}
	public int getN()
	{
		return N;
	}
	public double[] values() 
	{
		return values;
	}
	public DataSet cpy() 
	{
		double[] cpy = new double[N];
		System.arraycopy(values, 0, cpy, 0, N);
		return new DataSet(cpy);
	}
	
	
	
}
