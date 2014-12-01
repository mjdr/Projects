package com.osu;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Logginer 
{
	private static String loginUrl;
	
	static
	{
		loginUrl = "https://osu.ppy.sh/forum/ucp.php?mode=login";
		
		/*
		 * Method:POST
		 * 
		 * 
		 * 
		 * 
		 */
	}
	
	@SuppressWarnings("deprecation")
	public static boolean login(Account a) throws IOException
	{
		
		Connection c = Jsoup.connect("http://osu.ppy.sh");
		Document root = c.get();
		Map<String,String>cookies = c.response().cookies();
		Elements f = root.getElementsByClass("login-dropdown").select("form");
		Element form;
		Element f_sid;
		String sid;
		
		
		if(f.size() != 1)
			throw new IOException("Error on main page!(form)");
		form = f.get(0);
		loginUrl = form.attr("action");
		
		f = form.getElementsByAttributeValue("name", "sid");
		if(f.size() != 1)
			throw new IOException("Error on main page!(sid)");
		f_sid = f.get(0);
		sid = f_sid.attr("value");
		
		
		String cookie = "";
		String key;
		for(Iterator<String>i = cookies.keySet().iterator();i.hasNext();)
		{
			key = i.next();
			cookie += key +"="+cookies.get(key)+"; ";
		}
		HttpsURLConnection conn = (HttpsURLConnection)(new URL(loginUrl).openConnection());
		
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.41 Safari/537.36");
		conn.setRequestProperty("Cookie", cookie);
		//conn.setRequestProperty("", "");
		String req = "redirect=/ login=login autologin=on username=" + a.getLogin() + " password=" + a.getPassword() + " sid="+ sid;
		req = URLEncoder.encode(req);
		conn.connect();
		
		OutputStream out = conn.getOutputStream();
		out.write(req.getBytes());
		out.flush();
		out.close();
		System.out.println(conn.getHeaderField("Set-cookie"));
		
		
		conn.disconnect();
		
		return false;
	}
}
