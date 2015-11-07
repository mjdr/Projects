package bot.ui;

import jBittorrentAPI.Constants;
import jBittorrentAPI.DownloadManager;
import jBittorrentAPI.TorrentFile;
import jBittorrentAPI.TorrentProcessor;
import jBittorrentAPI.Utils;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import bot.Loader;
import bot.Post;

public class MainFrame extends JFrame 
{
	
	private static final long serialVersionUID = 1L;
	TitleList postsList;
	TitleImage titleImage;
	SearchBar searchBar;
	TorrentsList torrentsList;
	Post current;
	
	public MainFrame() 
	{
		super("db");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1100,600));
		postsList = new TitleList(this);
		titleImage = new TitleImage();
		searchBar = new SearchBar(this);
		torrentsList = new TorrentsList(this);
		
		
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		JScrollPane p = new JScrollPane(postsList);
		p.setPreferredSize(new Dimension(700,600));

		pane.add(p, BorderLayout.CENTER);
		pane.add(titleImage, BorderLayout.EAST);
		pane.add(torrentsList, BorderLayout.WEST);
		pane.add(searchBar, BorderLayout.NORTH);
		
		
		init();
		
		pack();
		setVisible(true);
		
	}
	
	
	public void init()
	{
		Loader.init();
		postsList.init();
	}
	
	
	
	public static void main(String[] args) 
	{
		new MainFrame();
	}

	public void search(String text) 
	{
		postsList.search(text);
	}


	public void postChanged(Post p) 
	{
		current = p;
		titleImage.setImage(p);
		torrentsList.set(p);
	}


	public void loadTorrent(int id) 
	{
		TorrentClient.loadTask(Loader.Local.torrentDir + current.dir + "/" + id + ".torrent", Loader.Local.videoDir + current.dir , id);
	}
	
}
