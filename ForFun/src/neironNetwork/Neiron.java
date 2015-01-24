package neironNetwork;

public abstract class Neiron 
{
	protected int N;
	protected DataSet weight;
	protected DataSet input;
	protected ActiveFunction actFunc;
	protected double output;
	
	public Neiron(int N , ActiveFunction func) 
	{
		this.N = N;
		weight = new DataSet(new double[N]);
		actFunc = func;
	}
	public void setInput(DataSet input)
	{
		if(input.getN() != N)
			throw new IllegalArgumentException("Input.n != N");
		this.input = input.cpy();
	}
	
	public abstract void calculate();
	
	public double getOutput(){ return output; }
	public void lern(double error)
	{
		for(int i = 0;i < N;i++)
		{
			
		}
	}
}
