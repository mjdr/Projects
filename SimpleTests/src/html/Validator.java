package html;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator 
{
	
	String data;
	
	public static void main(String[] args) throws IOException {
		new Validator(new String[]
				{
				
					"http://www.phbme.kpi.ua/~popov/teaching.html",
					"https://en.wikipedia.org/wiki/HT",
					"http://habrahabr.ru/",
					"http://habrahabr.ru/company/ua-hosting/blog/268593/"
				
				});
	}
	
	public Validator(String[] args) throws IOException 
	{
		for(String url : args)
		{
			loadData(url);
			Vector<Tag>tags = getTags();
			System.out.println(url+": "+validate(tags));
		}
			
	}
	
	
	
	public void loadData(String url) throws IOException
	{
		HttpURLConnection c = (HttpURLConnection)new URL(url).openConnection();
		
		c.setDoInput(true);
		
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
		StringBuffer sb = new StringBuffer();
		
		String line;
		while((line = br.readLine())!=null) sb.append(new String(line.getBytes(),"UTF-8")).append('\n');
		
		br.close();
		c.disconnect();
		
		data = sb.toString();
	}
	
	
	public Vector<Tag>getTags()
	{
		Vector<Tag>res = new Vector<Tag>();
		Pattern pattern = Pattern.compile("<(\\/)?([a-z]+)");
		Matcher matcher = pattern.matcher(data);
		
		boolean lock = false;
		
		 while (matcher.find()) 
		 {
			 Tag tag = new Tag(matcher.group(2), matcher.group(1) != null , matcher.start(), matcher.end());
			 
			 if(tag.name.equals("script"))
			 {
				 if(!tag.closed)res.add(tag);
				 lock = !tag.closed;
			 }
			 
			 if(!lock)
				 res.add(tag);
		 }
		
		return res;
	}
	
	public boolean validate(Vector<Tag> tags)
	{
		Stack<Tag>stack = new Stack<Tag>();
		
		for(Tag tag : tags)
		{
			if(
					tag.name.equals("img") ||
					tag.name.equals("meta") ||
					tag.name.equals("link") ||
					tag.name.equals("br") ||
					tag.name.equals("input")) continue;
			
			if(!tag.closed)
				stack.push(tag);
			else
			{
				if(stack.empty()) return false;
				Tag tag2 = stack.pop();
				if(!tag.name.equals(tag2.name))
				{
					System.out.println(data.substring(tag2.start, tag.end));
					return false;
				}
			}
			
		}
		return stack.empty();
	}
	
	
	
	
	
	
	
}
