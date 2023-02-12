package fipkartWebsite;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class IpadCheckout {

	   public static WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
		
		
		try {
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		//  WebDriverManager.chromedriver().setup();	
        driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    driver.get("https://www.flipkart.com/");  //Open flipkart website
		}
		catch(Exception e) {
			System.out.println("Website not accessible");
		}
		
	    
	    //Dismiss the login popup
	    driver.findElement(By.xpath("//button[contains(text() ,'✕')]")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//input[@name='q']")).sendKeys("ipad");   // Search for ipad
	    
	    
	    //Selecting one item from suggestion list
	    WebElement inTablets =driver.findElement(By.partialLinkText("ipad 9th generation"));
	    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
	    wait.until(ExpectedConditions.visibilityOf(inTablets));
	    Actions act = new Actions(driver);
	    act.moveToElement(inTablets).click().perform();
	    
	    
	    
	    //Scroll with JavascriptExecutor to online only filter (Availability element) - Exclude Out of Stock
	    try {
	    WebElement avail = driver.findElement(By.xpath("//div[@id='container']//section//div[contains(text(),'Availability')]")); 
	    Point location = avail.getLocation();
	    int x = location.getX();
	    int y = location.getY();
	    int y1 = y-300;
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("scrollBy(0,"+y1+")");  // Scroll to element Position
	    avail.click();
	    driver.findElement(By.xpath("//div[@id='container']//section//div[contains(text(),'Exclude Out of Stock')]")).click();
	    Thread.sleep(2000);
	    }
	    catch(Exception e) {
			System.out.println("Filter not selected");
		}
	    
	    
	    // Select one item from the results
	    driver.findElement(By.partialLinkText("APPLE iPad (9th Gen) 64 GB ROM 10.2 inch with Wi-Fi Only (Space Grey)")).click();
	 
	    // Switch to new window
	    Set<String> ids = driver.getWindowHandles(); 
	    ArrayList<String> ids2 = new ArrayList<>(ids);
	    String productWindow = (String) ids2.get(1);
	    driver.switchTo().window(productWindow);
	    
	    try {
	    // Checkout the order		
	    driver.findElement(By.xpath("//ul/li/form/button[text()='Buy Now']")).click();
	  
	    
	    // Enter random Mobile number
	    driver.findElement(By.xpath("//form/div/input[@type='text']")).sendKeys("862330193");
	    driver.findElement(By.xpath("//button//span[text()='CONTINUE']")).click();
	    }
	    catch(Exception e) {
			System.out.println("Product out of stock");
		}
	    
	    
	    
	    Thread.sleep(3000);
	    driver.quit();
	 
	  
	 

	}

}
