import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UpworkJsoup {
	private static final int MAX_COUNT = 100;
	private static final String inFile = "KR Pilot Car Search.csv";
	private static final String outFile = "out.csv";

	private static void showHelp() {
		System.out.println("-c: Skip CarFax");
		System.out.println("-a: Skip AutoCheck");
		System.out.println("-h: Help");
	}

	public static void main(String[] args) {
		BufferedReader br = null;
		FileOutputStream fos = null;
		String line = "";
		String cvsSplitBy = ",";
		boolean bHideCarFax = false, bHideAutoCheck = false;
		int count = 0;
		if (args.length == 0) {} else if (args.length > 1 || args[0].equals("-h")) {
			showHelp();
			return;
		} else if (args[0].equals("-a")) {
			bHideAutoCheck = true;
		} else if (args[0].equals("-c")) {
			bHideCarFax = true;
		} else {
			showHelp();
			return;
		}
		try {
			fos = new FileOutputStream(outFile);
			br = new BufferedReader(new FileReader(inFile));
			while ((line = br.readLine()) != null) {
				String[] info = line.split(cvsSplitBy);
				count++;
				String outline = line;
				if (info[0].startsWith("\"")) {
					String path = info[4].substring(info[1].indexOf("https:"));
					path = path.substring(0, path.length() - 2);
					System.out.print(count + " ");
					if (!bHideAutoCheck) {
						for (int i = 0; i < MAX_COUNT; i++) {
							try {
								Document doc = Jsoup.connect(path).get();
								String recordString = doc.select("h4.record-count").first().text().trim().substring(9);
								String numberString = recordString.substring(0, recordString.indexOf("hi") - 1);
								int autoCheckVal = Integer.parseInt(numberString);
								String autoCheck = ((autoCheckVal == 0) ? "\"-\"" : ("" + autoCheckVal));
								outline = line + autoCheck;
								System.out.print(" Autocheck " + ((autoCheckVal == 0) ? "-" : autoCheck));
								break;
							} catch (Exception e) {

							}
						}
					}
					path = info[1].substring(info[1].indexOf("https:"));
					path = path.substring(0, path.length() - 2);
					if (!bHideCarFax) {
						try {
							Document doc = Jsoup.connect(path).get();
							Element element = doc.select("a.records").first();
							while (element == null) {
								doc = Jsoup.connect(doc.baseUri()).get();
								element = doc.select("a.records").first();
							}
							String recordString = element.parent().text();
							String numberString = recordString.substring(0, recordString.indexOf("hi") - 1);
							int carFaxVal = Integer.parseInt(numberString);
							String carFax = ((carFaxVal == 0) ? "\"-\"" : ("" + carFaxVal));
							outline = outline.substring(0, outline.indexOf(",,") + 1) + carFax
									+ outline.substring(outline.indexOf(",,") + 1);
							System.out.print(" Carfax " + ((carFaxVal == 0) ? "-" : carFax));
						} catch (Exception e) {
							outline = outline.substring(0, outline.indexOf(",,") + 1) + "ERR"
									+ outline.substring(outline.indexOf(",,") + 1);
							System.out.print(" Carfax " + " ERR");
						}
					}
					System.out.println();
					fos.write((outline + "\r\n").getBytes());
				} else {
					System.out.println("-------------");
					fos.write((line + "\r\n").getBytes());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}