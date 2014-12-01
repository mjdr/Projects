import java.util.Random;

public class Algoritm 
{
	
	Node[] nodes;
	Random rand;
	
	public Algoritm() 
	{
		rand = new Random();
		nodes = new Node[30];
		for(int i = 0;i < 30;i++)
			nodes[i] = new Node(new int[]
					{
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100),
						rand.nextInt(100)
					});
		for(int i = 0;i < 10000;i++)
		{

			//System.out.println('{');
			//for(Node n : nodes)
			//	System.out.println("	"+n.toString());
			//System.out.println('}');
			
			for(int j = 0;j < nodes.length;j++)
				if(nodes[j] != null)
					if(rand.nextInt(100) < 33)
						Node.mute(nodes[j]);
			
			for(int j = 0;j < 10;j++)
			{
				int i1 = rand.nextInt(20);
				int i2 = rand.nextInt(20);
				nodes[20 + j] = Node.newNode(nodes[i1], nodes[i2]);
			}
			
			for(int j = 0;j < nodes.length;j++)
				if(nodes[j] != null)
					Node.f(nodes[j]);
			
			boolean sw;
			do
			{
				sw = false;
				for(int j = 1;j < nodes.length;j++)
					if(nodes[j].num > nodes[j - 1].num)
					{
						Node n = nodes[j - 1];
						nodes[j - 1] = nodes[j];
						nodes[j] = n;
						sw = true;
					}
				
			}while(sw);
			if(i % 5 == 0)
				System.out.println(nodes[0].num);
		}		
		System.out.println(nodes[0]);
	}
	
	
	public static void main(String[] args) 
	{
		new Algoritm();
	}

}


class Node
{

	static Random rand = new Random();
	int[] data;
	int num;
	public Node(int[] d) 
	{
		data = d;
	}
	public static void f(Node n)
	{
		int s = 0;
		int sing = 1;
		for(int i = 0;i < n.data.length;i++)
		{
			s += sing * n.data[i];
			sing *= -1;
		}	
		n.num = s;
	}
	public static void mute(Node n)
	{
		int i = rand.nextInt(n.data.length);
		n.data[i] = -n.data[i];
	}
	public static Node newNode(Node n1,Node n2)
	{
		int i = rand.nextInt(n1.data.length);
		int[] ch = new int[n1.data.length];
		for(int j = 0;j < ch.length;j++)
			if(j < i)
				ch[j] = n1.data[j];
			else
				ch[j] = n2.data[j];
		return new Node(ch);
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0;i < data.length;i++)
		{
			sb.append(data[i]);
			if(i != data.length - 1)
				sb.append(',');
		}
		sb.append(']');
		return sb.toString();
	}
}

