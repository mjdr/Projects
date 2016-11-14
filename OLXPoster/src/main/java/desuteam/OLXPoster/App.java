package desuteam.OLXPoster;

import static java.lang.Math.min;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.ArrayUtils;

public class App {
	public static void main(String[] args) {
		new App(args);
	}

	private Map<String, User> allUsers, freeUsers;
	private List<User> freeUsersList;
	private List<String> titles;
	private List<String> descriptions;
	private List<List<String>> images;
	private List<Integer> prices;
	private List<Integer> delais;
	private List<DateLink> links;
	private int numOrders;
	private int lastProgress;
	private Browser browser;

	public App(String[] args) {

		try {
			Options.load();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ошибка загрузки конфигурации.");
			System.exit(0);
		}

		int status = Options.getStatus();

		if (status == Options.STATUS_CREATED) {
			JOptionPane.showMessageDialog(null,
					"Был создан файл " + Options.fileName + " . Заполните его и запустите программу снова.");
			System.exit(0);
		} else if (status == Options.STATUS_DONE)
			JOptionPane.showMessageDialog(null, "Конфигурация загружена успешно.");

		String message = Options.checkOptions();

		if (message != null) {
			JOptionPane.showMessageDialog(null, message);
			System.exit(0);
		}

		browser = new Browser();
		browser.init();

		try {
			titles = Utils.readFile(new File(Options.getTitlesFilePath()));
			allUsers = Utils.parseUsersFromFile(new File(Options.getUserDataFilePath()));
			descriptions = Utils.loadDescription(new File(Options.getDescriptionFilePath()));
			images = Utils.loadImages(new File(Options.getImageRootDir()));

			prices = Utils.loadIntList(new File(Options.getPricesFilePath()));
			delais = Utils.loadIntList(new File(Options.getDelaiesFilePath()));

			links = Utils.readLinks(new File("history.csv"));

			lastProgress = Utils.readTaskProgress();

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		freeUsers = getFreeAcconts();
		freeUsersList = new ArrayList<>(freeUsers.values());

		clearDateLinks(links);

		numOrders = min(freeUsers.size() * 5,
				min(descriptions.size(), min(images.size(), min(delais.size(), prices.size()))));

		if (ArrayUtils.contains(args, "-stats"))
			printStats();
		if (ArrayUtils.contains(args, "-clear")) {
			clearAccounts(links);
			try {
				Utils.writeLinks(new File("history.csv"), links);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (ArrayUtils.contains(args, "-add"))
			mainLoop();

	}

	private Map<String, User> getFreeAcconts() {
		Map<String, User> accounts = new HashMap<String, User>();

		startLoop: for (User user : allUsers.values()) {

			for (DateLink link : links)
				if (link.getLogin().equals(user.getLogin()))
					continue startLoop;

			accounts.put(user.getLogin(), user);
		}
		return accounts;
	}

	private void testDateLinks() {
		try {
			List<DateLink> links = Utils.readLinks(new File("history.csv"));
			for (DateLink link : links)
				System.out.println("Login : " + link.getLogin() + " ; Date: " + link.getDate());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printStats() {
		System.out.println("+---------------------[Users Stats]---------------------+");
		System.out.println("|                                                       |");
		int n = 55 - 17 - Integer.toString(allUsers.size()).length();
		System.out.print("| All accounts  : " + allUsers.size());
		for (int i = 0; i < n; i++)
			System.out.print(' ');
		System.out.println('|');

		n = 55 - 17 - Integer.toString(freeUsers.size()).length();
		System.out.print("| Free accounts : " + freeUsers.size());
		for (int i = 0; i < n; i++)
			System.out.print(' ');
		System.out.println('|');

		System.out.println("|                                                       |");
		System.out.println("+-------------------------------------------------------+");
	}

	public void mainLoop() {

		int currentUser = 0;
		int orderPerUser = 0;
		int currentDelay = 0;

		System.out.println("::Add::");

		System.out.println(numOrders);
		if (numOrders == 0)
			return;

		int code = JOptionPane.showConfirmDialog(null,
				String.format(
						"Было загружено:\n Пользователей: %d,\n Названий: %d,\n Описаний: %d,\n Картинок: %d,\n Задержок: %d,\n Цен: %d.\n\n Будет выложено %d обьявл. \n\nНачать загрузку на сайт?",
						allUsers.size(), titles.size(), descriptions.size(), images.size(), delais.size(),
						prices.size(), numOrders),
				"Начать загрузку на сайт?", JOptionPane.OK_CANCEL_OPTION);

		if (code == JOptionPane.CANCEL_OPTION)
			System.exit(0);

		browser.openBrowser();
		browser.login(freeUsersList.get(currentUser));
		if (!inList(links, freeUsersList.get(currentUser).getLogin()))
			links.add(new DateLink(freeUsersList.get(currentUser).getLogin(), new Date()));
		try {
			Utils.writeLinks(new File("history.csv"), links);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = lastProgress == -1 ? 0 : lastProgress; i < numOrders; i++) {
			if (orderPerUser >= 5) {

				browser.closeBrowser();
				browser.openBrowser();
				currentUser++;
				orderPerUser = 0;
				browser.login(freeUsersList.get(currentUser));
				if (!inList(links, freeUsersList.get(currentUser).getLogin()))
					links.add(new DateLink(freeUsersList.get(currentUser).getLogin(), new Date()));
				try {
					Utils.writeLinks(new File("history.csv"), links);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else
				orderPerUser++;

			browser.post(titles.get(i), prices.get(i), descriptions.get(i), images.get(i), Options.isProductCourier());

			try {
				Utils.writeTaskProgress(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sleep(delais.get(currentDelay) * 1000L);
		}
		if (!inList(links, freeUsersList.get(currentUser).getLogin()))
			links.add(new DateLink(freeUsersList.get(currentUser).getLogin(), new Date()));
		try {
			Utils.writeLinks(new File("history.csv"), links);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Utils.writeTaskProgress(-1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		browser.closeBrowser();
	}

	private void clearAccounts(List<DateLink> links) {

		System.out.println("::Clear::");
		for (Iterator<DateLink> i = links.iterator(); i.hasNext();) {
			DateLink link = i.next();
			long dt = System.currentTimeMillis() - link.getDate().getTime();
			if (dt > (1000L * 60L * 60L * 24L * 30L) && allUsers.containsKey(link.getLogin())) {

				System.out.println("	Clear: " + link.getLogin());
				browser.clearAccount(allUsers.get(link.getLogin()));
				freeUsers.put(link.getLogin(), allUsers.get(link.getLogin()));
				i.remove();
			}
		}

	}

	private void clearDateLinks(List<DateLink> links) {
		for (Iterator<DateLink> i = links.iterator(); i.hasNext();) {
			DateLink link = i.next();
			if (!allUsers.containsKey(link.getLogin()))
				i.remove();
		}

	}

	private boolean inList(List<DateLink> links, String login) {
		for (DateLink link : links)
			if (link.getLogin().equals(login))
				return true;
		return false;
	}

	private void sleep(long millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
