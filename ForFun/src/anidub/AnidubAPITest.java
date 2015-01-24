package anidub;

import java.io.IOException;

import anidub.Anidub;
import anidub.Page;
import anidub.Post;
import anidub.parsers.PageParser;


public class AnidubAPITest 
{
	public static void main(String[] args) throws IOException
	{
		PageParser parser = Anidub.Tracker.getParser();
		Page page = parser.getPage(1);
		
		for(Post p : page)
			System.out.println(p);
	}
}
