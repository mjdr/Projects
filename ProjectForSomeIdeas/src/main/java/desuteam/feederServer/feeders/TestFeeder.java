package desuteam.feederServer.feeders;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestFeeder extends AbstractFeeder {

	@Override
	public String getUrl() {
		return "test";
	}

	@Override
	public String getXMLFeed() throws IOException {
		
		Document doc = Jsoup.connect("http://www.xvideos.com/").get();
		
		Elements elems = doc.select("div.thumb-block");
		
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ",Locale.US);
		try{
		for(Element element : elems){
			
			
			
			Element link = element.select("a").first();
			if(link == null) continue;
			sb.append(String.format("<item><title>%s</title><description>%s</description><link>%s</link><pubDate>%s</pubDate><guid>%s</guid></item>\n",link.text(),link.text(),"http://www.xvideos.com"+link.attr("href"),format.format(Calendar.getInstance().getTime()),"http://www.xvideos.com"+link.attr("href")));
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		String result = String.format(""
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<rss version=\"2.0\">\n"
				+ "<channel>\n"
				+ "<title>XVideos.com</title>\n"
				+ "<pubDate>%s</pubDate>\n"
				+ "<language>ru-ru</language>\n"
				+ "<description>XVideos.com feeder</description>\n"
				+ "<link>http://www.xvideos.com/</link>\n"
				+ "<lastBuildDate>%s</lastBuildDate>\n"
				+ "%s\n</channel>\n</rss>",
				format.format(Calendar.getInstance().getTime()),
				format.format(Calendar.getInstance().getTime()),
				sb.toString());
		
		System.out.println(result);
		
		return result;
	}
	
	
	
}
