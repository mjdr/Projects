package desuteam.TstM;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
    	new App();
    }
    
    public App() {
    	
    	try {
    		Connection conn = Jsoup.connect("https://www.amazon.com/dp/B013HW2BJK/");
    		conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
    		long t = System.currentTimeMillis();
			Response response = conn.execute();
			System.out.println("Load time: "+((System.currentTimeMillis() - t)/1e3f)+" sec");
			System.out.println("Doc length: "+response.bodyAsBytes().length);
			t = System.currentTimeMillis();
			
			Document doc = response.parse();
			String title = doc.select("#productTitle").first().text();
			String imageUrl = doc.getElementById("imgTagWrapperId").select("img").first().attr("src");
			System.out.println("Process time: "+((System.currentTimeMillis() - t)/1e3f)+" sec");

			System.out.println("Title: " + title);
			System.out.println("ImageUrl: " + imageUrl);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
}
