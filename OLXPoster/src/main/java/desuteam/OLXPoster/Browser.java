package desuteam.OLXPoster;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {
	
	private WebDriver driver;
	
	
	public void login(User user){
	    	
	    	driver.navigate().to("https://ssl.olx.ua/account/");
	    	
	    	sleep(2000);
	    	
	    	String selector = "body > div.smcx-widget.smcx-modal.smcx-modal-survey.smcx-show.smcx-widget-dark.smcx-hide-branding.smcx-opaque > div.smcx-modal-header > div.smcx-modal-close";
	    	if(driver.findElements(By.cssSelector(selector)).size() > 0)
	    		driver.findElement(By.cssSelector(selector)).click();
	    	
	    	
	    	WebElement loginField = driver.findElement(By.xpath("//*[@id=\"userEmail\"]"));
	    	WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"userPass\"]"));
	    	WebElement sendButton = driver.findElement(By.xpath("//*[@id=\"se_userLogin\"]"));
	    	
	    	
	    	loginField.sendKeys(user.getLogin());
	    	passwordField.sendKeys(user.getPassword());
	    	sendButton.click();
	    	
	    	sleep(5000);
	    	
	    }
	    
	public void post(String title, int price,String description,List<String> imagesPath,boolean hasCourier){

	    	if(imagesPath.size() > 8) throw new IllegalArgumentException("Number of images grader then 8");
	    	
	    	driver.navigate().to("http://olx.ua/post-new-ad/");
	    	sleep(5000);
	    	//Заголовок
	    	driver.findElement(By.xpath("//*[@id=\"add-title\"]")).sendKeys(title);
	    	
	    	//Рубрика
	    	clickByXpath("//*[@id=\"targetrenderSelect1-0\"]/dt/a");
	    	
	    	
	    	clickByXpath("//*[@id=\"cat-37\"]",
	    			"//*[@id=\"category-37\"]/div[2]/div[2]/div/ul/li[1]/a/span[1]",
	    			"//*[@id=\"category-44\"]/div[2]/div[2]/div/ul/li[2]/a/span[1]"
	    			);
	    	
	    	sleep(2000);
	    	//Марка телефона
	    	clickByXpath(1000,"//*[@id=\"targetparam113\"]/dt/a","//*[@id=\"targetparam113\"]/dd/ul/li[36]/a");
	    	
	    	//Цена
	    	driver.findElement(By.xpath("//*[@id=\"parameter-div-price\"]/div[2]/div/div[1]/p[3]/input")).sendKeys(price+"");
	    	
	    	//Бу
	    	clickByXpath("//*[@id=\"targetparam13\"]/dt/a","//*[@id=\"targetparam13\"]/dd/ul/li[2]/a");
	    	
	    	//Частное лицо / Бизнес
	    	if(driver.findElements(By.xpath("//*[@id=\"targetid_private_business\"]/dt/a")).size() > 0)
	    		clickByXpath("//*[@id=\"targetid_private_business\"]/dt/a", "//*[@id=\"targetid_private_business\"]/dd/ul/li[3]/a");
	    	
	    	
	    	//Описание
	    	driver.findElement(By.xpath("//*[@id=\"add-description\"]")).sendKeys(description);
	    	
	    	//Фотографии
	    	driver.findElement(By.xpath("//*[@id=\"show-gallery-html\"]")).click();
	    	for(int i = 0;i < imagesPath.size();i++)
	    		if(imagesPath.get(i).length() > 0)
	    			driver.findElement(By.cssSelector("#htmlbutton_"+(i + 1)+" input[type=file]")).sendKeys(imagesPath.get(i));
	    	
	    	//Доставка с OLX
	    	if(hasCourier)
	    		driver.findElement(By.xpath("//*[@id=\"email\"]/div[2]/div/label")).click();
	    	
	    	//Отправить
	    	clickByXpath("//*[@id=\"save\"]");
	    }
	    
	public void clickByXpath(String... paths){
		clickByXpath(0,paths);
	}
	public void clickByXpath(int wait,String... paths){
	    	for(String path : paths){
	    		waitForElement(path);
	    		sleep(wait);
	        	driver.findElement(By.xpath(path)).click();
	    	}
	    }
	    
	public void init(){
	    	if(OSValidator.isWindows())
	    		System.setProperty("webdriver.chrome.driver", "libs/chromedriver.exe");
	    	else if(OSValidator.isMac())
	    		System.setProperty("webdriver.chrome.driver", "libs/chromedriver");
	    	
	    }
	    
	public void sleep(long millisec){
	    	try {Thread.sleep(millisec);} catch (InterruptedException e) {e.printStackTrace();}
	    }
	
	
	private void waitForElement(String xPath){
		while(driver.findElements(By.xpath(xPath)).size() == 0)
    		sleep(100);
	}
	
	public void removeAllPosts(){

		
		driver.navigate().to("http://olx.ua/myaccount/waiting/");
		sleep(2000);
		
		if(driver.findElements(By.id("listContainer")).size() > 0){
			WebElement list = driver.findElement(By.id("listContainer"));
			
			for(WebElement link : list.findElements(By.tagName("a")))
				try{
					if(link.getText().contains("Отменить публикацию")){
						link.click();
						sleep(1000);
					}
				}catch (Exception e) {}
			
		}

		driver.navigate().to("http://olx.ua/myaccount/");
		sleep(2000);
    	
		for(WebElement link : driver.findElements(By.tagName("a")))
			try{
				if(link.getAttribute("id").contains("deactivate")){
					link.click();
					sleep(1000);
					if(exists("//*[@id=\"reasonInnerHeight\"]/div[3]/label[3]/span"))
						clickByXpath("//*[@id=\"reasonInnerHeight\"]/div[3]/label[3]/span");
				}
			}catch (Exception e) {}
		
		
		driver.navigate().to("http://olx.ua/myaccount/archive/");
		sleep(2000);
		for(WebElement link : driver.findElements(By.tagName("a")))
			try{
				if(link.getAttribute("text").contains("Удалить") && link.isDisplayed() && link.isEnabled()){
					link.click();
					sleep(1000);
				}
			}catch (Exception e) {}
		
	}

	public void openBrowser(){
		driver = new ChromeDriver();
	}
	public void closeBrowser(){
		driver.close();
		driver = null;
	}
	
	
	private boolean exists(String xPath) {
		return driver.findElements(By.xpath(xPath)).size() > 0;
	}

	public void clearAccount(User user) {
		boolean browserOpen = driver != null;
		
		if(!browserOpen) openBrowser();
		
		login(user);
		removeAllPosts();
		
		if(!browserOpen) closeBrowser();
		
	}
   
}
