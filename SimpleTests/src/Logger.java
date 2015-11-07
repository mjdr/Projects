import java.io.File;


public class Logger 
{
	
	
	long totalLength = 0;
	int i = 6600,id = 0;
	
	public static void main(String[] args) {
		new Logger();
	}
	
	public Logger() 
	{
		//tree(new File("D:\\readmanga.me\\data\\"));
		System.out.println(totalLength/1024.0/1024.0);
	}
	
	
	public void tree(File url)
	{
		File[] files = url.listFiles();
		
		for(File f : files)
		{
			if(f.isDirectory())
				tree(f);
			else
			{
				if(id > i)
				{
						continue;
				}
				++id;
				totalLength += f.length();
				System.out.println(totalLength/1024.0/1024.0);
			}
		}
	
	}
	
}
