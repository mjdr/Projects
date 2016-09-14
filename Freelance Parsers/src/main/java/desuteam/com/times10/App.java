package desuteam.com.times10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App 
{
    public static void main( String[] args ) throws Exception{
    	
    	new App();
    	
    	
    }
    
    public App() throws Exception {
    	
    	List<Organizer> organizers = getOrganizers(1);
    	
    	Organizer organizer = organizers.get(0);
    	setOrganizerInfo(organizer);
    	setEventInfo(organizer.event);
    	
    	print(organizer);
	}
    
    
    public void print(Organizer organizer){
    	String res = String.format("\"%s\",\"%s\",\"%s\",\"%s\"", 
    			organizer.name,
    			organizer.info,
    			organizer.event.name,
    			organizer.event.info);
    	
    	System.out.println(res);
    }
    
    public void setEventInfo(Event event) throws Exception {
    	Document doc = Jsoup.connect(event.link).get();
    	
    	Element info = doc.select("p.desc").first();
    	event.info = info.text();
    	
    }
    public void setOrganizerInfo(Organizer organizer) throws Exception {
    	Document doc = Jsoup.connect(organizer.link).get();
    	Element info = doc.select("blockquote.box").first();
    	organizer.info = info.text();
    	Element link = doc.select("div.maindiv h3 a").first();
    	Event event = new Event(link.text(), "http://10times.com" + link.attr("href"));
    	organizer.event = event;
    }
    
    public List<Organizer> getOrganizers(int page) throws IOException{
    	
    	List<Organizer> result = new ArrayList<Organizer>();
    	
    	Connection conn = Jsoup.connect("http://10times.com/organizers/china?&ajax=1&page="+page);
    	conn.method(Method.GET);
    	
    	Response response = conn.execute();
    	
    	Document doc = Jsoup.parseBodyFragment(response.body());
    	
    	Elements divs = doc.select("div.col-md-8");
    	
    	for(Element div : divs){
    		Element link = div.select("h4 a").get(0);
    		result.add(new Organizer(link.text(), "http://10times.com" + link.attr("href")));
    	}
    	
    	return result;
    }
}
