package apiTesting;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.GenericUtility.APIBaseClass;
import com.GenericUtility.APIEndPoints;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;

public class CreateInUIValidateInAPI extends APIBaseClass {

	String projectId;
	String projectName ;
	String userName;
	String password;
	
	@Test(priority = 1)
	public void createProjectInUI() throws SQLException, IOException {
	
		FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\RMGYantra.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		String browserName = pObj.getProperty("browser");
		String url = pObj.getProperty("url");
		userName = pObj.getProperty("username");
		password =pObj.getProperty("password");
		projectName = pObj.getProperty("project_name")+jLib.getRandomNum();
		String projectManager = pObj.getProperty("project_manager");
		String projectStatus =pObj.getProperty("project_status");
		//Script to login and submit the project in RMGyantra
		
		WebDriver driver=null;
		if(browserName.equalsIgnoreCase("chrome")) {
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
		driver.findElement(By.cssSelector("button.btn.btn-success")).click();
		driver.findElement(By.name("projectName")).sendKeys(projectName);
		driver.findElement(By.name("createdBy")).sendKeys(projectManager);
		Select select = new Select(driver.findElement(By.xpath("//select[(@name='status') and not (@class)]")));
		select.selectByValue(projectStatus);
		driver.findElement(By.cssSelector("input[value='Add Project']")).click();
		
		projectId=driver.findElement(By.xpath("//td[text()='"+projectName+"']/../td[1]")).getText();
		
		System.out.println(projectId);

		driver.quit();
		
	}
	@Test(priority = 2, dependsOnMethods = "createProjectInUI")
	public void getTheCreatedProject() {
	
		Response response = given()
			.pathParam("projectId", projectId)
		.when()
			.get(APIEndPoints.getSingleProject);
//			.get("http://rmgtestingserver:8084/projects/{projectId}");
		
		response.then().log().all();
		
		String actProjectName = rLib.getJSONValue(response, "projectName");
		assertEquals(actProjectName, projectName);
	}
	
	@Test(priority = 4,dependsOnMethods = "createProjectInUI")
	public void deleteProject() {
		given()
			.pathParam("projectId", projectId)
		.when()
			.delete(APIEndPoints.deleteProject)
		.then()
			.assertThat()
			.statusCode(204);
	}
	
}
