package com.api.fotki.yandex;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.api.fotki.Photo;
import com.api.fotki.PhotoManager;

public class YandexPhotoManager implements PhotoManager {

	private String login = "misterjdr";
	private static String log = "73fc520bdf6349cdb1009489d0e09f2d";
	private static Map<String,String>albums = new HashMap<>();
	
	private HttpURLConnection conn;
	
	public YandexPhotoManager() 
	{
	}
	
	public String Dir2Alb(String dir) throws UnsupportedEncodingException
	{
		
		return Base64.encodeBase64String(dir.getBytes("UTF8"));
	} 
	public String Alb2Dir(String alb)
	{
		try {
			return new String(Base64.decodeBase64(alb.getBytes()) , "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void showBuffer()
	{
		Set<String> keys = albums.keySet();
		String key;
		for(Iterator<String>i = keys.iterator();i.hasNext();)
		{
			key = i.next();
			System.out.println(";;;"+key+";"+albums.get(key));
		}
	}
	
	public boolean createDir(String dir) throws IOException
	{
		
		if(existsDir(dir))
			return false;
		dir = Dir2Alb(dir);
		byte[] data;
		Map<String, String> args =  new HashMap<String, String>();
		
		args.put("Content-Type", "application/atom+xml; charset=utf-8; type=entry");
		data = ("<entry xmlns=\"http://www.w3.org/2005/Atom\" xmlns:f=\"yandex:fotki\"><title>"+dir+"</title></entry>").getBytes();
		args.put("Content-Length", "" + data.length);
			data = sendRequest("http://api-fotki.yandex.ru/api/users/"+login+"/albums/", "POST",args , data , true);
			if(conn.getResponseMessage().equals("CREATED"))
				return true;
			return false;
	}
	private byte[] sendRequest(String url ,String method, Map<String , String>parametrs, byte[] req , boolean handleInput) throws IOException
	{
		conn = (HttpURLConnection)new URL(url).openConnection();
		
		if(parametrs != null)
		{
			Entry<String, String>p;
			for(Iterator<Entry<String, String>>i = parametrs.entrySet().iterator();i.hasNext();)
			{
				p = i.next();
				conn.addRequestProperty(p.getKey(), p.getValue());
			}
		}
		if(method != null && method.length() > 2)
			conn.setRequestMethod(method);
		conn.addRequestProperty("Authorization", "OAuth "+log);
		
		if(req != null)
			conn.setDoOutput(true);
		conn.setDoInput(true);
		
		for(int i = 0;;i++)
		{
			try
			{
				conn.connect();
				break;
			}catch(ConnectException e)
			{
				if(e.getMessage().indexOf("timed out") == -1 || i == 19)
					throw e;
			}
		}
		
		OutputStream out = null;
		if(req != null)
		{
			out = conn.getOutputStream();
			out.write(req);
			out.flush();
		}
		byte[] data = null;
		if(handleInput)
		{
			InputStream in = conn.getInputStream();
			data = new byte[in.available()];
			in.read(data);
			in.close();
			}
		if(req != null)
			out.close();
		conn.disconnect();
		
		
		return data;
	}
	@Override
	public InputStream openInputStream(Photo p , int size) throws IOException 
	{
		HttpURLConnection c = (HttpURLConnection) new URL(p.getUrls()[size]).openConnection();
		c.setDoInput(true);
		return c.getInputStream();
	}
	private String albomNameToURL(String albom) throws IOException
	{
		
		if(albums.containsKey(albom))
			return albums.get(albom);
		
			String albomUrl = null;
			
			Map<String, String> map = getListOfDirs();
			albom = Alb2Dir(albom);
			if(!map.containsKey(albom))
				return null;
			albomUrl = map.get(albom);
				
		albums.put(albom, albomUrl);
		return albomUrl;
	}
	@Override
	public Photo[] getList(String dir) throws IOException
	{
		
		String albomUrl = albomNameToURL(dir);
		if(albomUrl == null)
			return null;
		
		byte[] resp = sendRequest(albomUrl + "photos/", "GET", null, null , true);
		Document doc = Jsoup.parse(new String(resp , "UTF8").replaceAll("f:uid", "QQQQID").replaceAll("f:img", "QQQQIMG"));
		
		Elements entries = doc.select("entry"),links;
		Element ent,l;
		
		Photo[] res = new Photo[entries.size()];
		
		for(int i = 0;i < res.length;i++)
		{
			ent = entries.get(i);
			links = ent.select("QQQQIMG");
			int[] ws = new int[links.size()];
			String[] urls = new String[links.size()];
			for(int j = 0;j < links.size();j++)
			{
				l = links.get(j);
				ws[j] = Integer.parseInt(l.attr("width"));
				urls[j] = l.attr("href");
			}
			res[i] = Photo.createLink(null, Integer.parseInt(ent.select("QQQQID").text()),ws , urls, this);
		}
		
		
		return res;
	}
	@Override
	public boolean loadOnServer(Photo photo , String dir) throws IOException
	{
		
		if(!existsDir(dir))
			throw new IOException("Dir " + dir + " not exists!");
		dir = Dir2Alb(dir);
		String albomUrl = albomNameToURL(dir) + "photos/";
		
		Map<String, String>attrs = new HashMap<String, String>();
		attrs.put("Content-Type", photo.getContentTypes()[0]);
		attrs.put("Content-Length", "" + photo.getData().length);
		
		byte[] resp = sendRequest(albomUrl, "POST", attrs, photo.getData() , true);
		Document doc = Jsoup.parse(new String(resp , "UTF8").replaceAll("f:img", "fQQQQ").replaceAll("f:uid", "QQQQID"));
		Elements entries = doc.select("entry") , links;
		
		Element id = doc.select("QQQQID").get(0);
		photo.setId(Integer.parseInt(id.text()));
		
		
		
		Element ent , l;
		
		if(entries.size() == 0)
			return false;
			ent = entries.get(0);
			
			links = ent.select("fQQQQ");
			int[] ws = new int[links.size()];
			String[] urls = new String[links.size()];
			for(int j = 0;j < links.size();j++)
			{
				l = links.get(j);
				ws[j] = Integer.parseInt(l.attr("width"));
				urls[j] = l.attr("href");
			}
		
		photo.setWidthes(ws);
		photo.setUrls(urls);
		photo.setData(null);
		photo.setPlace(Photo.Type.LINK);
		
		return true;
	}
	@Override
	public boolean removeDir(String dir) throws IOException 
	{
		if(!existsDir(dir))
			return false;
		dir = Dir2Alb(dir);
		sendRequest(albomNameToURL(dir), "DELETE", null, null,false);
			if(conn.getResponseCode() == 204 && albums.containsKey(dir))
				albums.remove(dir);
				
		return conn.getResponseCode() == 204;
		
	}
	@Override
	public boolean existsDir(String dir) throws IOException 
	{
			dir = Dir2Alb(dir);
			return albomNameToURL(dir) != null;
	}
	public Map<String , String> getListOfDirs() throws IOException
	{
		Map<String,String>res = new HashMap<String, String>();
		String dt = new String(sendRequest("http://api-fotki.yandex.ru/api/users/"+login+"/albums/", "GET", null, null , true) , "UTF8");
		Document doc = Jsoup.parse(dt);
		Elements entries = doc.select("entry");
		Element e;
		for(int i = 0;i < entries.size();i++)
			{
				e = entries.get(i);
				if(e.select("title").size() > 0 && e.select("link").size() > 0 && e.select("link").get(0).attr("href") != null)
				{
					res.put(Alb2Dir(e.select("title").get(0).text()),e.select("link").get(0).attr("href"));
					if(!albums.containsKey(e.select("title").get(0).text()))
						albums.put(e.select("title").get(0).text(),e.select("link").get(0).attr("href"));
				}
			}
		
		return res;
	}


}
