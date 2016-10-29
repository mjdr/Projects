package desuteam.ru.equipnet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main();
	}

	public Main() {
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter("equipnet.csv");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}

		Map<String, String> firms;
		while (true) {
			try {
				firms = getFirms();
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Entry<String, String> entry : firms.entrySet()) {
			Map<String, String> companies;
			while (true) {
				try {
					System.out.println("Get conpanies Cat:" + entry.getKey());
					companies = getCompanies(entry.getValue());
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (String url : companies.values()) {
				CompanyInfo info;
				int counter = 0;
				for (; counter < 10; counter++) {
					try {
						info = getCompanyInfo(entry.getKey(), url);
						print(printWriter, info);
						printWriter.flush();
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		}
		printWriter.close();
	}

	public void print(PrintWriter pw, CompanyInfo info) {
		String template = "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n";

		pw.printf(template, info.getKind().replace('"', '\''), info.getName().replace('"', '\''),
				info.getDescription().replace('"', '\''), info.getAddress().replace('"', '\''),
				info.getPhone().replace('"', '\''), info.getFace().replace('"', '\''),
				info.getSite().replace('"', '\''), info.getEmail().replace('"', '\''));

	}

	private CompanyInfo getCompanyInfo(String kind, String companyUrl) throws IOException {
		System.out.println("//Get company info " + companyUrl);
		Document doc = Jsoup.connect(companyUrl).get();

		String name = getString(doc, "[itemprop=name]", "text");
		String description = getString(doc, "#substrText", "text");
		String address = getString(doc, "[itemprop=streetAddress]", "text");
		String phone = getString(doc, "[itemprop=telephone]", "text");
		String site = getString(doc, "#cardInfo noindex a", "text");
		String email = getString(doc, "[itemprop=email]", "text");
		String face = getStringFromTable(doc.select("#company_contact table").first(), "Контактное лицо:");

		return new CompanyInfo(kind, name, description, address, phone, face, site, email);
	}

	private String getStringFromTable(Element table, String key) {
		Elements trs = table.select("tr");
		for (Element tr : trs) {
			Elements keyEl = tr.select("td b");
			if (keyEl.size() == 0)
				continue;
			if (keyEl.first().text().equals(key)) {
				Elements res = tr.select("td");
				if (res.size() < 2)
					return "";
				return res.get(1).text();
			}
		}
		return "";
	}

	private String getString(Element parent, String selector, String attr) {
		Elements res = parent.select(selector);
		if (res.size() == 0)
			return "";
		if (attr.equals("text"))
			return res.get(0).text();
		return res.get(0).attr(attr);
	}

	private Map<String, String> getCompanies(String catUrl) throws IOException {
		boolean hasNext;
		int page = 0;
		Map<String, String> result = new HashMap<String, String>();
		Document doc = null;
		do {
			System.out.println("Get conpanies: Page " + page);
			int trys = 0;
			for (; trys < 10; trys++) {
				try {
					doc = Jsoup.connect(catUrl + "?curPos=" + (page * 10)).timeout(10000).get();
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			page++;
			if (trys == 10)
				break;

			Elements tables = doc.select("#companyList table.tableStandart.vTop");
			for (Element table : tables) {
				Element link = table.select("a").get(0);
				String href = "http://www.equipnet.ru" + link.attr("href");
				String title = link.hasAttr("title") ? link.attr("title") : link.text();
				result.put(title, href);
			}
			hasNext = doc.select("a.next_link").size() > 0;
		} while (hasNext);
		return result;
	}

	private Map<String, String> getFirms() throws IOException {
		System.out.println("//Get firms");
		Document doc = Jsoup.connect("http://www.equipnet.ru/russia/firms/").get();
		Elements links = doc.select(".companyRubricListBranch a");
		Map<String, String> results = new HashMap<String, String>();
		for (Element l : links)
			results.put(l.text(), "http://www.equipnet.ru" + l.attr("href"));
		return results;
	}

}
