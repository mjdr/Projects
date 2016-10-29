package desuteam.OLXPoster;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Br {
	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "libs/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.navigate().to("http://google.com/");
		long t = System.currentTimeMillis();

		driver.findElements(By.cssSelector("a.gb_P")).get(1).click();

		System.out.println(System.currentTimeMillis() - t);

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));

		System.out.println(System.currentTimeMillis() - t);

	}
}
