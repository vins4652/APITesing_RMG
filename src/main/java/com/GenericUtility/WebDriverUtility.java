package com.GenericUtility;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



public class WebDriverUtility {

	
	/**
	 * This method will wait untill the title get loaded fully 
	 * @param driver
	 */
	public Boolean explicitWaitForTitleContains(WebDriver driver, String completeTitle ) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		Boolean ref = wait.until(ExpectedConditions.titleContains(completeTitle));
		return ref;
	}
	/**
	 * This method will give the sync time to load the complete Url
	 * @param driver
	 * @param completeUrl
	 * @author Vinaykumar Mannur
	 * @return 
	 */
	public Boolean explicitwaitForCompleteUrl(WebDriver driver, String completeUrl) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		return wait.until(ExpectedConditions.urlToBe(completeUrl));
	}
	/**
	 * This method will give the sync time to load the Url 
	 * @param driver
	 * @param urlContains
	 * @return 
	 */
	public Boolean explicitwaitForUrlcontains(WebDriver driver, String urlContains) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		return wait.until(ExpectedConditions.urlContains(urlContains));
	}
	/**
	 * This will Give the explicit wait to the element
	 * @param driver
	 * @param elementRef
	 */
	public void explicitwaitForElement(WebDriver driver, WebElement elementRef) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(elementRef));
	}
	/**
	 * This method will change the driver control from the current frame to the another frame by frame index number 
	 * @param driver
	 * @param index
	 */
	public void frameAsIndex(WebDriver driver,int index) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
//		driver.switchTo().frame(index);
	}
	/**
	 * This method will change the driver control from the current frame to the another frame by name or id attribute value
	 * @param driver
	 * @param nameOrId
	 */
	public void frameAsIdorName(WebDriver driver,String nameOrId) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
//		driver.switchTo().frame(nameOrId);
	}
	
	/**
	 * This method will change the driver control from the current frame to the another frame by frame element reference
	 * @param driver
	 * @param eleRef
	 */
	public void frameAsWebElement(WebDriver driver,WebElement eleRef) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(eleRef));
//		driver.switchTo().frame(eleRef);
	}
	
	/**
	 * This method will wait till 5 seconds to load and perform click action
	 * @param driver
	 * @param elementRef
	 *  
	 */
	public void clickOnVisibleElement (WebDriver driver, WebElement elementRef) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(elementRef));
			elementRef.click();
	}
	
	/**
	 * This method will wait till 5 seconds to load and perform submit action
	 * @param driver
	 * @param elementRef
	 * @author Vinaykumar Mannur
	 */
	public void submitOnVisibleElement (WebDriver driver, WebElement elementRef) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(elementRef));
		elementRef.submit();
	}
	/**
	 * This method will wait till 5 seconds to load and send the text
	 * @param driver
	 * @param elementRef
	 * @param text
	 * @author Vinaykumar Mannur
	 */
	public void sendkeysOnVisibleTextField (WebDriver driver, WebElement elementRef, CharSequence text) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(elementRef));
		elementRef.clear();
		elementRef.sendKeys(text);
	}
	/**
	 * This method will wait till 5 seconds to load and it will clear the field
	 * @param driver
	 * @param elementRef
	 * @author Vinaykumar Mannur
	 */
	public void clearTheVisibleTextField (WebDriver driver, WebElement elementRef) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(elementRef));
		elementRef.clear();
	}
	/**
	 * 
	 * @param driver
	 * @param elementRef
	 * @return main or subTagContainingText
	 * @author Vinaykumar Mannur
	 */
	public String getTagtexOfElement (WebDriver driver, WebElement elementRef) {
		WebDriverWait wait =	new WebDriverWait(driver, 5);
		//wait.until(ExpectedConditions.visibilityOf(elementRef));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(elementRef)));
		return elementRef.getText();
	}
	/**
	 * This Method will wait for 5 second and confirms the visibility of the element
	 * @param driver
	 * @param elementRef
	 * @return boolean
	 * @author Vinaykumar Mannur
	 */
	public boolean displayOfElement(WebDriver driver, WebElement elementRef) {
		WebDriverWait wait =new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(elementRef));
		return elementRef.isDisplayed();
	}
	/**
	 * This method will take the screen shot of the webpage
	 * @param driver
	 * @param methodName
	 * @return String filepath of the image screen shot
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public String getScreenShot(WebDriver driver, String methodName) throws IOException {
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String filePath = "./errorshots/"+methodName+"_"+LocalDateTime.now().toString().replace(':', '-')+".png";
		File destFile= new File(filePath);
		FileUtils.copyFile(srcFile, destFile);
		return destFile.getAbsolutePath();
	}
	/**
	 * This method will select the option based on visible text
	 * @param Element
	 * @param text of Option in dropdown
	 * @author Vinaykumar Mannur
	 * @param driver 
	 */
	public void selectbyVisibleText(WebDriver driver, WebElement Element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Select select = new Select(wait.until(ExpectedConditions.visibilityOf(Element)));
		select.selectByVisibleText(text);
	}
	
	/**
	 * This method will select the option based Value of value Attribute
	 * @param Element
	 * @param Value of Option in dropdown
	 * @author Vinaykumar Mannur
	 */
	public void selectbyValue(WebElement Element, String value) {
		Select select = new Select(Element);
		select.selectByValue(value);
	}
	
	/**
	 * This method will select the option based on index value
	 * @param index of Option in dropdown
	 * @author Vinaykumar Mannur
	 */
	public void selectbyIndex(WebElement Element, int index) {
		Select select = new Select(Element);
		select.selectByIndex(index);
	}

	/**
	 * This method will scroll till the element comes to the view port area
	 * @param driver
	 * @param element
	 * @author Vinaykumar Mannur
	 */
	public void scrollToElementAndClick(WebDriver driver, WebElement element) {
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		for(;;) {
			try {
				element.click();
				break;
			}catch (NoSuchElementException e) {
				jse.executeScript("window.scrollBy(0,500)");
			}
		}
	}
	
	/**
	 * This method will scroll the webpage to 500pixel
	 * @param driver
	 */
	public void scrollThePage(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,500)");
	}
	
	/**
	 * This method will wait till the alertIsPresent 
	 * @param driver
	 * @return Alert
	 * @author Vinaykumar Mannur
	 */
	public Alert explicitWaitForAlert(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		return wait.until(ExpectedConditions.alertIsPresent());
	}
}
