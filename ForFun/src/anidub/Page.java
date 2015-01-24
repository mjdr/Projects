package anidub;

import java.util.Vector;

public class Page extends Vector<Post>
{
	
	public Page(Post[] posts) 
	{
		if(posts == null)
			throw new IllegalArgumentException("Posts is null");
		for(Post p : posts)
			add(p);
	}
}
