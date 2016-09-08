package desuteam.OLXPoster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Options {
	
	public static final String fileName = "options.txt";
	

	public static final int STATUS_NO_LOAD = 0;
	public static final int STATUS_CREATED = 1;
	public static final int STATUS_DONE = 2;
	
	
	private static String
					imageRootDir,
					userDataFilePath,
					descriptionFilePath,
					titlesFilePath,
					delaiesFilePath,
					pricesFilePath;
	
	private static int ordersByUser;
	private static boolean productCourier;
	

	private static int status;
	
	private Options() {}
	
	public static void load() throws IOException{
		
		if(!(new File(fileName)).exists()){
			createOptionsFile();
			status = STATUS_CREATED;
			return;
		}
		else parseOptions();
	}
	
	private static void parseOptions() throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		
		String line;
		
		while((line = reader.readLine())!=null){
			line = line.trim();
			if(line.startsWith("//") || line.length() == 0)
				continue;
			parseLine(line);
		}
		reader.close();
	}

	private static void parseLine(String line) {
		Matcher matcher = Pattern.compile("(\\w+)=\\\"([^\\\"]*)\\\"").matcher(line);
		
		if(matcher.find()){
			
			if(matcher.group(1).equals("userDataFilePath"))
				userDataFilePath = matcher.group(2);
			else if(matcher.group(1).equals("titlesFilePath"))
				titlesFilePath = matcher.group(2);
			else if(matcher.group(1).equals("pricesFilePath"))
				pricesFilePath = matcher.group(2);
			else if(matcher.group(1).equals("ordersByUser"))
				ordersByUser = Integer.parseInt(matcher.group(2));
			else if(matcher.group(1).equals("descriptionFilePath"))
				descriptionFilePath = matcher.group(2);
			else if(matcher.group(1).equals("delaiesFilePath"))
				delaiesFilePath = matcher.group(2);
			else if(matcher.group(1).equals("productCourier"))
				productCourier = matcher.group(2).equals("1");
			else if(matcher.group(1).equals("imageRootDir"))
				imageRootDir = matcher.group(2);
		}
		
		
	}

	private static void createOptionsFile() throws IOException{
		PrintWriter pw = new PrintWriter(fileName);

		pw.println("// Если строка начинаеться с // это коменттарий.Он никак не влияет на роботу программы.");
		pw.println("");
		pw.println("//Путь к файлу с данными аккаунтов от OLX");
		pw.println("userDataFilePath=\"\"");
		pw.println("");
		pw.println("//Путь к файлу с названиями товаров");
		pw.println("titlesFilePath=\"\"");
		pw.println("");
		pw.println("Путь к файлу с ценами (положительное целое число)");
		pw.println("pricesFilePath=\"\"");
		pw.println("");
		pw.println("//Путь к файлу с описаниями товаров (более 100 символов каждое)");
		pw.println("descriptionFilePath=\"\"");
		pw.println("");
		pw.println("//Пути к папке с картинками");
		pw.println("imageRootDir=\"\"");
		pw.println("");
		pw.println("//Нужна ли доставка (1 - нужна, 0 - не нужна)");
		pw.println("productCourier=\"1\"");
		pw.println("");
		pw.println("///Путь к файлу с задержками обьявлений (в секундах)");
		pw.println("delaiesFilePath=\"\"");
		pw.println("");
		pw.println("//Количество обьявлений залитых с одного аккаунта");
		pw.println("ordersByUser=\"3\"");
		pw.println("");
		
		pw.close();
	}
	
	public static String checkOptions(){
		
		if(userDataFilePath == null || userDataFilePath.length() == 0) return "Путь к файлу с данными пользователей не задан!(userDataFilePath)";
		if(descriptionFilePath == null || descriptionFilePath.length() == 0) return "Путь к файлу с описаниями товаров не задан!(descriptionFilePath)";
		if(titlesFilePath == null || titlesFilePath.length() == 0) return "Путь к файлу с названиями товаров не задан!(titlesFilePath)";
		if(imageRootDir == null || imageRootDir.length() == 0) return "Путь к папки с картинками товаров не задан!(imageRootDir)";
		if(pricesFilePath == null || pricesFilePath.length() == 0) return "Путь к файлу с ценами товаров не задан!(pricesFilePath)";
		if(delaiesFilePath == null || delaiesFilePath.length() == 0) return "Путь к файлу с задержками не задан!(delaiesFilePath)";

		if(!(new File(userDataFilePath).exists())) return "Файл с данными пользователей не найден!(userDataFilePath)";
		if(!(new File(descriptionFilePath).exists())) return "Файл с описаниями товаров не найден!(descriptionFilePath)";
		if(!(new File(titlesFilePath).exists())) return "Файл с названиями товаров не найден!(titlesFilePath)";
		if(!(new File(imageRootDir).exists())) return "Папка с картинками товаров не найден!(imageRootDir)";
		if(!(new File(pricesFilePath).exists())) return "Файл с ценами товаров не найден!(pricesFilePath)";
		if(!(new File(delaiesFilePath).exists())) return "Файл с задержками не найден!(delaiesFilePath)";
		
		
		return null;
	}
	
	public static boolean isProductCourier() {
		return productCourier;
	}
	public static int getStatus() {
		return status;
	}
	public static String getTitlesFilePath() {
		return titlesFilePath;
	}
	public static String getDescriptionFilePath() {
		return descriptionFilePath;
	}
	public static String getUserDataFilePath() {
		return userDataFilePath;
	}
	public static String getImageRootDir() {
		return imageRootDir;
	}
	public static int getOrdersByUser() {
		return ordersByUser;
	}
	public static String getDelaiesFilePath() {
		return delaiesFilePath;
	}
	public static String getPricesFilePath() {
		return pricesFilePath;
	}
}
