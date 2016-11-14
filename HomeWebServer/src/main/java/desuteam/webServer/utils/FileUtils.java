package desuteam.webServer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	public static String readFile(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer sb = new StringBuffer();
		
		String line;
		
		while((line = br.readLine())!=null)
			sb.append(line).append('\n');
		
		br.close();
		
		return sb.toString();
	}
}
