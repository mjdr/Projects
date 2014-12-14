
public class Lab4 
{
	
	public static float f(float x)
	{
		return (float) (x * x - Math.log(0.5 + x) - 3 * x);
	}
	public static float df(float x , float eps)
	{
		return (f(x + eps) - f(x)) / eps;
	}
	public static float bSearch(float x , float sX , float endX , float eps)
	{
		float t;

		System.out.println("Binary search");
		do
		{
			t = (endX - sX) / 2 + sX;
			System.out.println("L = " + (endX - sX));
			if(Math.signum(f(t)) == Math.signum(f(sX))) sX = t;
			else if(Math.signum(f(t)) == Math.signum(f(endX))) endX = t;
		}while(Math.abs(f(t) - x) > eps);
		return t;
	}	
	public static float hordSearch(float x , float sX , float endX , float eps)
	{
		float t;

		System.out.println("hord");
		do
		{
			t = sX - f(sX)* (endX - sX) / (f(endX) - f(sX));
			System.out.println("L = " + (endX - sX));
			if(Math.signum(f(t)) == Math.signum(f(sX))) sX = t;
			else if(Math.signum(f(t)) == Math.signum(f(endX))) endX = t;
		}while(Math.abs(f(t) - x) > eps);
		return t;
	}
	public static float searchNewtonRawsa(float x , float eps)
	{
		System.out.println("NewtonRawsa");
		int k = 0;
		float err;
		do
		{
			err = Math.abs(x - 3.40028F);
			k++;
			System.out.println("k:  " + k+";		X = " + x + "	err: " + err);
			x -= f(x) / df(x , eps);
		}while(Math.abs(f(x)) > eps);
		return x;
	}
	public static float searchSecant(float x1 , float x2 , float eps)
	{
		System.out.println("searchSecant");
		float t = x1 - f(x1) * (x2 - x1) / (f(x2) - f(x1));
		float err;
		int k = 0;
		do
		{
			err = Math.abs(x1 - 3.40028F);
			k++;
			System.out.println("k:  " + k+";		X = " + x1 + "	err: " + err);
			x2 = x1;
			x1 = t;
			t = x1 - f(x1) * (x2 - x1) / (f(x2) - f(x1));
			
		}while(Math.abs(t - x2) > eps);
		return x1;
	}
	public static float simpleIterations1(float x0 , float eps)
	{
		System.out.println("Simple Iterations (ex = |x - xp|)");
		
		float x = x0;
		float xp;//prev X
		float ex = Float.MAX_VALUE;
		float prev_ex = Float.MAX_VALUE;
		boolean fail = false;
		int i = 0;
		
		do
		{
			if(ex <= prev_ex)
				prev_ex = ex;
			else
			{
				fail = true;
				break;
			}
			if(i % 500 == 0)
				System.out.println("X = " + x);
			xp = x;
			x = f(x);
			ex = Math.abs(x - xp);
			i++;
			
		}while(ex > eps);
		
		if(fail) throw new RuntimeException("Fail!");
		return x;
	}
	public static float simpleIterations2(float x0 , float eps)
	{
		System.out.println("Simple Iterations (ex = |f(x)|)");
		
		float x = x0;
		float ex = Float.MAX_VALUE;
		float prev_ex = Float.MAX_VALUE;
		boolean fail = false;
		int i = 0;
		do
		{
			if(ex <= prev_ex)
				prev_ex = ex;
			else
			{
				fail = true;
				break;
			}
			if(i % 50000 == 0)
				System.out.println("X = " + x);
			x = f(x);
			ex = Math.abs(f(x));
			i++;
			
		}while(ex > eps);
		
		if(fail) throw new RuntimeException("Fail!");
		return x;
	}
	public static void main(String[] args) 
	{
		//System.out.println("	"+bSearch(0,-0.2F, 0.5F, 1E-9F));
		//System.out.println("	"+hordSearch(0,-0.2F, 0.5F, 1E-9F));
		//System.out.println("	"+searchSecant(5F, 6F, 1E-6F));
		//System.out.println("	"+searchNewtonRawsa(5, 1E-6F));
		try{
		System.out.println("	"+simpleIterations1(4, 1E-6F));
		}catch(Exception e){e.printStackTrace();}
		try{
		System.out.println("	"+simpleIterations2(4, 1E-3F));
		}catch(Exception e){e.printStackTrace();}
	}

}
