import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class KickStat 
{
	String url = "https://www.kickstarter.com/projects/1465468930/socuwan-the-community-driven-indie-mmorpg";
	PrintWriter pw;
	public KickStat() 
	{
		try {
			pw = new PrintWriter("stats.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		while(true)
		{
			stats();
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	
	}
	public static void main(String[] args) {
		new KickStat();
	}
	public void stats()
	{
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		Elements elements = doc.select("data.Project1323029524");
		Element number = elements.first();
		Element money = elements.get(1);

		int n = Integer.parseInt(number.text());
		float mn = Float.parseFloat(money.text().substring(1).replace(',', '.'));
		Calendar calendar = Calendar.getInstance();
		
		
		pw.println(n+";"+mn+";"+calendar.get(Calendar.HOUR_OF_DAY)+";"+calendar.get(Calendar.MINUTE)+";"+calendar.get(Calendar.SECOND)+";"+calendar.get(Calendar.DAY_OF_MONTH)+";"+(calendar.get(Calendar.MONTH)+1)+";"+calendar.get(Calendar.YEAR));
		System.out.println(n+";"+mn+";"+calendar.get(Calendar.HOUR_OF_DAY)+";"+calendar.get(Calendar.MINUTE)+";"+calendar.get(Calendar.SECOND)+";"+calendar.get(Calendar.DAY_OF_MONTH)+";"+(calendar.get(Calendar.MONTH)+1)+";"+calendar.get(Calendar.YEAR));
		pw.flush();
		
	}
	
}
