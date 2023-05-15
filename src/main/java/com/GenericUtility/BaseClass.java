package com.GenericUtility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
public class BaseClass {
	
	//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 -----> git Hrm tocken
	//git@github.com:vins4652/HRM-Framework.git -----> git ssh
	//https://github.com/vins4652/HRM-Framework.git -----> git HTTPS

	public WebDriver driver;
	public ExcelUtility xLib = new ExcelUtility();
	public FileUtility fLib=new FileUtility();
	public JavaUtility jLib = new JavaUtility();
	DBUtility dLib = new DBUtility();
	public static WebDriver sdriver;
	
	@BeforeSuite(alwaysRun = true)
	public void configBeforeSuite() throws SQLException {
		dLib.connectToDB();
		Reporter.log("--Connected to DataBase--", true);
	}
	
//	@Parameters({"browserName"})
	@BeforeMethod(alwaysRun = true)
//	public void configBeforeMethod(@Optional ("chrome") String browserName) throws IOException {
	public void configBeforeMethod() throws IOException {
		String browserName = fLib.getProperyFileData("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else {
			Reporter.log("Invalid Browser", true);
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		sdriver=driver;
		String url = fLib.getProperyFileData("url");
		driver.get(url);
	}
	
	@Test(groups ="browser")
	public void openBrowser() {
	}
	
	@AfterMethod(alwaysRun = true)
	public void configAfterMethod() {
		driver.quit();
	}
	
	@AfterSuite(alwaysRun = true)
	public void configAfterSuite() throws SQLException {
		dLib.closeDBConnection();
		Reporter.log("--Disconnect from database--",true);
	}
	
	
	
	
	
}
