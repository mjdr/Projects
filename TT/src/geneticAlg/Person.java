package geneticAlg;

import java.util.Random;

public class Person implements Comparable<Person>
{
	private static Random rand = new Random();
	private static int N = 4;
	
	
	public static void compare(function func , Person p) 
	{
		float error = 0;
		float dx = 0.01F;
		for(float x = -5;x < 5;x+=dx)
			error += Math.abs((func.f(x)) - p.calculate(x)) * dx;
		p.score = 1F/(1 + error);
	}
	public static Person mutation(Person p) 
	{
		float[] newData = new float[N];
			
		System.arraycopy(p.k, 0, newData, 0, N);
		int h = rand.nextInt(N/2);
		for(int j = 0;j < h;j++)
		{
			int i = rand.nextInt(N);
			
			p.k[i] += rand.nextBoolean() ? -1:1 * Math.pow(rand.nextBoolean() ? -2:2, (rand.nextFloat() * 6) - 3);
		}
		return new Person(newData);
		
	}
	
	public static Person newPerson(Person p1 , Person p2)
	{
		int k = rand.nextInt(N);
		float[] newData = new float[N];
		System.arraycopy(p1.k, 0, newData, 0, k);
		System.arraycopy(p2.k, k, newData, k, p1.k.length - k);
		return new Person(newData);
		
	}
	
	public static Person getRandomPerson()
	{

		float[] newData = new float[N];
		for(int i = 0;i < N;i++)
			newData[i] = 0;
		return new Person(newData);
		
	}
	
	float[] k;
	float score;
	public Person(float[] _k) 
	{
		k = _k;
	}
	@Override
	public int compareTo(Person o) 
	{
		return (int)Math.signum(score - o.score);
	}
	public float calculate(float x)
	{
		float accX = 1;
		float y = 0;
		for(int i = 0;i < N;i++)
		{
			y += accX * k[i];
			accX *= x;
		}
		return y;
	}
	
	
}