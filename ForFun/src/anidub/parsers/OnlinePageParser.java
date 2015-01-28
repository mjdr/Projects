package anidub.parsers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anidub.Page;

public class OnlinePageParser implements PageParser 
{

	@Override
	public Page getPage(int page) throws IOException 
	{
		return getPage("http://online.anidub.com/page/"+page+"/");
	}

	@Override
	public Page getPage(String url) throws IOException 
	{
		if(url == null)
			throw new IllegalArgumentException("Url is null!");
		if(!url.startsWith("http://online.anidub.com/page/"))
			throw new IllegalArgumentException("Illegal url format! URL mast start with http://online.anidub.com/page/");
		Document doc = Jsoup.connect(url).get();

		Element root = doc.getElementById("dle-content");
		
		Elements titles = root.select("div.newstitle");
		Element tmp;
		for(Element e : titles)
		{
			tmp = e.select("div").first().select("div").first().select("a").first();
			System.out.println(tmp.text());
		}
		
		
		return null;
	}

}
