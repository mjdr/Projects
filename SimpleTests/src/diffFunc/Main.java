package diffFunc;

public class Main 
{
	
	
	public Main() 
	{
		
		double dx = 0.00001;
		double sum = 0;
		for(double x = 0;x < 2*Math.PI;x+=dx)
		{
			sum += f(x + dx/2)*dx;
		}
		System.out.println(sum);
		
	}
	
	public double f(double x)
	{
		return Math.sin(x)+0.5;
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
