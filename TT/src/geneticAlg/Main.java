package geneticAlg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Main extends JComponent implements Runnable
{
	
	Person[] popumation;
	int N = 10;
	
	Person bestResult;
	int Iw;
	int Ih;
	Random rand = new Random();
	function f;
	
	Timer upd = new Timer(1000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			repaint();
		}
	});
	
	public Main() throws IOException 
	{
		f = new function() {
			
			@Override
			public float f(float x) 
			{
				return x * 2.5F;
			}
		};
		
		
		
		popumation = new Person[N];
		for(int i = 0;i < N;i++)
		{
			popumation[i] = Person.getRandomPerson();
			Person.compare(f, popumation[i]);
		}
		
		new Thread(this).start();
		upd.start();
	}
	
	
	private void iteration()
	{
		for(int i = 0;i < N;i++)
			Person.compare(f, popumation[i]);
		Arrays.sort(popumation, new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) 
			{
				return (int)Math.signum(o2.score - o1.score);
			}
		});
		bestResult = popumation[0];
		System.out.println(popumation[0].score);
		System.out.println(popumation[N - 1].score);
		System.out.println("-------------------------");
		
		int mut = (int)(Math.random() * N / 4);
		
		int index = 3*N/4;
		
		for(int i = 0;i < mut;i++)
			popumation[index + i] = Person.mutation(popumation[rand.nextInt(N)]);
		
		index += mut;
		
		int newP = N - index;

		for(int i = 0;i < newP;i++)
			popumation[index + i] = Person.newPerson(popumation[rand.nextInt(N/4)], popumation[rand.nextInt(N/4)]);
		repaint();
	}
	
	@Override
	public void paint(Graphics g) 
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, 1220, 820);
		
		g.setColor(Color.white);
		float dx = 10F/1220;
		float yy = 6;
		for(int x = 0;x < 1220;x++)
			g.drawLine(
					x,
					(int)(820*(f.f(-5+x*dx) + yy)/(2*yy)) , 
					x+1, 
					(int)(820*(f.f(-5+(x + 1)*dx) + yy)/(2*yy)));
		
		g.setColor(Color.red);
		for(int x = 0;x < 1220;x++)
			g.drawLine(
					x,
					(int)(820*(bestResult.calculate(-5+x*dx) + yy)/(2*yy)) ,
					x, 
					(int)(820*(bestResult.calculate(-5+(x + 1)*dx) + yy)/(2*yy)));
		
	}
	
	
	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1220, 820);
		f.add(new Main());
		f.setVisible(true);
		
	}


	@Override
	public void run() 
	{
		for(int i = 0;;i++)
		{
			System.out.println("Iteration: "+ i);
			iteration();
			
			if(bestResult.score > 0.995)
			{
				System.out.println(Arrays.toString(bestResult.k));
				break;
			}
			
			
			/*try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
	}
	
	
}
