
public class Lab7 
{

	public static double f(double x , double y)
	{
		return y - Math.sin(y) - x - x * x * x;
	}
	public static double dfx(double x ,double y , double eps)
	{
		return (f(x + eps , y) - f(x , y)) / eps;
	}
	public static double dfy(double x , double y , double eps)
	{
		return (f(x , y + eps) - f(x , y)) / eps;
	}
	public static double[] grad(double x , double y , double eps)
	{
		return new double[]{1 , dfy(x , y , eps)};
	}
	public static double[] gr(double x , double y , double eps)
	{
		double[] coords = new double[]{x , y};
		double[] grad;
		double z;
		
		do
		{
			grad = grad(coords[0] , coords[1] , eps/10);
			z = f(coords[0],coords[1]) * 10E-4;
			coords[0] -= z / grad[0];
			coords[1] -= z / grad[1];
			System.out.println("X: "+ coords[0]);
			System.out.println("Y: "+ coords[1]);
			
			System.out.println(f(coords[0] , coords[1]));
		}while(Math.abs(f(coords[0] , coords[1])) > eps);
		return coords;
	}
	
	public static void main(String[] args) 
	{
		gr(-1,1,1E-6);
	}

}
