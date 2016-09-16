package desuteam.com.times10;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    public App() throws IOException {

    	System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "64.33.135.190");
        System.setProperty("http.proxyPort", "8080");
    	PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data.csv", true)));
    	
    	
    	
    	for(int page = 48;page < 70;page++){
	    	List<Organizer> organizers;
	    	while(true){
				try {
					organizers = getOrganizers(page);
			    	sleep(5000);
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	System.out.println("Page: " + page);
	    	for(Organizer organizer : organizers){
	    		while(true)
			    	try {
						setOrganizerInfo(organizer);
				    	sleep(5000);
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
	    		if(organizer.event != null)
			    	while(true)
				    	try {
							setEventInfo(organizer, organizer.event);
					    	sleep(5000);
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
		    	
		    	print(printWriter, organizer);
		    	printWriter.flush();
		    	System.out.println(organizer.name + organizer.event);
	    	}
    	}
    	printWriter.close();
	}
    
    
    public void sleep(int time){
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void print(PrintWriter pw, Organizer organizer){
    	String res = String.format("\"%s\",\"%s\",\"%s\",\"%s\"", 
    			organizer.name,
    			organizer.info,
    			organizer.event == null? "":organizer.event.name,
    			organizer.event == null? "":organizer.event.info);
    	
    	pw.println(res);
    }
    
    public void setEventInfo(Organizer organizer, Event event) throws Exception {
    	Connection conn = Jsoup.connect(event.link);
    	conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    	conn.header("Upgrade-Insecure-Requests", "1");
    	conn.header("X-Requested-With", "XMLHttpRequest");
    	conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    	conn.header("DNT", "1");
    	conn.header("Referer", organizer.link);
    	conn.header("Accept-Encoding", "gzip, deflate, sdch");
    	conn.header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
    	conn.cookie("country", "Ukraine");
    	conn.cookie("countryCode", "UA");
    	Document doc = conn.get
    			();
    	
    	Element info = doc.select("p.desc").first();
    	event.info = info.text();
    	
    }
    public void setOrganizerInfo(Organizer organizer) throws Exception {
    	Connection conn = Jsoup.connect(organizer.link).timeout(10000);
    	conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    	conn.header("Upgrade-Insecure-Requests", "1");
    	conn.header("X-Requested-With", "XMLHttpRequest");
    	conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    	conn.header("DNT", "1");
    	conn.header("Referer", "http://10times.com/organizers/china");
    	conn.header("Accept-Encoding", "gzip, deflate, sdch");
    	conn.header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
    	conn.cookie("country", "Ukraine");
    	conn.cookie("countryCode", "UA");
    	Document doc = conn.get();
    	Element info = doc.select("blockquote.box").first();
    	organizer.info = info == null?"":info.text();
    	Element link = doc.select("div.maindiv h3 a").first();
    	if(link != null){
	    	Event event = new Event(link.text(), "http://10times.com" + link.attr("href"));
	    	organizer.event = event;
    	}
    }
    
    public List<Organizer> getOrganizers(int page) throws IOException{
    	
    	List<Organizer> result = new ArrayList<Organizer>();
    	
    	Connection conn = Jsoup.connect("http://10times.com/organizers/china?&ajax=1&page="+page);
    	conn.timeout(10000).method(Method.GET);
    	conn.header("Accept", "*/*");
    	conn.header("X-Requested-With", "XMLHttpRequest");
    	conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    	conn.header("DNT", "1");
    	conn.header("Referer", "http://10times.com/organizers/china");
    	conn.header("Accept-Encoding", "gzip, deflate, sdch");
    	conn.header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
    	conn.cookie("country", "Ukraine");
    	conn.cookie("countryCode", "UA");
    	
    	
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
