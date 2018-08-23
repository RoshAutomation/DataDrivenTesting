package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

public class TestBase {

	/*
	 * WebDriver Properties Logs ExtentReports DB Excel Mail
	 * 
	 */

	public static WebDriver driver;
	public static Properties prop = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;


	@BeforeSuite
	public void setUp() {
		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
				prop.load(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
				OR.load(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
				
				browser = System.getenv("browser");
			}else{
				
				browser = prop.getProperty("browser");
				
			}
			
			prop.setProperty("browser", browser);

			String browserName = prop.getProperty("browser");
			if (browserName.equals("ff")) {

				driver = new FirefoxDriver();

			} else if (browserName.equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");

				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");
				driver = new ChromeDriver(options);
			}

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 15);

			driver.get(prop.getProperty("url"));

		}
	}

	public void click(String locator) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

	public void type(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);
	}

	public static void verifyEquals(String expected, String Actual) throws IOException {
		try {
			Assert.assertEquals(Actual, expected);
		} catch (Throwable t) {
			TestUtil.takeScreenshotAtEndOfTest();
			Reporter.log("<br>"+"Verification Failure : "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			//Extent Reports
			test.log(LogStatus.FAIL, "Verification Failed with Exception : "+t.getMessage());
			test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.screenshotName));
			
		}
	}
	
	
	static WebElement dropdown;
	
	public void select(String locator,String value){
		
		if (locator.endsWith("_CSS")) {
			dropdown=driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown=driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select=new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, " Selecting from dropdown : " + locator + " entered value as " + value);
	}
	
	

	public boolean isElementPresent(By by) {

		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

	}

}
