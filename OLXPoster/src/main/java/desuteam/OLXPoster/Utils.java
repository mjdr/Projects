package desuteam.OLXPoster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static List<String> readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<String>list = new ArrayList<String>();
		String line;
		
		while((line = br.readLine())!= null)
			if(line.trim().startsWith("//") || line.trim().length() > 0)
				list.add(line);
		br.close();
		return list;
	}
	
	public static List<User> parseUsers(List<String> fileData){
		List<User> res = new ArrayList<User>();
		
		if(fileData.size() == 0)  throw new IllegalArgumentException(" == 0");
		
		Pattern pattern = Pattern.compile("\\\"([^\\\"]*)\\\"(?:\\;|\\,)\\\"([^\\\"]*)\\\"");
		
		for(int i = 0;i < fileData.size();i++){
			Matcher matcher = pattern.matcher(fileData.get(i));
			if(matcher.find())
				res.add(new User(matcher.group(1), matcher.group(2)));
			
		}
		return res;
	}
	
	public static Map<String, User> parseUsersFromFile(File file) throws IOException{
		List<String> tmp = readFile(file);
		List<User> usrList = parseUsers(tmp);
		Map<String, User> map = new HashMap<>();
		for(User user : usrList)
			map.put(user.getLogin(), user);
		return map;
	}
	
	public void createFileIfNotExists(File file) throws IOException{
		if(!file.exists())
			file.createNewFile();
	}
	
	public static List<List<String>> loadImages(File imagesRootDir){
		File[] dirs = imagesRootDir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		
		if(dirs.length > 8)
			throw new IllegalArgumentException("Dirs length mast be <= 8");
		
		File[][] files = new File[dirs.length][];
		
		for(int i = 0;i < dirs.length;i++)
			files[i] = dirs[i].listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					String lPath = pathname.getName().toLowerCase();
					
					boolean ok = false;
					
					if(lPath.endsWith(".png"))
						ok = true;
					if(lPath.endsWith(".jpg"))
						ok = true;
					if(lPath.endsWith(".jpeg"))
						ok = true;
					
					return pathname.isFile() && ok;
				}
			});
		
		return getImageFiles(files);
	}

	private static List<List<String>> getImageFiles(File[][] files) {
		
		List<List<String>> result = new ArrayList<List<String>>();
		
		if(files.length == 0) return result;
		
		int min = Integer.MAX_VALUE;
		
		for(File[] list : files)
			min = Math.min(min, list.length);
		
		for(int i = 0;i < min;i++){
			List<String> tmp = new ArrayList<String>(files.length);
			for(int j = 0;j < files.length;j++)
				tmp.add(files[j][i].getAbsolutePath());
			result.add(tmp);
		}
		
		return result;
	}
	
	
	public static List<String> loadDescription(File file) throws IOException{
		List<String> data = readFile(file);
		
		List<String> result = new ArrayList<String>(data.size());
		
		for(int i = 0;i < data.size();i++)
			result.add(data.get(i).replace("\\n", "\n"));
		
		return result;
	}
	
	
	public static List<Integer> loadIntList(File file) throws IOException{
		List<String> data = readFile(file);
		List<Integer> result = new ArrayList<Integer>(data.size());
		
		for(int i = 0;i < data.size();i++)
			result.add(Integer.parseInt(data.get(i)));
		
		return result;
	}
	
	public static int readTaskProgress() throws IOException{
		File file = new File("task_progress.txt");
		if(!file.exists())
			return -1;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int res = Integer.parseInt(reader.readLine());
		reader.close();
		
		return res;
	}
	
	public static void writeTaskProgress(int progress) throws IOException{
		
		File file = new File("task_progress.txt");
		if(progress == -1 && file.exists()){
			file.delete();
			return;
		}
		
		PrintWriter writer = new PrintWriter(file);
		writer.println(progress);
		writer.close();
	}
	
	
	public static void writeLinks(File file , List<DateLink> links) throws IOException{
		PrintWriter pw = new PrintWriter(file);
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
		for(DateLink link : links)
			pw.printf("\"%s\",\"%s\"\n", link.getLogin() , formatter.format(link.getDate()));
		pw.close();
	}
	
	public static List<DateLink> readLinks(File file) throws IOException{
		List<DateLink> links = new ArrayList<>();
		if(!file.exists())
			return links;
		
		List<String> fileData = readFile(file);
		

		Pattern pattern = Pattern.compile("\\\"([^\\\"]*)\\\"(?:\\;|\\,)\\\"([^\\\"]*)\\\"");
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
		
		for(int i = 0;i < fileData.size();i++){
			Matcher matcher = pattern.matcher(fileData.get(i));
			if(matcher.find())
				try {
					links.add(new DateLink(matcher.group(1), formatter.parse(matcher.group(2))));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
		}
		return links;
	}
	
}
