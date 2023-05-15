package apiTesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.GenericUtility.APIBaseClass;
import com.GenericUtility.APIEndPoints;
import com.mysql.cj.jdbc.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import pojoClasses.RMGYantra;

public class CreateProjectAndVarify2 extends APIBaseClass {

	String projectId;

	@Test(priority = 1)
	public void createProject() {

		RMGYantra pObj = new RMGYantra("Vinay", "bleero" + jLib.getRandomNum(), "Created", 4);

		Response response = given().spec(requestSpec).body(pObj).when().post(APIEndPoints.createProject);
		response.then().spec(responseSpec);

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);

		long responseTime = response.getTime();
		Assert.assertEquals(responseTime < 1000l, true);

		projectId = rLib.getJSONValue(response, "projectId");

		response.then().log().all();
	}

	@Test(priority = 2)
	public void validateProject() throws SQLException {

		String querry = "select * from project;";

		String data = DB.executeQueryAndReturnData(querry, 1, projectId);
		System.out.println(data);

	}

	@Test(priority = 3)
	public void valiDateInUI() throws Throwable {

		FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\RMGYantra.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		String browserName = pObj.getProperty("browser");
		String url = pObj.getProperty("url");
		String userName = pObj.getProperty("username");
		String password = pObj.getProperty("password");
		// Script to login and submit the project in RMGyantra

		WebDriver driver = null;
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		driver.findElement(By.id("usernmae")).sendKeys(userName);
		driver.findElement(By.id("inputPassword")).sendKeys(password);
		driver.findElement(By.xpath("//button[text()='Sign in']")).submit();
		driver.findElement(By.linkText("Projects")).click();

		List<WebElement> pIds = driver.findElements(By.xpath("//tbody/tr/td[1]"));

		boolean flag = false;
		for (WebElement pid : pIds) {
			String projId = pid.getText();
			System.out.println(projId);
			if (projectId.equals(projId)) {
				flag = true;
			}
		}

		if (flag) {
			System.out.println("project is created and verified in database");
		} else if (!flag) {
			System.out.println("project is not created and verified in database");
		}

	}

}
