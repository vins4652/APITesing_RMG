package com.GenericUtility;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestListener implements ITestListener {

	
	ExtentReports report;
	ExtentTest test;
	
	public void onTestStart(ITestResult result) {
		test = report.createTest(result.getMethod().getMethodName());
		Reporter.log(result.getMethod().getMethodName()+" ---------> Test Execution Started");
		test.log(Status.INFO, "---------> Test Execution Started");
	}

	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, result.getMethod().getMethodName()+"Test is passed");
		Reporter.log(result.getMethod().getMethodName()+" ---------> Test is Passed");
	}

	public void onTestFailure(ITestResult result) {
		EventFiringWebDriver edriver = new EventFiringWebDriver(BaseClass.sdriver);
		File src = edriver.getScreenshotAs(OutputType.FILE);
		String ScreenShotPath = "./screenshot/"+result.getMethod().getMethodName()+"_"+LocalDateTime.now().toString().replace(':', '-')+".jpeg";
		File dst = new File(ScreenShotPath);
		try {
			FileUtils.copyFile(src, dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.log(Status.FAIL, result.getThrowable());
		test.addScreenCaptureFromPath(dst.getAbsolutePath(), result.getMethod().getMethodName());
		
	}

	public void onTestSkipped(ITestResult result) {
		test.log(Status.SKIP, result.getThrowable());
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		test.log(Status.FAIL, result.getThrowable());
	}

	public void onStart(ITestContext context) {
		//create a html file using ExtentSparkReporter
		ExtentSparkReporter reporter = new ExtentSparkReporter("./ExtentReport/Report_"+LocalDateTime.now().toString().replace(':', '-')+".html");
		reporter.config().setDocumentTitle("Test Automation Report");
		reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setEncoding("utf-8");
		reporter.config().setJs("js-string");
		reporter.config().setCss("css-string");
		reporter.config().setReportName("Human Resource Management");
		//reporter.config().setCss(".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}  .card-panel.environment  th:first-child{ width:30%;}");
		//".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}  .card-panel.environment  th:first-child{ width:30%;}"
		report = new ExtentReports();
		report.attachReporter(reporter);
		report.setSystemInfo("OS version", "Windows 10");
		report.setSystemInfo("Build version", "v1.0.5");
		report.setSystemInfo("Browser", "Chrome");
		report.setSystemInfo("Base_URL", "http://rmgtestingserver/domain/HRM_System/");
		report.setSystemInfo("Test Engineer name", "VinayKumar");
	}

	public void onFinish(ITestContext context) {
		report.flush();
	}
	
	
	
}
