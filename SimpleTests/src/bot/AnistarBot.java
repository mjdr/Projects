package bot;
import java.io.IOException;
import java.util.List;


public class AnistarBot implements Runnable
{
	public static final int timeStep = 1000 * 60 * 5;//5min
	
	
	public static void main(String[] args) 
	{
		new AnistarBot();
	}
	
	public AnistarBot() 
	{
		new Thread(this).start();
	}
	
	@Override
	public void run()
	{
		init();
		for(;!Thread.interrupted();)
		{
			loop();
			try{Thread.sleep(timeStep);}catch (InterruptedException e){break;}
		}
		dispose();
	}
	
	
	public void init()
	{
		Loader.init();
	}
	public void loop()
	{
		
		for(int i = 1;i <= 10;i++)
		{
			try 
			{
				List<Post>posts = Loader.Inet.getPosts(i);
				for(Post p : posts)
				{
					try 
					{
						System.out.println(p);
						Loader.Local.createTorrentDir(p);
						Loader.Inet.loadPicture(p);
						Loader.Inet.loadTorrentLinks(p);
						Loader.Inet.loadTorrents(p);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					Loader.Local.putPost(p);
				}
			} catch (IOException e) 
			{
				e.printStackTrace();
				i--;
			}
			
		}
		try {
			Loader.Local.savePosts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void dispose(){}
	
	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------
	abstract class Task implements Runnable
	{
		volatile boolean isDone;
		
		@Override
		public void run() 
		{	
			isDone = false;
			work();
			isDone = true;
		}
		abstract void work();
	}
	
	
	
	
	
	
	
	
	
	
	
}
