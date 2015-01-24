package anidub.parsers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anidub.Page;
import anidub.Post;

public class TrackerPageParser implements PageParser
{
	@Override
	public Page getPage(int pageID) throws IOException
	{
		if(pageID < 0)
			throw new IllegalArgumentException("PageID < 0");
		return getPage("http://tr.anidub.com/page/"+pageID+"/");
	}

	@Override
	public Page getPage(String url) throws IOException 
	{
		if(url == null)
			throw new IllegalArgumentException("URL is null");
		if(!url.startsWith("http://tr.anidub.com/page/"))
			throw new IllegalArgumentException("Illegal url format\n url mast start with http://tr.anidub.com/page/");
		Document doc = Jsoup.connect(url).get();
		
		Element root = doc.getElementById("dle-content");
		Elements articles = root.select("article.story");
		Element tmp;

		String title;
		String link;
		String author;
		String imageLink;
		String description;
		
		Post[] posts = new Post[articles.size()];
		Element e;
		
		for(int i = 0;i < articles.size();i++)
		{
			e = articles.get(i);
			tmp = e
					.select("div.story_h").get(0)
					.select("h2").get(0)
					.select("a").get(0);
			title = tmp.text();
			link = tmp.attr("href");
			
			tmp = e
					.select("ul.story_inf").get(0)
					.select("li").get(0)
					.select("a").get(0);
			author = tmp.text();
			tmp = e
					.select("div.story_c").get(0)
					.select("span").get(0)
					.select("a").get(0).select("img").get(0);
			imageLink = tmp.attr("src");
			tmp = e.select("div.story_c").get(0);
			
			description = tmp.text().split("Описание: ")[1];
			posts[i] = new Post(title, link, author, imageLink, 0, description);
		}
		
		return new Page(posts);
	}
}
