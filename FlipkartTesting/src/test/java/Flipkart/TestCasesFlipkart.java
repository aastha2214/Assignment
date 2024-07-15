package Flipkart;

import java.awt.AWTException;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.util.Assert;

import org.openqa.selenium.TakesScreenshot;



import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;
import util.Utility;



public class TestCasesFlipkart {
	
	
	public static Logger logger=LogManager.getLogger("TestCasesFlipkart");
	
	WebDriver driver;
	SoftAssert soft=new SoftAssert();
	
	@BeforeTest
	@Parameters({"browser"})
	public void launchBrowser(String browser) {
		
	logger.info("Launching browser and opening flipkart");
		
		if(browser.equalsIgnoreCase("chrome"))
		{
		    driver=new ChromeDriver();
			WebDriverManager.chromedriver().setup();
			driver.get("https://www.flipkart.com/");
			driver.manage().window().maximize();
		}
		else if(browser.equalsIgnoreCase("edge"))
		{
			driver=new EdgeDriver();
			WebDriverManager.edgedriver().setup();
			driver.get("https://www.flipkart.com/");
			driver.manage().window().maximize();
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
			WebDriverManager.firefoxdriver().setup();
			driver.get("https://www.flipkart.com/");
			driver.manage().window().maximize();
		}

		
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		
		logger.info("Printing session id");
		
		String sessionid=driver.getWindowHandle();
		System.out.println("before searching prdoduct session id is :"+sessionid);
	}
	
	
	@Test(priority=1, description="Fetching all the categories below search bar")

	public void categoriesBelowSearch() {
		List <WebElement> categories=driver.findElements(By.xpath("//a[@class='1ch8e']"));
		List <WebElement> restcategories=driver.findElements(By.xpath("//div[@class='1ch8e']"));
		
		logger.info("Printing categories");
		
		for(WebElement cat:categories) {
			
			System.out.println(cat.getText());
			
		}
		for(WebElement restcat:restcategories) {
			
			System.out.println(restcat.getText());
		}
	}
	
	
	@Test(priority=2,description="Searching, selecting and opening details page of the product")

	public void searchAndOpenProduct() throws AWTException {
	
    logger.info("Searching product");
    
	WebElement search=	driver.findElement(By.xpath("//input[@title='Search for Products, Brands and More']"));
	search.sendKeys("Iphone 13");
	Robot rb= new Robot();
	rb.keyPress(KeyEvent.VK_ENTER);
	rb.keyRelease(KeyEvent.VK_ENTER);
	
	logger.info("validating products page is opened or not");
	
	String Actualtitle=driver.getTitle();
	soft.assertEquals(Actualtitle,"Iphone 13- Buy Products Online at Best Price in India - All Categories | Flipkart.com");
	soft.assertAll();
	
	logger.info("Clicking on product");
	
	WebElement productlink=driver.findElement(By.xpath("//div[@class='KzDlHZ'][normalize-space()='Apple iPhone 13 (Green, 128 GB)']"));
	productlink.click();
	
	Set <String>handles=driver.getWindowHandles();
	List<String>handle=new ArrayList<>();
	handle.addAll(handles);
	driver.switchTo().window(handle.get(1));
	
	logger.info("validating correct product open or not ");
	
	WebElement actualproduct=driver.findElement(By.xpath("//span[text()='Apple iPhone 13 (Green, 128 GB)']"));
	String ExpectedProduct="Apple iPhone 13 (Green, 128 GB)";
	soft.assertEquals(actualproduct, ExpectedProduct);
	
	}
	
	
	@Test(priority=3,description="Checking the compare checkbox & validating if compare modal opens,Clicking on compare modal & validating selected device is visible")

	public void checkCompareModalAndSelectedDevice() throws AWTException {
			
		logger.info("Clicking on compare checkbox");
		
		WebElement compareCheckBox=driver.findElement(By.xpath("//label[normalize-space()='Compare']"));
		compareCheckBox.click();
		
		WebElement compareModal=driver.findElement(By.xpath("//span[contains(text(),'COMPARE')]"));
		
		logger.info("Validating compare modal displayed or not");
		
		soft.assertTrue(compareModal.isDisplayed());
		
		logger.info("Clicking on compare modal");
		
		compareModal.click();
		
		logger.info("Validating selected product visible or not");
		
		String compareproductselected=driver.findElement(By.xpath("(//a[text()='Apple iPhone 13 (Green, 128 GB)'])[1]")).getText();
		soft.assertEquals(compareproductselected, "Apple iPhone 13 (Green, 128 GB)");
	}
	
	
	@Test(priority=4,description="Adding more products and printing ratings and reviews")

	public void addMoreProductsAndFetchRatings() throws InterruptedException {
		
		Set <String>handles=driver.getWindowHandles();
		List<String>handle=new ArrayList<>();
		handle.addAll(handles);
		driver.switchTo().window(handle.get(0));
		
		logger.info("Adding more products on compare page"); 
		
		Thread.sleep(3000);
		
		List <WebElement> product= driver.findElements(By.xpath("//label[@class='uu79Xy']"));
		
	     for(int i=0;i<product.size();i++)
	     {
	    	 if(i<4)
	    	 {
	    		 product.get(i).click();
	    	 }
	     }
	     
	     logger.info("Going to compare page");
	     
	     Thread.sleep(3000);
	     
	     WebElement compareModal=driver.findElement(By.xpath("//span[contains(text(),'COMPARE')]"));
		 compareModal.click();
			
		  logger.info("Fetching ratings and reviews of 4 products");
		 
		    Thread.sleep(3000);
		    
			List<WebElement> ratingsandreviews=driver.findElements(By.xpath("//span[@class='Wphh3N XNM81O']"));
			for(WebElement ratingandreview:ratingsandreviews)
			{
				System.out.println(ratingandreview.getText());
			}
	    	   
			
	       }
	
	@AfterMethod
	public void ScreenshotOnTestFailure(ITestResult result) throws IOException
	{
		if(ITestResult.FAILURE==result.getStatus())
		{
			Utility.TakingScreenshot(driver,result.getName());
		}
		
	}
	
	@AfterTest
	public void teardown() {
	driver.quit();
	}
	
	}


