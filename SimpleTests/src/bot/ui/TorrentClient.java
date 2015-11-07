package bot.ui;

import jBittorrentAPI.Constants;
import jBittorrentAPI.DownloadManager;
import jBittorrentAPI.TorrentFile;
import jBittorrentAPI.TorrentProcessor;
import jBittorrentAPI.Utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TorrentClient 
{
	
	public static boolean ready = true;
	public static Thread task;
	
	public static void loadTask(String torrentFile , String path , int id)
	{
		if(!ready) return;
		
		if(!new File(path).exists())new File(path).mkdir();
		
		
		task = new Thread(new Task(torrentFile , path , id));
		task.start();
	}
	
	
	
	static class Task implements Runnable
	{
		String torrentPath , dist;
		int id;
		public Task(String torrentPath , String dist , int id) 
		{
			this.id = id;
			this.dist = dist;
			this.torrentPath = torrentPath;
		}
		@Override
		public void run() 
		{
			Constants.SAVEPATH = dist+"/";
		    TorrentProcessor tp = new TorrentProcessor();

	        TorrentFile tf = tp.getTorrentFile(tp.parseTorrent(torrentPath));
	        
	        tf.printData(false);
	        
	        DownloadManager dm = new DownloadManager(tf, Utils.generateID());

	        // «апуск закачки
	        dm.startListening(6882, 6889);
	        dm.startTrackerUpdate();

	        while(true)
	        {
	            // ≈сли загрузка завершена, то ожидание прерываетс€
	            if(dm.isComplete())
	            {
	                break;
	            }

	            try
	            {
	                Thread.sleep(1000);
	            }
	            catch(InterruptedException ex)
	            {
	                Logger.getLogger(DownloadManager.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	        // завершение закачки
	        dm.stopTrackerUpdate();
	        dm.closeTempFiles();
	        File f = new File(Constants.SAVEPATH + tp.getTorrentFile(tp.parseTorrent(torrentPath)).saveAs);
	        File f2 = new File(Constants.SAVEPATH + id + ".mkv");
	        f.renameTo(f2);
	        ready = true;
		}
		
	}
	
	
}
