package com.GenericUtility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	int count = 0;
	public boolean retry(ITestResult result) {
		
		int retryLimit = 2;
		if(count<retryLimit) {
			count++;
			return true;
		}
		return false;
	}

}
