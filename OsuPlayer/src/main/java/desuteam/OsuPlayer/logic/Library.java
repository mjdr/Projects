package desuteam.OsuPlayer.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Library {
	
	private static File osuSoundFolderPath = new File("C:/Users/PC/AppData/Local/osu!/Songs");
	private File[] folders;
	private MapData[] mapData;
	private int current;
	
	public Library(String[] args) {
		osuSoundFolderPath = new File(args[0]);
	}
	
	public void load(){
		folders = getFolders();
    	mapData = parseAll(folders);
    	
    	current = new Random().nextInt(mapData.length);
	}
	
	public MapData[] getMapData() {
		return mapData;
	}
	public synchronized MapData getCurrentMapData() {
		return mapData[current];
	}
	
	
	private File[] getFolders(){
    	File[] folders = osuSoundFolderPath.listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				if(!pathname.isDirectory()) return false;
				
				File[] osuFiles = pathname.listFiles((file)->file.getName().endsWith(".osu"));
				
				return osuFiles.length > 0;
			}
		});
    	return folders;
    }
    
    private MapData[] parseAll(File[] folders){
    	
    	MapData[] data = new MapData[folders.length];
    	
    	for(int i = 0;i < data.length;i++)
    		data[i] = parseMap(folders[i]);
    	
    	return data;
    }


	private MapData parseMap(File folder) {
		
		File[] osuFiles = folder.listFiles((file)->file.getAbsolutePath().endsWith(".osu"));
		
		
		return parseOsuFile(folder,osuFiles[0]);
	}

	private MapData parseOsuFile(File folder,File osuFile) {
		
		List<String> data =  fileAsListOfStrings(osuFile);
		
		
		Pattern[] patterns = {
				Pattern.compile("AudioFilename: (.*)"),
				Pattern.compile("Title:(.*)"),
				Pattern.compile("Artist:(.*)"),
				Pattern.compile("\\d+,\\d+,\\\"(.*)\\\"")
		};
		
		String[] resultData = multiParse(data,patterns);
		
		
		return new MapData(
				folder,
				resultData[2] + " - " + resultData[1],
				resultData[3] == null ? null:new File(folder.getAbsolutePath() + "/" + resultData[3]), 
				resultData[0] == null ? null:new File(folder.getAbsolutePath() + "/" + resultData[0]));
	}
    
	private List<String> fileAsListOfStrings(File file){
		List<String> result = new ArrayList<>();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String line;
			
			while((line = reader.readLine())!=null)
				result.add(line);
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String[] multiParse(List<String> dataSet, Pattern[] patterns){
		
		String[] results = new String[patterns.length];
		
		for(String data : dataSet){
			for(int i = 0;i < patterns.length;i++){
				if(results[i] != null) continue;
				Matcher matcher = patterns[i].matcher(data);
				if(matcher.find())
					results[i] = matcher.group(1);
			}
		}
		return results;
	}

	public synchronized void nextTrack() {
		current++;
		if(current >= mapData.length)
			current = 0;
	}

	public synchronized void prevTrack() {
		current--;
		if(current == -1)
			current = mapData.length - 1;
		
	}
	
	public synchronized int getCurrent() {
		return current;
	}
}
