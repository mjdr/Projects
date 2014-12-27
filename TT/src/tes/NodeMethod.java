package tes;
import math.Matrix;


public class NodeMethod 
{
	private int n;
	private Matrix Y,V,I;
	private boolean calcul;
	
	public NodeMethod(int rank) 
	{
		n = rank;
		Y = new Matrix(n , n);
		V = new Matrix(1 , n);
		I = new Matrix(1 , n);
		calcul = false;
	}
	
	public void addG(int id1 , int id2 ,float g)
	{
		if(id1 == id2)
			throw new IllegalArgumentException("ID1 == ID2");

		addToY(id1, id1, g);
		addToY(id2, id2, g);
		addToY(id1, id2, -g);
		addToY(id2, id1, -g);
		calcul = false;
		
	}
	private void addToY(int id1 , int id2,float g)
	{
		if(id1 == 0 || id2 == 0)
			return;
		Y.set(id1 - 1, id2 - 1, Y.get(id1 - 1, id2 - 1) + g);
	}
	/*
	 * 
	 *     (2)-----(>>)-----(1)
	 * 
	 * */
	public void addI(int id1 , int id2 ,float i)
	
	{
		if(id1 != 0)
			I.set(0, id1 - 1, I.get(0, id1 - 1) - i);
		if(id2 != 0)
			I.set(0, id2 - 1, I.get(0, id2 - 1) + i);
		calcul = false;
	}
	
	
	
	/*
	 * 
	 *     (2)-----(->)-----(1)
	 * 
	 * */
	public void addU(int a , int b ,float u)
	{
		
			for(int f = 0;f < n;f++)
				Y.set(f, b - 1, Y.get(f, b - 1) + Y.get(f, a - 1));
			
			I.set(0 , b - 1, I.get(0, b - 1) + I.get(0, a - 1));
			

			for(int f = 0;f < n;f++)
				Y.set(b - 1, f, Y.get(b - 1, f) + Y.get(a - 1, f));
			
			for(int f = 0;f < n;f++)
				I.set(0, f, I.get(0, f) + Y.get(a - 1, f) * (-u));
		
		
			Matrix newY = new Matrix(n - 1, n - 1);
			Matrix newI = new Matrix(1 , n - 1);

			for(int i = 0;i < n;i++)
				for(int j = 0;j < n;j++)
				{
					if(i == a - 1 || j == a - 1)
						continue;
					if(i < a - 1 && j < a - 1)
						newY.set(i, j, Y.get(i, j));
					else
					{
						if(i < a - 1 && j > a - 1)
							newY.set(i, j - 1, Y.get(i, j));
						else if(i > a - 1 && j < a - 1)
							newY.set(i - 1, j, Y.get(i, j));
						else
							newY.set(i - 1, j - 1, Y.get(i, j));
							
					}
				}
			

			for(int i = 0;i < n;i++)
			{
				if(i == a)
					continue;
				if(i < a)
					newI.set(0, i, I.get(0, i));
				else
					newI.set(0, i - 1, I.get(0, i));
					
			}
			
			Y = newY;
			I = newI;
			
		calcul = false;
	}
	
	
	private void calculate()
	{
		V = Y.invert().multiplay(I);
	}
	public Matrix getY(){ return Y; }
	public Matrix getV()
	{
		if(!calcul)
			calculate();
		return V; 
	}
	public Matrix getI(){ return I; }
	
}
