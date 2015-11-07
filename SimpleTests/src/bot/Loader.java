package bot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Loader 
{
	
	public static void init()
	{
		Inet.init();
		Local.init();
	}
	
	
	public static class Inet
	{
		public static final String feedUrl = "http://anistar.ru/anime/page/%d/";
		public static final String feedImgBaseUrl = "http://anistar.ru";
		public static final String torrentBaseUrl = "http://anistar.ru";
		public static final Map<String,String>cookies = new HashMap<String,String>();
		
		public static final String login = "jdr";
		public static final String password = "Windows7";
		
		
		public static void init()
		{
			cookies.put("blazingfast-layer7-protection", "8d2f975a116ecc355f0595a82ccf5431");
			cookies.put("__cfduid", "d087aa10613eb1039e7c9944260e60fd81429437570");
		}
		public static void login() throws IOException
		{
			
			getResponse("http://anistar.ru");
			Response resp = Jsoup.connect("http://anistar.ru/index.php").data
					(
							"login_name",login,
							"login_password",password,
							"login","submit"
					).execute();
			cookies.putAll(resp.cookies());
		}
		
		public static List<Post> getPosts(int page) throws IOException
		{
			List<Post>res = new ArrayList<Post>();
			Document doc = getPage(String.format(feedUrl,page));
			
			if(doc == null)
				System.out.println("Doc is NULL");
			Elements news = doc.select(".news");
			if(news!=null)
				for(Element n : news)
				{
					Element link = n.select("div.title_left a").first();
					Element img = n.select("div.news_avatar img").first();
					if(link != null && img != null)
					{
						String imgUrl;
						if(img.attr("src").startsWith("http://") || img.attr("src").startsWith("https://"))
							imgUrl = img.attr("src");
						else
							imgUrl = feedImgBaseUrl + img.attr("src");
						res.add(new Post(link.text() , link.attr("href") , imgUrl));
					}
				}
			
			return res;
		}
		
		public static void loadTorrentLinks(Post p) throws IOException
		{
			Document doc = getPage(p.link);
			
			Elements loadPanel = doc.getElementsByClass("list_torrent");
			if(loadPanel.size() == 0)
			{
				p.isVideoPost = false;
				return;
			}
			Elements torrents = loadPanel.select(".torrent");
			
			String[] urls = new String[torrents.size()];
			for(int i = 0;i < torrents.size();i++)
				urls[i] = torrentBaseUrl + torrents.get(torrents.size() - i - 1).select("div.title a").get(0).attr("href");
			
			p.torrentLinks = urls;
			
			
		}
		
		public static void loadTorrents(Post p) throws Exception
		{
			if(p.isVideoPost)
				for(int i = 0;i < p.torrentLinks.length;i++)
				{
					File f = new File(Local.torrentDir + "/" + p.dir+"/"+(i+1)+".torrent");
					if(!f.exists() || f.length() < 3 * 1024)
						loadFile(p.torrentLinks[i], Local.torrentDir + "/" + p.dir+"/"+(i+1)+".torrent");
				}
			
			
		}
		public static void loadPicture(Post p) throws Exception
		{
			if(new File(Local.torrentDir + "/" + p.dir+"/poster.jpg").exists()) return;
			loadFile(p.posterUrl, Local.torrentDir + "/" + p.dir+"/poster.jpg");
			
		}
		
		private static void loadFile(String url , String path) throws Exception
		{
			Response resp = getResponse(url);
			File f = new File(path);
			if(!f.exists())
				f.createNewFile();
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
			
			out.write(resp.bodyAsBytes());
			
			out.close();
			
			
		}
		private static Document getPage(String url) throws IOException
		{
			return Jsoup.parse(getResponse(url).body());
		}
		private static Response getResponse(String url) throws IOException
		{
			Connection c = Jsoup.connect(url);
			c.ignoreContentType(true);
			c.ignoreHttpErrors(true);
			c.followRedirects(true);
			c.cookies(cookies);
			Response r = c.execute();
			cookies.putAll(r.cookies());
			
			if(r.body().indexOf("<meta http-equiv=\"Refresh\" content=\"0;") != -1 || r.statusCode() != 200)
			{
				c = Jsoup.connect(url);

				c.ignoreHttpErrors(true);
				c.ignoreContentType(true);
				c.followRedirects(true);
				c.cookies(cookies);
				r = c.execute();
				cookies.putAll(r.cookies());
			}

			return r;
		}
	}
	
	public static class Local
	{
		public static final String torrentDir = "D://Anistar/torrents";
		public static final String videoTmpDir = "D://Anistar/tmp/video";
		public static final String tasksDir = "D://Anistar/tasks";
		public static final String videoDir = "D://Anistar/videoCatalog";
		public static final String mainFile = "D://Anistar/main.j";
		
		private static Vector<Post> posts;
		
		@SuppressWarnings("unchecked")
		public static void loadPosts() throws Exception
		{
			if(!new File(mainFile).exists())
			{
				if(posts == null)
					posts = new Vector<Post>();
				return;
			}
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(mainFile));
			posts = (Vector<Post>)in.readObject();
			in.close();
			
		}
		public static void clearCatalog(){ posts.clear(); }
		public static void savePosts() throws Exception
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(mainFile));
			out.writeObject(posts);
			out.close();
		}
		public static void putPost(Post p)
		{
			if(posts.contains(p))
				posts.remove(p);
			posts.add(p);
		}
		public static List<Post> findPost(String s)
		{
			s = s.toLowerCase();
			String[] parts = s.split(" ");
			List<Post>res = new ArrayList<Post>();
			String ltitle;
			
			for(Post p : posts)
			{
				ltitle = p.title.toLowerCase();
				if(ltitle.indexOf(s)!= -1)
				{
					res.add(p);
					continue;
				}
			}
			for(String part : parts)
			{
				if(part.length() == 0)
					continue;
				for(Post p : posts)
				{
						ltitle = p.title.toLowerCase();
						if(ltitle.indexOf(part)!= -1 && ltitle.indexOf(s) == -1)
						{
							res.add(p);
							break;
						}
				}
			}
			
			return res;
		}
		public static void createTorrentDir(Post p)
		{
			File dir = new File(torrentDir + p.dir);
			if(!dir.exists())
				dir.mkdir();
		}
		
		public static void init()
		{
			checkDirs();
			try {loadPosts();} catch (Exception e) {e.printStackTrace();}
		}
		
		private static void checkDirs()
		{
			File[] dirs = new File[]
			{
				new File("D://Anistar"),
				new File("D://Anistar/tmp"),
				
				new File(torrentDir),
				new File(videoTmpDir),
				new File(videoDir),
				new File(tasksDir)
			};
			
			for(File f : dirs)
				if(!f.exists())
					f.mkdir();
		}

		public static int size(){ return posts.size(); }
		public static Post get(int i){ return posts.get(i); }
		public static List<Post> getAll(){return posts; }
		
	}
	
}
