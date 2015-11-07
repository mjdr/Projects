package bot;

import java.io.Serializable;

public class Post implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	
	
	public String title , link , posterUrl,dir;
	public String[] torrentLinks;
	public boolean isVideoPost;
	public final String id;

	public Post(String title, String link , String posterUrl) 
	{
		this.title = title;
		this.link = link;
		this.posterUrl = posterUrl;
		isVideoPost = true;
		id = link + "@" + title;
		dir = link.substring(link.lastIndexOf('/'), link.lastIndexOf('.')-1);
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof Post)
		{
			Post p = (Post) obj;
			return p.id.equals(id);
		}
		return false;
	}
	
	@Override
	public String toString() 
	{
		return title.split("/")[0];
	}
	
}
