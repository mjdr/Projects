import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.api.fotki.Photo;
import com.api.fotki.yandex.YandexPhotoManager;


public class Uploader 
{
	
	
	String baseUrl="D:\\readmanga.me\\data\\";
	YandexPhotoManager manager;
	
	int id = 0;
	
	LinkedBlockingQueue<Photo>photos;
	
	public Uploader() throws IOException 
	{
		manager = new YandexPhotoManager();
		
		photos = new LinkedBlockingQueue<Photo>(20);
		
		new Thread(new FileLoader(photos)).start();
		new Thread(new FileUploader(photos)).start();
		new Thread(new FileUploader(photos)).start();
		new Thread(new FileUploader(photos)).start();
		new Thread(new FileUploader(photos)).start();
		
	}
	
	
	
	
	public static void main(String[] args) throws IOException 
	{
		
		new Uploader();
	}
	
	private class FileLoader implements Runnable
	{
		String baseUrl="D:\\readmanga.me\\data\\";
		LinkedBlockingQueue<Photo>pipe;
		public FileLoader(LinkedBlockingQueue<Photo> pipe) 
		{
			this.pipe = pipe;
		}
		
		@Override
		public void run() 
		{
			tree(new File(baseUrl));
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
					
					String qurl = f.toString().replace(baseUrl, "");
					if(qurl.substring(qurl.lastIndexOf('.')).equalsIgnoreCase(".png"))
					{
						id++;
						try {
							
							pipe.put(getPhoto(qurl.substring(0,qurl.lastIndexOf('\\')) , f));
							f.delete();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			
		}
		
		private Photo getPhoto(String folder , File file) throws Exception
		{
			InputStream in = new BufferedInputStream( new FileInputStream(file));
			byte data[] = new byte[in.available()];
			in.read(data);
			in.close();
			
			return Photo.createPhoto(file.getName(), "image/png", data, folder.toString().replace(baseUrl, "") ,manager);
		}
		
	}
	private class FileUploader implements Runnable
	{
		LinkedBlockingQueue<Photo> pipe;
		
		public FileUploader(LinkedBlockingQueue<Photo> pipe) 
		{
			this.pipe = pipe;
		}
		
		@Override
		public void run() 
		{
			while(!Thread.interrupted())
			{
				try 
				{
					Photo p = pipe.take();
					manager.createDir(p.getAlbom());
					System.out.println(p.getAlbom());
					p.loadOnServer();
					System.out.println(p.getAlbom());
					
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
					break;
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
}
