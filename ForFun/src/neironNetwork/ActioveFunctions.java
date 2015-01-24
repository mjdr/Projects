package neironNetwork;

public class ActioveFunctions 
{
	private ActioveFunctions(){}
	
	
	public static final ActiveFunction sign = new ActiveFunction() 
	{
		
		@Override
		public double f(double x) 
		{
			return Math.signum(x);
		}
	}; 
	
	public static final ActiveFunction atanh = new ActiveFunction() 
	{
		@Override
		public double f(double x) 
		{
			return 0.5*Math.log( (x + 1.0) / (x - 1.0));
		}
	};
	
}
